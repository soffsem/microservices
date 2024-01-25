package Skillbox.com.users.entity;

import Skillbox.com.users.utils.Sex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String firstName;
    private String middleName;
    @NonNull
    private String surname;
    @NonNull
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    @NonNull
    private Sex sex;
    private Integer city_id;
    @Column(unique = true)
    @NonNull
    private String nickname;
    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    @NonNull
    private String passwordHash;
    private String avatarLink;
    private String aboutUser;

    @Builder.Default
    private boolean deleted = Boolean.FALSE;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "followee_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followedBy;

    @JsonIgnore
    @ManyToMany(mappedBy = "followedBy")
    private Set<User> followerOf;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "users_hardskills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Hardskill> userSkills;

    public void follow(User user) {
        this.followerOf.add(user);
        user.followedBy.add(this);
    }

    public void unfollow(Long id) {
        User user = this.followerOf.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
        if(user != null) {
            this.followerOf.remove(user);
            user.followedBy.remove(this);
        }
    }

    public void addSkill(Hardskill hardskill) {
        this.userSkills.add(hardskill);
        hardskill.getUsers().add(this);
    }

    public void removeSkill(Long id) {
        Hardskill hardskill = this.userSkills.stream().filter(h -> h.getId() == id).findFirst().orElse(null);
        if(hardskill != null) {
            this.userSkills.remove(hardskill);
            hardskill.getUsers().remove(this);
        }
    }
}

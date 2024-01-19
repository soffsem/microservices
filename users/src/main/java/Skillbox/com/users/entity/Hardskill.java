package Skillbox.com.users.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hardskills")
@SQLDelete(sql = "UPDATE hardskills SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Hardskill {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hardskills_id_seq")
    @SequenceGenerator(name = "hardskills_id_seq", sequenceName = "hardskills_id_seq", allocationSize = 1)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String skill;

    private boolean deleted = Boolean.FALSE;

    @JsonIgnore
    @ManyToMany(mappedBy = "userSkills")
     private Set<User> users;
}

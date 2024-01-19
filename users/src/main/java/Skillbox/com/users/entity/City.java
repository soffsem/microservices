package Skillbox.com.users.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "cities")
@SQLDelete(sql = "UPDATE cities SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cities_id_seq")
    @SequenceGenerator(name = "cities_id_seq", sequenceName = "cities_id_seq", allocationSize = 1)
    @Column(updatable = false)
    private Long id;
    @NonNull
    private String cityName;
    @NonNull
    private String countryName;

    @Builder.Default
    private boolean deleted = Boolean.FALSE;
}

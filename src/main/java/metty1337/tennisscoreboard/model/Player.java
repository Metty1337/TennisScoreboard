package metty1337.tennisscoreboard.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
@ToString
@Table(name = "Players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, name = "Name", unique = true)
    private String name;
}

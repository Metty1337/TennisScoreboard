package metty1337.tennisscoreboard.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Getter
@Setter
@ToString
@Table(name = "Matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Player1", referencedColumnName = "ID")
    private Player Player1Id;

    @ManyToOne
    @JoinColumn(name = "Player2", referencedColumnName = "ID")
    private Player Player2Id;

    @ManyToOne
    @JoinColumn(name = "Winner", referencedColumnName = "ID")
    private Player WinnerId;
}

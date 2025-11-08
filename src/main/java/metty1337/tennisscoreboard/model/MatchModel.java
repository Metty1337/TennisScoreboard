package metty1337.tennisscoreboard.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Getter
@Setter
@ToString
@Table(name = "matches")
public class MatchModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player1_id", referencedColumnName = "id")
    private PlayerModel playerOne;

    @ManyToOne
    @JoinColumn(name = "player2_id", referencedColumnName = "id")
    private PlayerModel playerTwo;

    @ManyToOne
    @JoinColumn(name = "winner", referencedColumnName = "id")
    private PlayerModel winner;
}

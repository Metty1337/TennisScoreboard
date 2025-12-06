package metty1337.tennisscoreboard.model;

import lombok.*;
import metty1337.tennisscoreboard.enums.Point;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class ScoreModel {
    private Point playerOnePoints;
    private int playerOneGames;
    private int playerOneSets;
    private int playerOneTieBreakPoints;

    private Point playerTwoPoints;
    private int playerTwoGames;
    private int playerTwoSets;
    private int playerTwoTieBreakPoints;

    private boolean tieBreak;
}
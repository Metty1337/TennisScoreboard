package metty1337.tennisscoreboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import metty1337.tennisscoreboard.model.ScoreModel;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MatchScoreDto {
    private String playerOneName;
    private String playerTwoName;
    private ScoreModel score;
}

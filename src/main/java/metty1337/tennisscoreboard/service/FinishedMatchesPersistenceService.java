package metty1337.tennisscoreboard.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metty1337.tennisscoreboard.dao.MatchDao;
import metty1337.tennisscoreboard.model.MatchModel;
import metty1337.tennisscoreboard.model.MatchScoreModel;
import metty1337.tennisscoreboard.model.PlayerModel;

@ApplicationScoped
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class FinishedMatchesPersistenceService {
    private final MatchDao matchDao;

    @Inject
    public FinishedMatchesPersistenceService(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    public void saveMatchToFinishedScoreboard(MatchScoreModel matchScoreModel, int winnerId) {
        PlayerModel playerOne = matchScoreModel.playerOne();
        PlayerModel playerTwo = matchScoreModel.playerTwo();
        PlayerModel winner = null;
        if (winnerId == playerOne.getId()) {
            winner = playerOne;
        } else {
            winner = playerTwo;
        }

        MatchModel matchModel = new MatchModel();
        matchModel.setPlayerOne(playerOne);
        matchModel.setPlayerTwo(playerTwo);
        matchModel.setWinner(winner);

        matchDao.save(matchModel);
    }
}

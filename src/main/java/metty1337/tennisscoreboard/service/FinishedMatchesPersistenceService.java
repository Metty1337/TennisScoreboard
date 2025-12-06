package metty1337.tennisscoreboard.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metty1337.tennisscoreboard.dao.MatchDao;
import metty1337.tennisscoreboard.model.MatchModel;
import metty1337.tennisscoreboard.model.MatchScoreModel;
import metty1337.tennisscoreboard.model.PlayerModel;

import java.util.List;
import java.util.Objects;

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
        PlayerModel winner;
        if (winnerId == playerOne.getId()) {
            winner = playerOne;
        } else {
            winner = playerTwo;
        }

        MatchModel matchModel = new MatchModel();
        matchModel.setPlayerOne(playerOne);
        matchModel.setPlayerTwo(playerTwo);
        matchModel.setWinner(winner);

        Objects.requireNonNull(matchDao).save(matchModel);
    }

    public List<MatchModel> getPageOfMatches(int page, int pageSize, String playerName) {
        int offset = (page - 1) * pageSize;

        List<MatchModel> matchModels;
        if (!playerName.isBlank()) {
            matchModels = Objects.requireNonNull(matchDao).findBySimilarNameWithSettings(pageSize, offset, playerName);
        } else {
            matchModels = Objects.requireNonNull(matchDao).findAllWithSettings(pageSize, offset);
        }
        return matchModels;
    }

    public int getTotalPages(int pageSize) {
        List<MatchModel> matchModels = Objects.requireNonNull(matchDao).findAll();
        return (int) Math.ceil((double) matchModels.size() / pageSize);
    }
}

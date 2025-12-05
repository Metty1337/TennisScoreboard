package metty1337.tennisscoreboard.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metty1337.tennisscoreboard.dto.MatchScoreDto;
import metty1337.tennisscoreboard.model.MatchScoreModel;
import metty1337.tennisscoreboard.model.ScoreModel;
import metty1337.tennisscoreboard.service.FinishedMatchesPersistenceService;
import metty1337.tennisscoreboard.service.MatchScoreCalculationService;
import metty1337.tennisscoreboard.service.OngoingMatchesService;
import metty1337.tennisscoreboard.service.PlayerService;

import java.io.IOException;

@WebServlet(value = "/match-score")
public class MatchScoreController extends HttpServlet {
    private static final String UUID = "uuid";
    private static final String WINNER_ID = "winnerId";
    private static final String WINNER_NAME = "winnerName";
    private static final String PLAYER_ONE = "playerOne";
    private static final String PLAYER_TWO = "playerTwo";
    private static final String SCORE = "score";
    private static final String MATCH_FINISHED = "matchFinished";
    @Inject
    private OngoingMatchesService ongoingMatchesService;
    @Inject
    private MatchScoreCalculationService matchScoreCalculationService;
    @Inject
    private PlayerService playerService;
    @Inject
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter(UUID);
        MatchScoreModel matchScoreModel = ongoingMatchesService.getMatch(uuid);

        request.setAttribute(PLAYER_ONE, matchScoreModel.playerOne());
        request.setAttribute(PLAYER_TWO, matchScoreModel.playerTwo());
        request.setAttribute(SCORE, matchScoreModel.score());
        request.setAttribute(UUID, uuid);

        request.getRequestDispatcher("/match-score.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter(UUID);
        MatchScoreModel matchScoreModel = ongoingMatchesService.getMatch(uuid);
        int winnerId = Integer.parseInt(request.getParameter(WINNER_ID));
        matchScoreCalculationService.updateMatchScore(matchScoreModel, winnerId);

        if (matchScoreCalculationService.isFinished(matchScoreModel)) {
            ongoingMatchesService.removeMatchFromOngoing(uuid);
            finishedMatchesPersistenceService.saveMatchToFinishedScoreboard(matchScoreModel, winnerId);

            request.setAttribute(MATCH_FINISHED, Boolean.TRUE);
            request.setAttribute(WINNER_NAME, playerService.getPlayerById(winnerId).getName());
        } else {
            request.setAttribute(MATCH_FINISHED, Boolean.FALSE);
        }

        request.setAttribute(PLAYER_ONE, matchScoreModel.playerOne());
        request.setAttribute(PLAYER_TWO, matchScoreModel.playerTwo());
        request.setAttribute(SCORE, matchScoreModel.score());
        request.setAttribute(UUID, uuid);

        request.getRequestDispatcher("/match-score.jsp").forward(request, response);
    }
}
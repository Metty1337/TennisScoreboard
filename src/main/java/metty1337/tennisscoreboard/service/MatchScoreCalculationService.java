package metty1337.tennisscoreboard.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metty1337.tennisscoreboard.enums.Point;
import metty1337.tennisscoreboard.model.MatchScoreModel;
import metty1337.tennisscoreboard.model.ScoreModel;

@ApplicationScoped
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class MatchScoreCalculationService {
    private static final int ONE = 1;
    private static final int GAMES_TO_WIN_SET = 6;
    private static final int REQUIRED_DELTA_FOR_GAMES_TO_WIN = 2;
    private static final int REQUIRED_DELTA_FOR_TIE_BRAKES_TO_WIN = 2;
    private static final int TIEBREAK_MIN_POINTS_TO_WIN = 7;
    private static final int GAMES_MIN_POINTS_TO_TIE_BRAKE = 6;

    public void updateMatchScore(MatchScoreModel matchScoreModel, long winnerId) {
        if (winnerId == matchScoreModel.playerOne().getId()) {
            playerOneWonPoint(matchScoreModel.score());
        } else if (winnerId == matchScoreModel.playerTwo().getId()) {
            playerTwoWonPoint(matchScoreModel.score());
        }
        if (isTieBreak(matchScoreModel.score())) {
            startTieBreak(matchScoreModel.score());
        }
    }


    public boolean isFinished(MatchScoreModel matchScoreModel) {
        ScoreModel scoreModel = matchScoreModel.score();
        int playerOneSets = scoreModel.getPlayerOneSets();
        int playerTwoSets = scoreModel.getPlayerTwoSets();

        return playerOneSets == 2 || playerTwoSets == 2;
    }

    private void playerOneWonPoint(ScoreModel scoreModel) {
        applyPoint(scoreModel, true);
    }

    private void playerTwoWonPoint(ScoreModel scoreModel) {
        applyPoint(scoreModel, false);
    }

    private void applyPoint(ScoreModel scoreModel, boolean playerOneWon) {
        Point playerOnePoint = scoreModel.getPlayerOnePoints();
        Point playerTwoPoint = scoreModel.getPlayerTwoPoints();

        if (isTieBreak(scoreModel)) {
            startTieBreak(scoreModel);
            applyTieBreakPoint(scoreModel, playerOneWon);
            checkTieBreakWin(scoreModel);
            return;
        }

        if (isDeuceSituation(playerOnePoint, playerTwoPoint)) {
            handleDeuceSituation(scoreModel, playerOneWon);
        } else {
            if (playerOneWon) {
                advancePlayerOnePoint(scoreModel);
            } else {
                advancePlayerTwoPoint(scoreModel);
            }
        }
        checkSetWin(scoreModel);
    }

    private boolean isTieBreak(ScoreModel scoreModel) {
        int playerOneGames = scoreModel.getPlayerOneGames();
        int playerTwoGames = scoreModel.getPlayerTwoGames();

        return (playerOneGames == GAMES_MIN_POINTS_TO_TIE_BRAKE && playerTwoGames == GAMES_MIN_POINTS_TO_TIE_BRAKE) || scoreModel.isTieBreak();
    }

    private void applyTieBreakPoint(ScoreModel scoreModel, boolean playerOneWon) {
        if (playerOneWon) {
            scoreModel.setPlayerOneTieBreakPoints(scoreModel.getPlayerOneTieBreakPoints() + ONE);
        } else {
            scoreModel.setPlayerTwoTieBreakPoints(scoreModel.getPlayerTwoTieBreakPoints() + ONE);
        }
    }

    private void checkTieBreakWin(ScoreModel scoreModel) {
        int playerOneTieBreakPoints = scoreModel.getPlayerOneTieBreakPoints();
        int playerTwoTieBreakPoints = scoreModel.getPlayerTwoTieBreakPoints();

        if (playerOneTieBreakPoints >= TIEBREAK_MIN_POINTS_TO_WIN && (playerOneTieBreakPoints - playerTwoTieBreakPoints >= REQUIRED_DELTA_FOR_TIE_BRAKES_TO_WIN)) {
            applyTieBreakWin(scoreModel, true);
            endTieBreak(scoreModel);
        } else if (playerTwoTieBreakPoints >= TIEBREAK_MIN_POINTS_TO_WIN && (playerTwoTieBreakPoints - playerOneTieBreakPoints >= REQUIRED_DELTA_FOR_TIE_BRAKES_TO_WIN)) {
            applyTieBreakWin(scoreModel, false);
            endTieBreak(scoreModel);
        }
    }

    private void applyTieBreakWin(ScoreModel scoreModel, boolean playerOneWon) {
        applySetWin(scoreModel, playerOneWon);
    }

    private void startTieBreak(ScoreModel scoreModel) {
        scoreModel.setTieBreak(true);
    }

    private void endTieBreak(ScoreModel scoreModel) {
        scoreModel.setTieBreak(false);
        scoreModel.setPlayerOneTieBreakPoints(0);
        scoreModel.setPlayerTwoTieBreakPoints(0);
        resetScorePoints(scoreModel);
    }

    private boolean isDeuceSituation(Point playerOnePoint, Point playerTwoPoint) {
        return (playerOnePoint == Point.FORTY && playerTwoPoint == Point.FORTY) || (playerOnePoint == Point.DEUCE && playerTwoPoint == Point.DEUCE) || (playerOnePoint == Point.ADVANTAGE || playerTwoPoint == Point.ADVANTAGE);
    }

    private void handleDeuceSituation(ScoreModel scoreModel, boolean playerOneWon) {
        Point playerOnePoint = scoreModel.getPlayerOnePoints();
        Point playerTwoPoint = scoreModel.getPlayerTwoPoints();

        if ((playerOnePoint == Point.FORTY && playerTwoPoint == Point.FORTY) || (playerOnePoint == Point.ADVANTAGE && playerTwoPoint == Point.ADVANTAGE)) {
            scoreModel.setPlayerOnePoints(Point.DEUCE);
            scoreModel.setPlayerTwoPoints(Point.DEUCE);

            playerOnePoint = scoreModel.getPlayerOnePoints();
            playerTwoPoint = scoreModel.getPlayerTwoPoints();
        }

        if (playerOnePoint == Point.DEUCE && playerTwoPoint == Point.DEUCE) {
            if (playerOneWon) {
                scoreModel.setPlayerOnePoints(Point.ADVANTAGE);
            } else {
                scoreModel.setPlayerTwoPoints(Point.ADVANTAGE);
            }
            return;
        }

        if (playerOneWon && playerOnePoint == Point.ADVANTAGE) {
            applyGameWin(scoreModel, true);
            return;
        }

        if (!playerOneWon && playerTwoPoint == Point.ADVANTAGE) {
            applyGameWin(scoreModel, false);
            return;
        }

        scoreModel.setPlayerOnePoints(Point.DEUCE);
        scoreModel.setPlayerTwoPoints(Point.DEUCE);
    }

    private void advancePlayerOnePoint(ScoreModel scoreModel) {
        Point playerOnePoint = scoreModel.getPlayerOnePoints();
        Point playerTwoPoint = scoreModel.getPlayerTwoPoints();
        if (playerOnePoint == Point.FORTY && playerTwoPoint.ordinal() < Point.FORTY.ordinal()) {
            applyGameWin(scoreModel, true);
        } else {
            scoreModel.setPlayerOnePoints(playerOnePoint.next());
        }
    }

    private void advancePlayerTwoPoint(ScoreModel scoreModel) {
        Point playerOnePoint = scoreModel.getPlayerOnePoints();
        Point playerTwoPoint = scoreModel.getPlayerTwoPoints();
        if (playerTwoPoint == Point.FORTY && playerOnePoint.ordinal() < Point.FORTY.ordinal()) {
            applyGameWin(scoreModel, false);
        } else {
            scoreModel.setPlayerTwoPoints(playerTwoPoint.next());
        }
    }

    private void resetScorePoints(ScoreModel scoreModel) {
        scoreModel.setPlayerOnePoints(Point.LOVE);
        scoreModel.setPlayerTwoPoints(Point.LOVE);
    }

    private void applyGameWin(ScoreModel scoreModel, boolean playerOneWon) {
        if (playerOneWon) {
            scoreModel.setPlayerOneGames(scoreModel.getPlayerOneGames() + ONE);
        } else {
            scoreModel.setPlayerTwoGames(scoreModel.getPlayerTwoGames() + ONE);
        }
        resetScorePoints(scoreModel);
    }

    private void checkSetWin(ScoreModel scoreModel) {
        int playerOneGames = scoreModel.getPlayerOneGames();
        int playerTwoGames = scoreModel.getPlayerTwoGames();

        if (playerOneGames >= GAMES_TO_WIN_SET && playerOneGames - playerTwoGames >= REQUIRED_DELTA_FOR_GAMES_TO_WIN) {
            applySetWin(scoreModel, true);
        } else if (playerTwoGames >= GAMES_TO_WIN_SET && playerTwoGames - playerOneGames >= REQUIRED_DELTA_FOR_GAMES_TO_WIN) {
            applySetWin(scoreModel, false);
        }
    }

    private void applySetWin(ScoreModel scoreModel, boolean playerOneWon) {
        if (playerOneWon) {
            scoreModel.setPlayerOneSets(scoreModel.getPlayerOneSets() + ONE);
        } else {
            scoreModel.setPlayerTwoSets(scoreModel.getPlayerTwoSets() + ONE);
        }
        resetScoreGames(scoreModel);
    }

    private void resetScoreGames(ScoreModel scoreModel) {
        scoreModel.setPlayerOneGames(0);
        scoreModel.setPlayerTwoGames(0);
    }
}
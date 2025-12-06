package metty1337.tennisscoreboard.service;

import metty1337.tennisscoreboard.enums.Point;
import metty1337.tennisscoreboard.model.MatchScoreModel;
import metty1337.tennisscoreboard.model.PlayerModel;
import metty1337.tennisscoreboard.model.ScoreModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MatchScoreCalculationServiceTest {
    private MatchScoreCalculationService matchScoreCalculationService;
    private PlayerModel playerOne;
    private PlayerModel playerTwo;

    @BeforeEach
    void setUp() {
        matchScoreCalculationService = new MatchScoreCalculationService();
        playerOne = new PlayerModel();
        playerOne.setId(1L);
        playerTwo = new PlayerModel();
        playerTwo.setId(2L);
    }

    @Test
    void shouldNotWinAfterDeuce() {
        ScoreModel scoreModel = ScoreModel.builder()
                .playerOnePoints(Point.FORTY)
                .playerOneGames(0)
                .playerOneSets(0)
                .playerTwoPoints(Point.FORTY)
                .playerTwoGames(0)
                .playerTwoSets(0)
                .tieBreak(Boolean.FALSE)
                .playerOneTieBreakPoints(0)
                .playerTwoTieBreakPoints(0)
                .build();

        MatchScoreModel matchScoreModel = new MatchScoreModel(playerOne, playerTwo, scoreModel);

        matchScoreCalculationService.updateMatchScore(matchScoreModel, playerOne.getId());

        int playerOneGames = scoreModel.getPlayerOneGames();

        Assertions.assertEquals(0, playerOneGames, "Player should not win game after 40 - 40 score");
    }

    @Test
    void shouldPlayerOneWinGame() {
        ScoreModel scoreModel = ScoreModel.builder()
                .playerOnePoints(Point.FORTY)
                .playerOneGames(0)
                .playerOneSets(0)
                .playerTwoPoints(Point.LOVE)
                .playerTwoGames(0)
                .playerTwoSets(0)
                .tieBreak(Boolean.FALSE)
                .playerOneTieBreakPoints(0)
                .playerTwoTieBreakPoints(0)
                .build();

        MatchScoreModel matchScoreModel = new MatchScoreModel(playerOne, playerTwo, scoreModel);

        matchScoreCalculationService.updateMatchScore(matchScoreModel, playerOne.getId());

        int playerOneGames = scoreModel.getPlayerOneGames();

        Assertions.assertEquals(1, playerOneGames, "Player one should game after 40 - 0 score");
    }

    @Test
    void shouldPlayerTwoWinGame() {
        ScoreModel scoreModel = ScoreModel.builder()
                .playerOnePoints(Point.LOVE)
                .playerOneGames(0)
                .playerOneSets(0)
                .playerTwoPoints(Point.FORTY)
                .playerTwoGames(0)
                .playerTwoSets(0)
                .tieBreak(Boolean.FALSE)
                .playerOneTieBreakPoints(0)
                .playerTwoTieBreakPoints(0)
                .build();

        MatchScoreModel matchScoreModel = new MatchScoreModel(playerOne, playerTwo, scoreModel);

        matchScoreCalculationService.updateMatchScore(matchScoreModel, playerTwo.getId());

        int playerTwoGames = scoreModel.getPlayerTwoGames();

        Assertions.assertEquals(1, playerTwoGames, "Player two should game after 0 - 40 score");
    }

    @Test
    void shouldPlayerOneWinAfterAdvantage() {
        ScoreModel scoreModel = ScoreModel.builder()
                .playerOnePoints(Point.ADVANTAGE)
                .playerOneGames(0)
                .playerOneSets(0)
                .playerTwoPoints(Point.DEUCE)
                .playerTwoGames(0)
                .playerTwoSets(0)
                .tieBreak(Boolean.FALSE)
                .playerOneTieBreakPoints(0)
                .playerTwoTieBreakPoints(0)
                .build();

        MatchScoreModel matchScoreModel = new MatchScoreModel(playerOne, playerTwo, scoreModel);

        matchScoreCalculationService.updateMatchScore(matchScoreModel, playerOne.getId());

        int playerOneGames = scoreModel.getPlayerOneGames();

        Assertions.assertEquals(1, playerOneGames, "Player one should win game after ADVANTAGE - DISADVANTAGE score");
    }

    @Test
    void shouldPlayerTwoWinAfterAdvantage() {
        ScoreModel scoreModel = ScoreModel.builder()
                .playerOnePoints(Point.DEUCE)
                .playerOneGames(0)
                .playerOneSets(0)
                .playerTwoPoints(Point.ADVANTAGE)
                .playerTwoGames(0)
                .playerTwoSets(0)
                .tieBreak(Boolean.FALSE)
                .playerOneTieBreakPoints(0)
                .playerTwoTieBreakPoints(0)
                .build();

        MatchScoreModel matchScoreModel = new MatchScoreModel(playerOne, playerTwo, scoreModel);

        matchScoreCalculationService.updateMatchScore(matchScoreModel, playerTwo.getId());

        int playerTwoGames = scoreModel.getPlayerTwoGames();

        Assertions.assertEquals(1, playerTwoGames, "Player two should win game after DISADVANTAGE - ADVANTAGE score");
    }

    @Test
    void shouldDeuceAfterLosingAdvantage() {
        ScoreModel scoreModel = ScoreModel.builder()
                .playerOnePoints(Point.DEUCE)
                .playerOneGames(0)
                .playerOneSets(0)
                .playerTwoPoints(Point.ADVANTAGE)
                .playerTwoGames(0)
                .playerTwoSets(0)
                .tieBreak(Boolean.FALSE)
                .playerOneTieBreakPoints(0)
                .playerTwoTieBreakPoints(0)
                .build();

        MatchScoreModel matchScoreModel = new MatchScoreModel(playerOne, playerTwo, scoreModel);

        matchScoreCalculationService.updateMatchScore(matchScoreModel, playerOne.getId());

        Point playerOnePoints = scoreModel.getPlayerOnePoints();
        Point playerTwoPoints = scoreModel.getPlayerTwoPoints();

        boolean isBothDeuce = playerOnePoints.equals(Point.DEUCE) && playerTwoPoints.equals(Point.DEUCE);

        Assertions.assertTrue(isBothDeuce, "Should be both deuce after losing advantage");
    }

    @Test
    void shouldStartTieBreak() {
        ScoreModel scoreModel = ScoreModel.builder()
                .playerOnePoints(Point.FORTY)
                .playerOneGames(5)
                .playerOneSets(0)
                .playerTwoPoints(Point.LOVE)
                .playerTwoGames(6)
                .playerTwoSets(0)
                .tieBreak(Boolean.FALSE)
                .playerOneTieBreakPoints(0)
                .playerTwoTieBreakPoints(0)
                .build();

        MatchScoreModel matchScoreModel = new MatchScoreModel(playerOne, playerTwo, scoreModel);

        matchScoreCalculationService.updateMatchScore(matchScoreModel, playerOne.getId());

        boolean isTieBreak = scoreModel.isTieBreak();

        Assertions.assertTrue(isTieBreak);
    }

    @Test
    void shouldPlayerOneWinTieBreak() {
        ScoreModel scoreModel = ScoreModel.builder()
                .playerOnePoints(Point.LOVE)
                .playerOneGames(6)
                .playerOneSets(0)
                .playerTwoPoints(Point.LOVE)
                .playerTwoGames(6)
                .playerTwoSets(0)
                .tieBreak(Boolean.TRUE)
                .playerOneTieBreakPoints(6)
                .playerTwoTieBreakPoints(5)
                .build();

        MatchScoreModel matchScoreModel = new MatchScoreModel(playerOne, playerTwo, scoreModel);

        matchScoreCalculationService.updateMatchScore(matchScoreModel, playerOne.getId());

        int playerOneSets = scoreModel.getPlayerOneSets();

        Assertions.assertEquals(1, playerOneSets, "Player one should win TieBreak with score 7 - 5");
    }

    @Test
    void shouldPlayerTwoWinTieBreak() {
        ScoreModel scoreModel = ScoreModel.builder()
                .playerOnePoints(Point.LOVE)
                .playerOneGames(6)
                .playerOneSets(0)
                .playerTwoPoints(Point.LOVE)
                .playerTwoGames(6)
                .playerTwoSets(0)
                .tieBreak(Boolean.TRUE)
                .playerOneTieBreakPoints(5)
                .playerTwoTieBreakPoints(6)
                .build();

        MatchScoreModel matchScoreModel = new MatchScoreModel(playerOne, playerTwo, scoreModel);

        matchScoreCalculationService.updateMatchScore(matchScoreModel, playerTwo.getId());

        int playerTwoSets = scoreModel.getPlayerTwoSets();

        Assertions.assertEquals(1, playerTwoSets, "Player two should win TieBreak with score 5 - 7");
    }

    @ParameterizedTest
    @CsvSource({
            "LOVE,FIFTEEN",
            "FIFTEEN,THIRTY",
            "THIRTY,FORTY"
    })
    void shouldProgressPointsCorrectly(Point currentPoint, Point nextPoint) {
        ScoreModel scoreModel = ScoreModel.builder()
                .playerOnePoints(currentPoint)
                .playerOneGames(0)
                .playerOneSets(0)
                .playerTwoPoints(Point.LOVE)
                .playerTwoGames(0)
                .playerTwoSets(0)
                .tieBreak(Boolean.FALSE)
                .playerOneTieBreakPoints(0)
                .playerTwoTieBreakPoints(0)
                .build();
        MatchScoreModel matchScoreModel = new MatchScoreModel(playerOne, playerTwo, scoreModel);

        matchScoreCalculationService.updateMatchScore(matchScoreModel, playerOne.getId());

        Assertions.assertEquals(nextPoint, scoreModel.getPlayerOnePoints(), "Should progress points correctly");
    }
}
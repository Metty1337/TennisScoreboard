package metty1337.tennisscoreboard.model;


public record MatchScoreModel(long playerOneId, long playerTwoId, ScoreModel score) {
}
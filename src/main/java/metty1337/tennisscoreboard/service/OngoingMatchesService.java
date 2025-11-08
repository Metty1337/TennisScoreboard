package metty1337.tennisscoreboard.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metty1337.tennisscoreboard.model.MatchScoreModel;
import metty1337.tennisscoreboard.model.PlayerModel;
import metty1337.tennisscoreboard.model.ScoreModel;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class OngoingMatchesService {
    private final Map<UUID, MatchScoreModel> ongoingMatches;
    private final PlayerService playerService;

    @Inject
    public OngoingMatchesService(PlayerService playerService) {
        this.ongoingMatches = new ConcurrentHashMap<>();
        this.playerService = playerService;
    }

    public String createMatch(String playerOneName, String playerTwoName) {
        PlayerModel playerOne = Objects.requireNonNull(playerService).findOrCreatePlayer(playerOneName);
        PlayerModel playerTwo = Objects.requireNonNull(playerService).findOrCreatePlayer(playerTwoName);

        UUID matchId = UUID.randomUUID();
        ScoreModel score = new ScoreModel(0, 0);
        MatchScoreModel matchScore = new MatchScoreModel(playerOne.getId(), playerTwo.getId(), score);
        Objects.requireNonNull(ongoingMatches).put(matchId, matchScore);

        return matchId.toString();
    }
}

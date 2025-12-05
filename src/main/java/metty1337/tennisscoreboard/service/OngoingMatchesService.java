package metty1337.tennisscoreboard.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metty1337.tennisscoreboard.enums.Point;
import metty1337.tennisscoreboard.exceptions.ExceptionMessages;
import metty1337.tennisscoreboard.exceptions.MatchDoesntExistException;
import metty1337.tennisscoreboard.model.MatchScoreModel;
import metty1337.tennisscoreboard.model.PlayerModel;
import metty1337.tennisscoreboard.model.ScoreModel;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
        //TODO: сделать метод в каком-то сервисе с инициализацией скора с нулями
        ScoreModel scoreModel = new ScoreModel(Point.LOVE, 0, 0, 0, Point.LOVE, 0, 0, 0, false);
        MatchScoreModel matchScore = new MatchScoreModel(playerOne, playerTwo, scoreModel);
        Objects.requireNonNull(ongoingMatches).put(matchId, matchScore);

        return matchId.toString();
    }

    public MatchScoreModel getMatch(String matchId) {
        UUID uuid = UUID.fromString(matchId);
        Optional<MatchScoreModel> matchScoreModel = Optional.ofNullable(Objects.requireNonNull(ongoingMatches).get(uuid));

        if (matchScoreModel.isPresent()) {
            return matchScoreModel.get();
        } else {
            throw new MatchDoesntExistException(ExceptionMessages.MATCH_DOESNT_EXIST_EXCEPTION.getMessage());
        }
    }
    
    public void removeMatchFromOngoing(String matchId) {
        UUID uuid = UUID.fromString(matchId);
        Objects.requireNonNull(ongoingMatches).remove(uuid);
    }
}

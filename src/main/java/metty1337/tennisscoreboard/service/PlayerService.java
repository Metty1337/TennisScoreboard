package metty1337.tennisscoreboard.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metty1337.tennisscoreboard.dao.PlayerDao;
import metty1337.tennisscoreboard.exceptions.ExceptionMessages;
import metty1337.tennisscoreboard.exceptions.PlayerDoesntExistException;
import metty1337.tennisscoreboard.model.PlayerModel;

import java.util.Objects;

@ApplicationScoped
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PlayerService {
    private final PlayerDao playersDao;

    @Inject
    public PlayerService(PlayerDao playersDao) {
        this.playersDao = playersDao;
    }

    public PlayerModel findOrCreatePlayer(String playerName) {
        if (isPlayerExist(playerName)) {
            return getPlayerByName(playerName);
        } else {
            return createPlayer(playerName);
        }
    }

    private boolean isPlayerExist(String playerName) {
        return Objects.requireNonNull(playersDao).existsByName(playerName);
    }

    public PlayerModel createPlayer(String playerName) {
        PlayerModel playerModel = new PlayerModel();
        playerModel.setName(playerName);
        Objects.requireNonNull(playersDao).save(playerModel);
        return playerModel;
    }

    public PlayerModel getPlayerByName(String playerName) {
        return Objects.requireNonNull(playersDao).findByName(playerName)
                .orElseThrow(() -> new PlayerDoesntExistException(ExceptionMessages.PLAYER_DOESNT_EXIST_EXCEPTION.getMessage()));
    }

    public PlayerModel getPlayerById(int playerId) {
        return Objects.requireNonNull(playersDao).findById(playerId)
                .orElseThrow(() -> new PlayerDoesntExistException(ExceptionMessages.PLAYER_DOESNT_EXIST_EXCEPTION.getMessage()));
    }
}

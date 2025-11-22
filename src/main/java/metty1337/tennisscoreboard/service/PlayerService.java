package metty1337.tennisscoreboard.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metty1337.tennisscoreboard.dao.PlayersDao;
import metty1337.tennisscoreboard.exceptions.ExceptionMessages;
import metty1337.tennisscoreboard.exceptions.PlayerDoesntExistException;
import metty1337.tennisscoreboard.model.PlayerModel;

import java.util.Objects;

@ApplicationScoped
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PlayerService {
    private final PlayersDao playersDao;

    @Inject
    public PlayerService(PlayersDao playersDao) {
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

    private PlayerModel createPlayer(String playerName) {
        PlayerModel playerModel = new PlayerModel();
        playerModel.setName(playerName);
        Objects.requireNonNull(playersDao).save(playerModel);
        return playerModel;
    }

    private PlayerModel getPlayerByName(String playerName) {
        return Objects.requireNonNull(playersDao).findByName(playerName)
                .orElseThrow(() -> new PlayerDoesntExistException(ExceptionMessages.PLAYER_DOESNT_EXIST_EXCEPTION.getMessage()));
    }
}

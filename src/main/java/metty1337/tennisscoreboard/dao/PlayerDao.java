package metty1337.tennisscoreboard.dao;

import metty1337.tennisscoreboard.model.PlayerModel;

import java.util.Optional;

public interface PlayerDao {
    boolean existsByName(String name);

    void save(PlayerModel playerModel);

    Optional<PlayerModel> findByName(String name);

    Optional<PlayerModel> findById(int id);
}

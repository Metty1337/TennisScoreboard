package metty1337.tennisscoreboard.dao;

import metty1337.tennisscoreboard.model.MatchModel;

import java.util.List;

public interface MatchDao {
    void save(MatchModel matchModel);

    List<MatchModel> findBySimilarNameWithSettings(int limit, int offset, String playerName);

    List<MatchModel> findAllWithSettings(int limit, int offset);

    List<MatchModel> findAll();
}

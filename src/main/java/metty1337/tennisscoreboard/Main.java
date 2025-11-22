package metty1337.tennisscoreboard;

import metty1337.tennisscoreboard.dao.HiberPlayersDao;
import metty1337.tennisscoreboard.dao.PlayersDao;
import metty1337.tennisscoreboard.dto.MatchScoreDto;
import metty1337.tennisscoreboard.mapper.MatchScoreMapper;
import metty1337.tennisscoreboard.model.MatchScoreModel;
import metty1337.tennisscoreboard.model.PlayerModel;
import metty1337.tennisscoreboard.model.ScoreModel;
import metty1337.tennisscoreboard.service.PlayerService;
import metty1337.tennisscoreboard.util.HibernateUtil;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        PlayersDao playersDao = new HiberPlayersDao(HibernateUtil.produceSessionFactory());
        PlayerService newMatchService = new PlayerService(playersDao);

        String name = "Jhon";
        String name2 = "Alina";
        PlayerModel player = newMatchService.findOrCreatePlayer(name);
        PlayerModel player2 = newMatchService.findOrCreatePlayer(name2);
        ScoreModel scoreModel = new ScoreModel(0, 0);
        MatchScoreModel matchScoreModel = new MatchScoreModel(player, player2, scoreModel);
        System.out.println(matchScoreModel);
        MatchScoreDto matchScoreDto = MatchScoreMapper.INSTANCE.toDto(matchScoreModel);
        System.out.println(matchScoreDto);
    }
}

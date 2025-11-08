package metty1337.tennisscoreboard;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
//        PlayersDao playersDao = new HiberPlayersDao(HibernateUtil.produceSessionFactory());
//        NewMatchService newMatchService = new NewMatchService(playersDao);
//
//        String name = "Jhon";
//        Player player = newMatchService.findOrCreatePlayer(name);
//        System.out.println(player);

        String uuid = UUID.randomUUID().toString();
        System.out.println("/match-score?uuid=$%s".formatted(uuid));
    }
}

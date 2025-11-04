package metty1337.tennisscoreboard;

import metty1337.tennisscoreboard.model.Match;
import metty1337.tennisscoreboard.model.Player;
import metty1337.tennisscoreboard.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Player player1 = new Player();
            player1.setName("George");
            session.persist(player1);
            Player player2 = new Player();
            player2.setName("Jack");
            session.persist(player2);

            Match match1 = new Match();
            match1.setPlayer1Id(player1);
            match1.setPlayer2Id(player2);
            match1.setWinnerId(player1);
            session.persist(match1);
            session.getTransaction().commit();
            session.close();
            System.out.println(match1);
        }
    }
}

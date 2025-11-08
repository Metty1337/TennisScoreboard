package metty1337.tennisscoreboard.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metty1337.tennisscoreboard.exceptions.DatabaseException;
import metty1337.tennisscoreboard.exceptions.ExceptionMessages;
import metty1337.tennisscoreboard.model.PlayerModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class HiberPlayersDao implements PlayersDao {
    private final SessionFactory sessionFactory;

    @Inject
    public HiberPlayersDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean existsByName(String name) {
        try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM PlayerModel WHERE name = :name", Long.class);
            query.setParameter("name", name);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DatabaseException(ExceptionMessages.DATABASE_EXCEPTION.getMessage(), e);
        }
    }

    @Override
    public void save(PlayerModel playerModel) {
        try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
            session.beginTransaction();
            session.persist(playerModel);
            session.getTransaction().commit();
        }
    }

    @Override
    public Optional<PlayerModel> findByName(String name) {
        try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
            return Optional.ofNullable(session.find(PlayerModel.class, name));
        }
    }
}

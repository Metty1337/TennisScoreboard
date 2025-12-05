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
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class HiberPlayerDao implements PlayerDao {
    private static final String PLAYER_EXISTS_BY_NAME = "SELECT COUNT(*) FROM PlayerModel WHERE name = :name";
    private static final String PLAYER_FIND_BY_NAME = "FROM PlayerModel WHERE name = :name";
    private static final String PLAYER_FIND_BY_ID = "FROM PlayerModel WHERE id = :id";
    private final SessionFactory sessionFactory;

    @Inject
    public HiberPlayerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean existsByName(String name) {
        try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
            Query<Long> query = session.createQuery(
                    PLAYER_EXISTS_BY_NAME,
                    Long.class
            );
            query.setParameter("name", name);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DatabaseException(ExceptionMessages.DATABASE_EXCEPTION.getMessage(), e);
        }
    }

    @Override
    public void save(PlayerModel playerModel) {
        Transaction transaction = null;
        try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
            transaction = session.beginTransaction();
            session.persist(playerModel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException(ExceptionMessages.DATABASE_EXCEPTION.getMessage(), e);
        }
    }

    @Override
    public Optional<PlayerModel> findByName(String name) {
        try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
            Query<PlayerModel> query = session.createQuery(
                    PLAYER_FIND_BY_NAME,
                    PlayerModel.class
            );
            query.setParameter("name", name);
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception e) {
            throw new DatabaseException(ExceptionMessages.DATABASE_EXCEPTION.getMessage(), e);
        }
    }

    @Override
    public Optional<PlayerModel> findById(int id) {
        try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
            Query<PlayerModel> query = session.createQuery(
                    PLAYER_FIND_BY_ID,
                    PlayerModel.class
            );
            query.setParameter("id", id);
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception e) {
            throw new DatabaseException(ExceptionMessages.DATABASE_EXCEPTION.getMessage(), e);
        }
    }
}

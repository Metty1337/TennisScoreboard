package metty1337.tennisscoreboard.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metty1337.tennisscoreboard.exceptions.DatabaseException;
import metty1337.tennisscoreboard.exceptions.ExceptionMessages;
import metty1337.tennisscoreboard.model.MatchModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class HiberMatchDao implements MatchDao {
    private static final String FIND_BY_SIMILAR_NAME = "FROM MatchModel m WHERE m.playerOne.name LIKE :name OR m.playerTwo.name LIKE :name ORDER BY m.id";
    private static final String FIND_ALL = "FROM MatchModel m ORDER BY m.id";
    private final SessionFactory sessionFactory;

    @Inject
    public HiberMatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(MatchModel matchModel) {
        Transaction transaction = null;
        try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
            transaction = session.beginTransaction();
            session.persist(matchModel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException(ExceptionMessages.DATABASE_EXCEPTION.getMessage(), e);
        }
    }

    @Override
    public List<MatchModel> findBySimilarNameWithSettings(int limit, int offset, String playerName) {
        try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
            Query<MatchModel> query = session.createQuery(FIND_BY_SIMILAR_NAME, MatchModel.class);
            query.setParameter("name", playerName);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();
        } catch (Exception e) {
            throw new DatabaseException(ExceptionMessages.DATABASE_EXCEPTION.getMessage(), e);
        }
    }

    @Override
    public List<MatchModel> findAllWithSettings(int limit, int offset) {
        try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
            Query<MatchModel> query = session.createQuery(FIND_ALL, MatchModel.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();
        } catch (Exception e) {
            throw new DatabaseException(ExceptionMessages.DATABASE_EXCEPTION.getMessage(), e);
        }
    }

    @Override
    public List<MatchModel> findAll() {
        try (Session session = Objects.requireNonNull(sessionFactory).openSession()) {
            Query<MatchModel> query = session.createQuery(FIND_ALL, MatchModel.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DatabaseException(ExceptionMessages.DATABASE_EXCEPTION.getMessage(), e);
        }
    }

}

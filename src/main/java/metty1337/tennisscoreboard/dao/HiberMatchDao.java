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

import java.util.Objects;

@ApplicationScoped
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class HiberMatchDao implements MatchDao {
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
}

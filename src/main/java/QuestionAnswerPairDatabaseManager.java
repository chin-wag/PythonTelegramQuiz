package main.java;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class QuestionAnswerPairDatabaseManager {
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("QuizUnit");
  private EntityManager em = emf.createEntityManager();
  private GameDatabaseManager gameDatabaseManager;

  QuestionAnswerPairDatabaseManager(GameDatabaseManager gameDatabaseManager) {
    this.gameDatabaseManager = gameDatabaseManager;
  }

  public void save(QuestionAnswerPair pair)
  {
    var tx = em.getTransaction();
    tx.begin();
    em.persist(pair);
    tx.commit();
  }

  public void update(QuestionAnswerPair pair)
  {
    var tx = em.getTransaction();
    tx.begin();
    em.merge(pair);
    tx.commit();
  }

  public void remove(int id) throws DataHandlingException {
    var existentGame = gameDatabaseManager.getExistent(id);
    var tx = em.getTransaction();
    tx.begin();

    if (!em.contains(existentGame)) {
      existentGame = em.merge(existentGame);
    }
    em.remove(existentGame);
    tx.commit();
  }

  public Optional<QuestionAnswerPair> get(long id) throws DataHandlingException
  {
    return Optional.ofNullable(em.find(QuestionAnswerPair.class, id));
  }

  public List<QuestionAnswerPair> getPairs(long id) throws DataHandlingException
  {
    try {
      var startIndex = gameDatabaseManager.get(id)
              .map(Game::getCurrentPairId)
              .orElse(1);
      var query = em.createQuery("select p from QuestionAnswerPair p where p.id >= :startIndex");
      query.setParameter("startIndex", startIndex);
      return query.getResultList();
    } catch (Exception e) {
      throw new DataHandlingException(e);
    }
  }

  public QuestionAnswerPair getExistent(long id) throws DataHandlingException
  {
    return get(id).orElseThrow(()->
            new DataHandlingException(String.format("Pair with id %s is not in database", id)));
  }
}

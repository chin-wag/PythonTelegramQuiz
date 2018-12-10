package main.java;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class QuestionAnswerPairDatabaseManager {
  private static EntityManagerFactory emf;
  private EntityManager em;
  private GameDatabaseManager gameDatabaseManager;
//
//  QuestionAnswerPairDatabaseManager(GameDatabaseManager gameDatabaseManager) {
//    this.gameDatabaseManager = gameDatabaseManager;
//  }

  public QuestionAnswerPairDatabaseManager(String unitName, GameDatabaseManager gameDatabaseManager) {
    emf = Persistence.createEntityManagerFactory(unitName);
    em = emf.createEntityManager();
    this.gameDatabaseManager = gameDatabaseManager;
  }

  public void save(QuestionAnswerPair pair) {
    var tx = em.getTransaction();
    tx.begin();
    em.persist(pair);
    tx.commit();
  }

//  public void update(QuestionAnswerPair pair) {
//    var tx = em.getTransaction();
//    tx.begin();
//    em.merge(pair);
//    tx.commit();
//  }

  public void update(int id, String[] arguments) {
    var tx = em.getTransaction();
    tx.begin();
    try {
      var currentPair = getExistent(id);
      currentPair.setQuestion(arguments[1]);
      currentPair.setAnswer(arguments[2]);
    } catch (DataHandlingException e) {return;}
    tx.commit();
  }

  public void remove(int id) throws DataHandlingException {
    var existent = getExistent(id);
    var tx = em.getTransaction();
    tx.begin();

    if (!em.contains(existent)) {
      existent = em.merge(existent);
    }
    em.remove(existent);
    tx.commit();
  }

  public Optional<QuestionAnswerPair> get(int id) {
    return Optional.ofNullable(em.find(QuestionAnswerPair.class, id));
  }

  public List<QuestionAnswerPair> getPairs(long id) throws DataHandlingException {
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

  public QuestionAnswerPair getExistent(int id) throws DataHandlingException {
    return get(id).orElseThrow(()->
            new DataHandlingException(String.format("Pair with id %s is not in database", id)));
  }
}

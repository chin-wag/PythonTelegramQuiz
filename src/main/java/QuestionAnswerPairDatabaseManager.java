package main.java;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class QuestionAnswerPairDatabaseManager extends DatabaseManager<QuestionAnswerPair, Integer> {
//  private static EntityManagerFactory emf;
  private EntityManager em;
  private GameDatabaseManager gameDatabaseManager;
//
//  QuestionAnswerPairDatabaseManager(GameDatabaseManager gameDatabaseManager) {
//    this.gameDatabaseManager = gameDatabaseManager;
//  }

  public QuestionAnswerPairDatabaseManager(String unitName, GameDatabaseManager gameDatabaseManager) {
//    emf = Persistence.createEntityManagerFactory(unitName);
    super(unitName);
    em = emf.createEntityManager();
    this.gameDatabaseManager = gameDatabaseManager;
  }


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

  public Optional<QuestionAnswerPair> get(Integer id) {
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
}

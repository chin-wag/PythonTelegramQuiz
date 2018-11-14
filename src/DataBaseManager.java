import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Optional;

class DataBaseManager {
  static ArrayList<QuestionAnswerPair> getData(Long id) throws DataHandlingException {
    var data = new ArrayList<QuestionAnswerPair>();
    EntityManagerFactory emf = null;
    EntityManager em = null;
    try {
      emf = Persistence.createEntityManagerFactory("QuizUnit");
      em = emf.createEntityManager();
      Integer startIndex;
      try {
        startIndex = em.find(Game.class, id).getCurrentPairId();
      } catch (NullPointerException e) {
        startIndex = 1;
      }
      var query = em.createQuery("select p from QuestionAnswerPair p where p.id >= :startIndex");
      query.setParameter("startIndex", startIndex);
      var result = query.getResultList();
      for(var elem : result){
        data.add((QuestionAnswerPair) elem);
      }
    } catch (Exception e) {
      throw new DataHandlingException(e);
    }
    finally {
      try {
        if (emf != null)
          emf.close();
        if (em != null)
          em.close();
      } catch (IllegalStateException e) {/*can't do anything*/}
    }
    return data;
  }

  static void saveGame(Game game) {
    var emf = Persistence.createEntityManagerFactory("QuizUnit");
    var em = emf.createEntityManager();

    var tx = em.getTransaction();
    tx.begin();
    em.persist(game);
    tx.commit();

    em.close();
    emf.close();
  }

  static void updateGame(Game game) {
    var emf = Persistence.createEntityManagerFactory("QuizUnit");
    var em = emf.createEntityManager();

    var tx = em.getTransaction();
    tx.begin();
    em.merge(game);
    tx.commit();

    em.close();
    emf.close();
  }

  static void removeGame(Game game) {
    var emf = Persistence.createEntityManagerFactory("QuizUnit");
    var em = emf.createEntityManager();

    var tx = em.getTransaction();
    tx.begin();
    if (!em.contains(game)) {
      game = em.merge(game);
    }

    em.remove(game);
    tx.commit();

    em.close();
    emf.close();
  }

  static Optional<Game> getExistentGame(Long id) {
    var emf = Persistence.createEntityManagerFactory("QuizUnit");
    var em = emf.createEntityManager();
    var existentGame = em.find(Game.class, id);
    em.close();
    emf.close();
    return Optional.ofNullable(existentGame);
  }
}
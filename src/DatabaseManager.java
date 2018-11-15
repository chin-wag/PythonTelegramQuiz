import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Optional;

interface DatabaseManagerInterface {
  ArrayList<QuestionAnswerPair> getData(Long id) throws DataHandlingException;
  void updateGame(Game game);
  void saveGame(Game game);
  void removeGame(Long id) throws DataHandlingException;
  Optional<Game> getGame(Long id);
  Game getExistentGame(Long id) throws DataHandlingException;
  boolean isGameExistent(Long id);
}

class DatabaseManager implements DatabaseManagerInterface {
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("QuizUnit");
  private EntityManager em = emf.createEntityManager();

  public ArrayList<QuestionAnswerPair> getData(Long id) throws DataHandlingException {
    var data = new ArrayList<QuestionAnswerPair>();
    try {
      Integer startIndex;
      try {
        startIndex = em.find(Game.class, id).getCurrentPairId();
      } catch (NullPointerException e) {
        startIndex = 1;
      }
      var query = em.createQuery("select p from QuestionAnswerPair p where p.id >= :startIndex");
      query.setParameter("startIndex", startIndex);
      data.addAll(query.getResultList());
    } catch (Exception e) {
      throw new DataHandlingException(e);
    }

    return data;
  }

  public void saveGame(Game game) {
    var tx = em.getTransaction();
    tx.begin();
    em.persist(game);
    tx.commit();
  }

  public void updateGame(Game game) {
    var tx = em.getTransaction();
    tx.begin();
    em.merge(game);
    tx.commit();
  }

  public void removeGame(Long id) throws DataHandlingException {
    var existentGame = getGame(id).orElseThrow(()->
            new DataHandlingException("Game with id " + id.toString() + " is not in database"));

    var tx = em.getTransaction();
    tx.begin();

    if (!em.contains(existentGame)) {
      existentGame = em.merge(existentGame);
    }
    em.remove(existentGame);
    tx.commit();
  }

  public Optional<Game> getGame(Long id) {
    return Optional.ofNullable(em.find(Game.class, id));
  }

  public Game getExistentGame(Long id) throws DataHandlingException {
    return getGame(id).orElseThrow(()->
            new DataHandlingException("Game with id " + id.toString() + " is not in database"));
  }

  public boolean isGameExistent(Long id) {
    return getGame(id).isPresent();
  }
}

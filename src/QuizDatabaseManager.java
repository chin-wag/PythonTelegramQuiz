import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

interface DatabaseManager {
  List<QuestionAnswerPair> getData(long id) throws DataHandlingException;
  void updateGame(Game game);
  void saveGame(Game game);
  void removeGame(long id) throws DataHandlingException;
  Optional<Game> getGame(long id);

  default Game getExistentGame(long id) throws DataHandlingException {
    return getGame(id).orElseThrow(()->
            new DataHandlingException("Game with id " + id + " is not in database"));
  }

  default boolean isGameExistent(long id)  {
    return getGame(id).isPresent();
  }
}

class QuizDatabaseManager implements DatabaseManager {
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("QuizUnit");
  private EntityManager em = emf.createEntityManager();

  public List<QuestionAnswerPair> getData(long id) throws DataHandlingException {
    var data = new ArrayList<QuestionAnswerPair>();
    try {
      var startIndex = getGame(id)
              .map(Game::getCurrentPairId)
              .orElse(1);
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

  public void removeGame(long id) throws DataHandlingException {
    var existentGame = getExistentGame(id);
    var tx = em.getTransaction();
    tx.begin();

    if (!em.contains(existentGame)) {
      existentGame = em.merge(existentGame);
    }
    em.remove(existentGame);
    tx.commit();
  }

  public Optional<Game> getGame(long id) {
    return Optional.ofNullable(em.find(Game.class, id));
  }
}

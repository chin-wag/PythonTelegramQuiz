package main.java;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class GameDatabaseManager {
  private static EntityManagerFactory emf;
  private EntityManager em;

  public GameDatabaseManager(String unitName) {
    emf = Persistence.createEntityManagerFactory(unitName);
    em = emf.createEntityManager();
  }

  public void save(Game game) {
    var tx = em.getTransaction();
    tx.begin();
    em.persist(game);
    tx.commit();
  }

  public void update(Game game)
  {
    var tx = em.getTransaction();
    tx.begin();
    em.merge(game);
    tx.commit();
  }

  public void remove(long id) throws DataHandlingException
  {
    var existentGame = getExistent(id);
    var tx = em.getTransaction();
    tx.begin();

    if (!em.contains(existentGame)) {
      existentGame = em.merge(existentGame);
    }
    em.remove(existentGame);
    tx.commit();
  }

  public Optional<Game> get(long id)
  {
    return Optional.ofNullable(em.find(Game.class, id));
  }

  Game getExistent(long id) throws DataHandlingException
  {
    return get(id).orElseThrow(()->
            new DataHandlingException(String.format("Game with id %s is not in database", id)));
  }

  public boolean isExistent(long id)  {
    return get(id).isPresent();
  }
}

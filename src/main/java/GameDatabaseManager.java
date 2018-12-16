package main.java;

import javax.persistence.EntityManager;
import java.util.Optional;

public class GameDatabaseManager extends DatabaseManager<Game, Long> {
  private EntityManager em;

  public GameDatabaseManager(String unitName) {
    super(unitName);
    em = emf.createEntityManager();
  }


//  public void update(Game game)
//  {
//    var tx = em.getTransaction();
//    tx.begin();
//    em.merge(game);
//    tx.commit();
//  }

  public Optional<Game> get(Long id)
  {
    return Optional.ofNullable(em.find(Game.class, id));
  }

  public boolean isExistent(long id)  {
    return get(id).isPresent();
  }
}

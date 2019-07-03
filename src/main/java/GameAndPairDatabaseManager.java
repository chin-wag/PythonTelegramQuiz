package main.java;

import javax.persistence.EntityManager;
import java.util.Optional;

abstract class GameAndPairDatabaseManager<T1, T2> extends DatabaseManager {
  EntityManager em;

  GameAndPairDatabaseManager(String unitName) {
    super(unitName);
    em = emf.createEntityManager();
  }

  void save(T1 obj) {
    var tx = em.getTransaction();
    tx.begin();
    em.persist(obj);
    tx.commit();
  }

  public void remove(T2 id) throws DataHandlingException {
    var existent = getExistent(id);
    var tx = em.getTransaction();
    tx.begin();

    if (!em.contains(existent)) {
      existent = em.merge(existent);
    }
    em.remove(existent);
    tx.commit();
  }

  abstract Optional<T1> get(T2 id);

  T1 getExistent(T2 id) throws DataHandlingException {
    return get(id).orElseThrow(()->
            new DataHandlingException(String.format("Id %s is not in database", id)));
  }
}

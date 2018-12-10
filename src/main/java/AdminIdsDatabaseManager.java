package main.java;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AdminIdsDatabaseManager {
  private static EntityManagerFactory emf;
  private EntityManager em;

  AdminIdsDatabaseManager(String unitName) {
    emf = Persistence.createEntityManagerFactory(unitName);
    em = emf.createEntityManager();
  }

  public boolean isAdminId(long id) {
    try {
      return em.find(AdminId.class, id) != null;
    } catch (Exception e) {
      return false;
    }
  }
}

package main.java;

import javax.persistence.EntityManager;

class AdminIdsDatabaseManager extends DatabaseManager {
  private EntityManager em;

  AdminIdsDatabaseManager(String unitName) {
    super(unitName);
    em = emf.createEntityManager();
  }

  boolean isAdminId(long id) {
    try {
      return em.find(AdminId.class, id) != null;
    } catch (Exception e) {
      return false;
    }
  }
}

package main.java;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AdminIdsDatabaseManager {
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("QuizUnit");
  private EntityManager em = emf.createEntityManager();

  public boolean isAdminId(long id) {
    try {
      return em.find(AdminId.class, id) != null;
    } catch (Exception e) {
      return false;
    }
  }


  public void saveGame(AdminId adminId) {
    var tx = em.getTransaction();
    tx.begin();
    em.persist(adminId);
    tx.commit();
  }

}

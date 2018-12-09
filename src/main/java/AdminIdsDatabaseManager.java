package main.java;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AdminsIdsDatabaseManager {
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("QuizUnit");
  private EntityManager em = emf.createEntityManager();

  public boolean isAdminId(long id) {
    try {
      return em.find(AdminsId.class, id) != null;
    } catch (Exception e) {
      return false;
    }
  }


  public void saveGame(AdminsId adminsId) {
    var tx = em.getTransaction();
    tx.begin();
    em.persist(adminsId);
    tx.commit();
  }

}

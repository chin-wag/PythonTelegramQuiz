package main.java;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

abstract class DatabaseManager {
  static EntityManagerFactory emf;

  DatabaseManager(String unitName) {
    if (emf == null) {
      emf = Persistence.createEntityManagerFactory(unitName);
    }
  }
}

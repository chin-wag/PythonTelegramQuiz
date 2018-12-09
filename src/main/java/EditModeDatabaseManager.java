package main.java;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

class EditModeDatabaseManager {
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("QuizUnit");
  private EntityManager em = emf.createEntityManager();

  public List<QuestionAnswerPair> getQuestionAnswerPairs() throws DataHandlingException {
    try {
      return em.createQuery("select p from QuestionAnswerPair p").getResultList();
    } catch (Exception e) {
      throw new DataHandlingException(e);
    }
  }

  public void saveQuestionAnswerPair(QuestionAnswerPair pair) {
    var tx = em.getTransaction();
    tx.begin();
    em.persist(pair);
    tx.commit();
  }

  public void updateQuestionAnswerPair(int id, String[] arguments) {
    var tx = em.getTransaction();
    tx.begin();
    try {
      var currentPair = getExistentQuestionAnswerPair(id);
      currentPair.setQuestion(arguments[1]);
      currentPair.setAnswer(arguments[2]);
//      em.merge(currentPair);
    } catch (DataHandlingException e) {return;}
    tx.commit();
  }

  public void removeQuestionAnswerPair(int id) throws DataHandlingException {
    var tx = em.getTransaction();
    tx.begin();
    var existentGame = getExistentQuestionAnswerPair(id);

    if (!em.contains(existentGame)) {
      existentGame = em.merge(existentGame);
    }
    em.remove(existentGame);
    tx.commit();
  }

  QuestionAnswerPair getExistentQuestionAnswerPair(int id) throws DataHandlingException {
    return getQuestionAnswerPair(id).orElseThrow(()->
            new DataHandlingException(String.format("Pair with id %s is not in database", id)));
  }

  Optional<QuestionAnswerPair> getQuestionAnswerPair(int id) {
    return Optional.ofNullable(em.find(QuestionAnswerPair.class, id));
  }
}
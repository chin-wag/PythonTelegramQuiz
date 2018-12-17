package main.java;


import java.util.List;
import java.util.Optional;

public class QuestionAnswerPairDatabaseManager extends GameAndPairDatabaseManager<QuestionAnswerPair, Integer> {

  public QuestionAnswerPairDatabaseManager(String unitName) {
    super(unitName);
  }

  void update(int id, String[] arguments) throws DataHandlingException {
    var tx = em.getTransaction();
    tx.begin();
    var currentPair = getExistent(id);
    currentPair.setQuestion(arguments[1]);
    currentPair.setAnswer(arguments[2]);
    tx.commit();
  }

  public Optional<QuestionAnswerPair> get(Integer id) {
    return Optional.ofNullable(em.find(QuestionAnswerPair.class, id));
  }

  List<QuestionAnswerPair> getPairs(int startIndex) throws DataHandlingException {
    try {
      var query = em.createQuery("select p from QuestionAnswerPair p where p.id >= :startIndex");
      query.setHint("eclipselink.refresh", "true");
      query.setParameter("startIndex", startIndex);
      return query.getResultList();
    } catch (Exception e) {
      throw new DataHandlingException(e);
    }
  }
}

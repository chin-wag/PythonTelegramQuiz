import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

interface QuestionManagerInterface {
  Optional<QuestionAnswerPair> getNextPair();
}

class QuestionManager implements QuestionManagerInterface {
  private List<QuestionAnswerPair> data = new ArrayList<>();
  private ListIterator<QuestionAnswerPair> questionIterator;

  QuestionManager() throws DataHandlingException{
    handleData();
    questionIterator = data.listIterator();
  }

  public Optional<QuestionAnswerPair> getNextPair() {
    return  questionIterator.hasNext() ? Optional.of(questionIterator.next()) : Optional.empty();
  }

  private void handleData() throws DataHandlingException{
    EntityManagerFactory emf = null;
    EntityManager em = null;
    try {
      emf = Persistence.createEntityManagerFactory("QuizUnit");
      em = emf.createEntityManager();
      var result = em.createQuery("select p from QuestionAnswerPair p").getResultList();
      for(var elem : result){
        data.add((QuestionAnswerPair) elem);
      }
    } catch (Exception e) {
      throw new DataHandlingException(e);
    }
    finally {
      try {
      if (emf != null)
        emf.close();
      if (em != null)
        em.close();
      } catch (IllegalStateException e) {/*can't do anything*/}
    }
  }
}


import java.io.Serializable;
import java.util.ListIterator;
import java.util.Optional;

interface QuestionManagerInterface extends Serializable {
  Optional<QuestionAnswerPair> getNextPair();
}

class QuestionManager implements QuestionManagerInterface {
  private ListIterator<QuestionAnswerPair> questionIterator;

  QuestionManager(Long id) throws DataHandlingException{
    var data = DataBaseManager.getData(id);
    questionIterator = data.listIterator();
  }

  QuestionManager() throws DataHandlingException {
    this((long)1);
  }

  public Optional<QuestionAnswerPair> getNextPair() {
    return  questionIterator.hasNext() ? Optional.of(questionIterator.next()) : Optional.empty();
  }
}


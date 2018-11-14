import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

interface QuestionManagerInterface extends Serializable {
  Optional<QuestionAnswerPair> getNextPair();
}

class QuestionManager implements QuestionManagerInterface {
  private ArrayList<QuestionAnswerPair> questions;
  private Integer currentIndex;

  QuestionManager(Long id) throws DataHandlingException{
    questions = DataBaseManager.getData(id);
    currentIndex = 0;
  }

  QuestionManager() throws DataHandlingException {
    this((long)1);
  }

  public Optional<QuestionAnswerPair> getNextPair() {
    Optional<QuestionAnswerPair> result = questions.size() > currentIndex
            ? Optional.of(questions.get(currentIndex))
            : Optional.empty();
    currentIndex++;
    return result;
  }
}


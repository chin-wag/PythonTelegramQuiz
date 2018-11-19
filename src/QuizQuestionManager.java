import java.io.Serializable;
import java.util.List;
import java.util.Optional;

interface QuestionManagerInterface extends Serializable {
  Optional<QuestionAnswerPair> getNextPair();
}

class QuestionManager implements QuestionManagerInterface {
  private List<QuestionAnswerPair> questions;
  private int currentIndex;

  QuestionManager(Long id, DatabaseManagerInterface dataBaseManager) throws DataHandlingException {
    questions = dataBaseManager.getData(id);
    currentIndex = 0;
  }

  QuestionManager(DatabaseManager dataBaseManager) throws DataHandlingException {
    this((long)1, dataBaseManager);
  }

  public Optional<QuestionAnswerPair> getNextPair() {
    Optional<QuestionAnswerPair> result = questions.size() > currentIndex
            ? Optional.of(questions.get(currentIndex))
            : Optional.empty();
    currentIndex++;
    return result;
  }
}
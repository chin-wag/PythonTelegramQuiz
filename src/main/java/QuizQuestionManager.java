package main.java;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

class QuizQuestionManager implements QuestionManager {
  private List<QuestionAnswerPair> questions;
  private int currentIndex;

  QuizQuestionManager(long id, DatabaseManager dataBaseManager) throws DataHandlingException {
    questions = dataBaseManager.getData(id);
    currentIndex = 0;
  }

  QuizQuestionManager(QuizDatabaseManager dataBaseManager) throws DataHandlingException {
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

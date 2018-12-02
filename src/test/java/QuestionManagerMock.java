package test.java;

import java.util.Optional;
import main.java.*;

class QuestionManagerMock implements QuestionManager {
  private QuestionAnswerPair pair;

  QuestionManagerMock(QuestionAnswerPair questionAnswerPair) {
    pair = questionAnswerPair;
  }

  public Optional<QuestionAnswerPair> getNextPair() {
    return Optional.of(pair);
  }
}

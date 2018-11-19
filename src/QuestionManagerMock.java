import java.util.Optional;

class QuestionManagerMock implements QuestionManager {
  private QuestionAnswerPair pair;

  QuestionManagerMock(QuestionAnswerPair questionAnswerPair) {
    pair = questionAnswerPair;
  }

  public Optional<QuestionAnswerPair> getNextPair() {
    return Optional.of(pair);
  }
}

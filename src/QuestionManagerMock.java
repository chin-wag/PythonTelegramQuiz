import java.util.Optional;

class QuestionManagerMock implements QuestionManagerInterface {
  private QuestionAnswerPair pair;

  QuestionManagerMock(QuestionAnswerPair questionAnswerPair) throws DataHandlingException {
    pair = questionAnswerPair;
  }

  public Optional<QuestionAnswerPair> getNextPair() {
    return Optional.of(pair);
  }
}

class Game {
  private Integer score = 0;
  private QuestionAnswerPair curPair;
  private QuestionManagerInterface questionManager;
  Boolean isGameContinued = true;

  Game(QuestionManagerInterface questionManager) {
    this.questionManager = questionManager;
    curPair = questionManager.getNextPair().orElse(null);
  }

  private void nextQuestion() {
    curPair = questionManager.getNextPair()
    .orElseGet(() -> {
      stopGame();
      return null;
    });
  }

  String getCurrentQuestion() {
    return curPair.getQuestion();
  }

  boolean checkAnswer(String answer) {
    if (answer.equalsIgnoreCase(curPair.getAnswer())) {
      score++;
      nextQuestion();
      return true;
    } else
      return false;
  }

  int getScore() {
    return score;
  }

  void stopGame()
  {
    isGameContinued = false;
  }
}

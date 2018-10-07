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
    if (answer.equals(curPair.getAnswer())) {
      score++;
      nextQuestion();
      return true;
    } else
      return false;
  }

  String getHelp() {
    return "Команды: /help - справка, /score - узнать количество очков, /stop - остановить викторину";
  }

  int getScore() {
    return score;
  }

  void stopGame()
  {
    isGameContinued = false;
  }
}

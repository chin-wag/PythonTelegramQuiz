class Game {
  private Integer score = 0;
  private QuestionAnswerPair curPair;
  private QuestionManager questionManager = new QuestionManager();
  Boolean isGameContinued = true;

  Game() {
    curPair = questionManager.getNextPair();
  }

  private void nextQuestion() {
    curPair = questionManager.getNextPair();
    if (curPair == null) {
      isGameContinued = false;
      stopGame();
    }
  }

  String getCurrentQuestion() {
    return curPair.question;
  }

  boolean checkAnswer(String answer) {
    if (answer.equals(curPair.answer)) {
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

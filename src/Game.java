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
      handleCommand("/stop");
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

  String handleCommand(String command) {
    String res;
    switch (command) {
      case "/score":
        res = "Ваш счет: " + getScore();
        break;
      case "/help":
        res =  getHelp();
        break;
      case "/stop":
        isGameContinued = false;
        res = "До встречи";
        break;
      default:
        res = "Неизвестная команда";
    }

    return "\n--- " + res + " ---\n";
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

import javax.persistence.*;

@Entity
class Game {
  @Id @GeneratedValue
  int id;

  private Integer score = 0;
  @OneToOne
  private QuestionAnswerPair curPair;
  @Transient
  private QuestionManagerInterface questionManager;
  Boolean isGameContinued = true;

  public Game() {

  }

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
    }

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

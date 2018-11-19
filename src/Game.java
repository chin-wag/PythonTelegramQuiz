import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
class Game {
  @Id
  private long id;
  private int score = 0;
  @OneToOne
  private QuestionAnswerPair curPair;
  private QuestionManager questionManager;
  @Transient
  private boolean isGameContinued = true;

  public Game() {}

  Game(QuestionManager questionManager, long id) {
    this.questionManager = questionManager;
    curPair = questionManager.getNextPair().orElse(null);
    this.id = id;
  }

  Game(QuestionManager questionManager) {
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

  int getCurrentPairId() {
    return curPair.getId();
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

  void stopGame() {
    isGameContinued = false;
  }

  boolean isGameContinued() { return isGameContinued; }
}

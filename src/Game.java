import javax.persistence.*;

@Entity
class Game {
  @Id
  private long id;

  private Integer score = 0;
  @OneToOne
  private QuestionAnswerPair curPair;
  @Transient
  private QuestionManagerInterface questionManager;
  @Transient
  Boolean isGameContinued = true;

  public Game() {

  }

  Game(QuestionManagerInterface questionManager, Long id, Integer score) {
    this(questionManager, id);
    this.score = score;
  }

  Game(QuestionManagerInterface questionManager, Long id) {
    this.questionManager = questionManager;
    curPair = questionManager.getNextPair().orElse(null);
    this.id = id;
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

  Integer getCurrentPairId() {
    return curPair.getId();
  }

  boolean checkAnswer(String answer) {
    if (answer.equalsIgnoreCase(curPair.getAnswer())) {
      score++;
      nextQuestion();
      DataBaseManager.updateGame(this);
      return true;
    }

    return false;
  }

  int getScore() {
    return score;
  }

  long getId() { return id; }

  void stopGame() {
    isGameContinued = false;
  }
}

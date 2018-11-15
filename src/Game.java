import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
class Game {
  @Id
  private long id;
  private Integer score = 0;
  @OneToOne
  private QuestionAnswerPair curPair;
  private QuestionManagerInterface questionManager;
  @Transient
  Boolean isGameContinued = true;
  @Transient
  private DatabaseManagerInterface dataBaseManager;

  public Game() {}

  Game(QuestionManagerInterface questionManager, Long id, DatabaseManagerInterface dataBaseManager) {
    this.questionManager = questionManager;
    curPair = questionManager.getNextPair().orElse(null);
    this.id = id;
    this.dataBaseManager = dataBaseManager;
  }

  Game(QuestionManagerInterface questionManager, DatabaseManagerInterface dataBaseManager) {
    this.questionManager = questionManager;
    curPair = questionManager.getNextPair().orElse(null);
    this.dataBaseManager = dataBaseManager;
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
      dataBaseManager.updateGame(this);
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

  void setDataBaseManager(DatabaseManagerInterface dataBaseManager) {
    this.dataBaseManager = dataBaseManager;
  }
}

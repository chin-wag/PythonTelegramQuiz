package main.java;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Game {
  @Id
  private long id;
  private int score = 0;
  @OneToOne
  private QuestionAnswerPair curPair;
  private QuestionManager questionManager;
  @Transient
  private boolean isGameContinued = true;
  boolean isEditMode = false;

  public Game() {}

  Game(QuestionManager questionManager, long id) {
    this.questionManager = questionManager;
    curPair = questionManager.getNextPair().orElse(null);
    this.id = id;
  }

  public Game(QuestionManager questionManager) {
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

  public String getCurrentQuestion() {
    return curPair.getQuestion();
  }

  public int getCurrentPairId() {
    return curPair.getId();
  }

  public boolean checkAnswer(String answer) {
    if (answer.equalsIgnoreCase(curPair.getAnswer())) {
      score++;
      nextQuestion();
      return true;
    }

    return false;
  }

  public int getScore() {
    return score;
  }

  public void stopGame() {
    isGameContinued = false;
  }

  public boolean isGameContinued() { return isGameContinued; }

  long getId() { return id; }
}

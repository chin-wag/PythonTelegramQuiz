package main.java;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
  @Id
  private long id;
  private int score = 0;
  @OneToOne
  private QuestionAnswerPair curPair;
  @ManyToMany
  private List<QuestionAnswerPair> pairs;
  private int currentPairIndex;
  @Transient
  private boolean isGameContinued = true;
  boolean isEditMode = false;

  public Game() {}

  Game(long id, QuestionAnswerPairDatabaseManager databaseManager) {
    this(databaseManager);
    this.id = id;
  }

  public Game(QuestionAnswerPairDatabaseManager databaseManager) {
    try {
      pairs = databaseManager.getPairs(id);
      currentPairIndex = 0;
    } catch (DataHandlingException e) {
      pairs = new ArrayList<>();
    }

    if (pairs.size() > 0) {
      curPair = pairs.get(currentPairIndex);
      currentPairIndex++;
    } else {
      curPair = null;
    }
  }

  private void nextQuestion() {
    if (currentPairIndex < pairs.size()) {
      curPair = pairs.get(currentPairIndex);
      currentPairIndex++;
    } else {
      stopGame();
    }
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

package test.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import main.java.*;

class GameTest {
  private static String unitName = "TestQuizUnit";
  private static QuestionAnswerPairDatabaseManager questionAnswerPairDatabaseManager;
  private static GameDatabaseManager gameDatabaseManager;

  @BeforeEach
  void initialize() {
    gameDatabaseManager = new GameDatabaseManager(unitName);
    questionAnswerPairDatabaseManager = new QuestionAnswerPairDatabaseManager(unitName);
  }

  @Test
  void testGetScore() {
    var game = new Game(questionAnswerPairDatabaseManager, gameDatabaseManager);
    assertEquals(0, game.getScore());
  }

  @Test
  void testCheckAnswer() {
      var game = new Game(questionAnswerPairDatabaseManager, gameDatabaseManager);
      assertTrue(game.checkAnswer("4"));
  }

  @Test
  void testStopGame() {
    var game = new Game(questionAnswerPairDatabaseManager, gameDatabaseManager);
    assertTrue(game.isGameContinued());

    game.stopGame();
    assertFalse(game.isGameContinued());
  }

  @Test
  void testGetCurrentQuestion() {
    var game = new Game(questionAnswerPairDatabaseManager, gameDatabaseManager);
    assertEquals("2+2", game.getCurrentQuestion());
  }
}
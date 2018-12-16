package test.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import main.java.*;

class GameTest {
  private static String unitName = "TestQuizUnit";
  private static QuestionAnswerPairDatabaseManager questionAnswerPairDatabaseManager;

  @BeforeEach
  void initialize()
  {
    questionAnswerPairDatabaseManager = new QuestionAnswerPairDatabaseManager(unitName, new GameDatabaseManager(unitName));
  }

  @Test
  void testGetScore() {
    var game = new Game(questionAnswerPairDatabaseManager);
    assertEquals(0, game.getScore());
  }

  @Test
  void testCheckAnswer() {
      var game = new Game(questionAnswerPairDatabaseManager);
      assertTrue(game.checkAnswer("4"));
  }

  @Test
  void testStopGame() {
    var game = new Game(questionAnswerPairDatabaseManager);
    assertTrue(game.isGameContinued());

    game.stopGame();
    assertFalse(game.isGameContinued());
  }

  @Test
  void testGetCurrentQuestion() {
    var game = new Game(questionAnswerPairDatabaseManager);
    assertEquals("2+2", game.getCurrentQuestion());
  }
}
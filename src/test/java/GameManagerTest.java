package test.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import main.java.*;

class GameManagerTest {
  private static GameDatabaseManager gameDatabaseManager;
  private static GameManager gameManager;
  private final long gameId = 2;

  @BeforeEach
  void initialize() {
    var unitName = "TestQuizUnit";
    gameDatabaseManager = new GameDatabaseManager(unitName);
    var questionAnswerPairDatabaseManager = new QuestionAnswerPairDatabaseManager(unitName);
    gameManager = new GameManager(gameDatabaseManager, questionAnswerPairDatabaseManager);
  }

  @AfterEach
  void afterEach() {
    try {
      if (gameDatabaseManager.isExistent(gameId))
        gameDatabaseManager.remove(gameId);
    } catch (DataHandlingException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testAddNewGame() {
    gameManager.handleUserRequest(gameId, " ");

    var game = gameDatabaseManager.get(gameId);
    assertTrue(game.isPresent());
  }

  @Test
  void testGetCurrentQuestion() {
    gameManager.handleUserRequest(gameId, " ");

    var game = gameDatabaseManager.get(gameId);
    assertEquals("2+2", game.get().getCurrentQuestion());
  }

  @Test
  void testScoreIncrement() {
    gameManager.handleUserRequest(gameId, " ");

    var game = gameDatabaseManager.get(gameId);
    assertEquals(0, game.get().getScore());

    gameManager.handleUserRequest(gameId, "4");

    game = gameDatabaseManager.get(gameId);
    assertEquals(1, game.get().getScore());
  }

  @Test
  void testWrongAnswer() {
    gameManager.handleUserRequest(gameId, " ");

    var expectedScore = gameDatabaseManager.get(gameId).get().getScore();
    var expectedPairId = gameDatabaseManager.get(gameId).get().getCurrentPairId();

    gameManager.handleUserRequest(gameId, "0");

    var game = gameDatabaseManager.get(gameId);
    assertEquals(expectedScore, game.get().getScore());
    assertEquals(expectedPairId, game.get().getCurrentPairId());
  }

  @Test
  void testNoScoreIncrementIfWrongAnswer() {
    gameManager.handleUserRequest(gameId, " ");
    gameManager.handleUserRequest(gameId, "3");

    var game = gameDatabaseManager.get(gameId);
    assertEquals(0, game.get().getScore());
  }

  @Test
  void testStopCommand() {
    gameManager.handleUserRequest(gameId, " ");
    gameManager.handleUserRequest(gameId, "/stop");

    assertFalse(gameDatabaseManager.isExistent(gameId));
  }

  @Test
  void testRemoveAfterEndOfQuestions() {
    gameManager.handleUserRequest(gameId, " ");
    gameManager.handleUserRequest(gameId, "4");
    gameManager.handleUserRequest(gameId, "6");

    assertFalse(gameDatabaseManager.isExistent(gameId));
  }
}
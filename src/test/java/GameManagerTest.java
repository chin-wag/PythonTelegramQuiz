package test.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.java.*;

class GameManagerTest {
  private static String unitName = "TestQuizUnit";
  private static GameDatabaseManager gameDatabaseManager;
  private static QuestionAnswerPairDatabaseManager questionAnswerPairDatabaseManager;
  private static GameManager gameManager;

  @BeforeEach
  void initialize()
  {
    gameDatabaseManager = new GameDatabaseManager(unitName);
    questionAnswerPairDatabaseManager = new QuestionAnswerPairDatabaseManager(unitName, gameDatabaseManager);
    gameManager = new GameManager(gameDatabaseManager, questionAnswerPairDatabaseManager);
  }

  @Test
  void testAddNewGame() throws DataHandlingException {
    gameManager.handleUserRequest((long)-2, " ");
    var game = gameDatabaseManager.get((long)-2);
    assertTrue(game.isPresent());
    gameDatabaseManager.remove((long)-2);
  }

  @Test
  void testGetCurrentQuestion() throws DataHandlingException {
    gameManager.handleUserRequest((long)-2, " ");
    var game = gameDatabaseManager.get((long)-2);
    assertEquals("2+2", game.get().getCurrentQuestion());
    gameDatabaseManager.remove((long)-2);
  }

  @Test
  void testScoreIncrement() throws DataHandlingException {
    gameManager.handleUserRequest((long)-2, " ");
    var game = gameDatabaseManager.get((long)-2);
    assertEquals(0, game.get().getScore());

    gameManager.handleUserRequest((long)-2, "4");
    game = gameDatabaseManager.get((long)-2);
    assertEquals(1, game.get().getScore());
    gameDatabaseManager.remove((long)-2);
  }

  @Test
  void testWrongAnswer() throws DataHandlingException {
    gameManager.handleUserRequest((long)-2, " ");

    var expectedScore = gameDatabaseManager.get((long)-2).get().getScore();
    var expectedPairId = gameDatabaseManager.get((long)-2).get().getCurrentPairId();

    gameManager.handleUserRequest((long)-2, "0");
    var game = gameDatabaseManager.get((long)-2);
    assertEquals(expectedScore, game.get().getScore());
    assertEquals(expectedPairId, game.get().getCurrentPairId());
    gameDatabaseManager.remove((long)-2);
  }

  @Test
  void testNoScoreIncrementIfWrongAnswer() throws DataHandlingException {
    gameManager.handleUserRequest((long)-2, " ");
    gameManager.handleUserRequest((long)-2, "3");
    var game = gameDatabaseManager.get((long)-2);
    assertEquals(0, game.get().getScore());
    gameDatabaseManager.remove((long)-2);
  }

  @Test
  void testStopCommand() throws DataHandlingException {
    gameManager.handleUserRequest((long)-2, " ");
    gameManager.handleUserRequest((long)-2, "/stop");
    assertFalse(gameDatabaseManager.isExistent((long)-2));
    if (gameDatabaseManager.isExistent((long)-2)) {
      gameDatabaseManager.remove((long)-2);
    }
  }

  @Test
  void testRemoveAfterEndOfQuestions() throws DataHandlingException {
    gameManager.handleUserRequest((long)-2, " ");
    gameManager.handleUserRequest((long)-2, "4");
    gameManager.handleUserRequest((long)-2, "6");
    assertFalse(gameDatabaseManager.isExistent((long)-2));
    if (gameDatabaseManager.isExistent((long)-2)) {
      gameDatabaseManager.remove((long)-2);
    }
  }
}
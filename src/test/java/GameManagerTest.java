package test.java;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import main.java.*;

class GameManagerTest {
  private static String unitName = "TestQuizUnit";
  private static GameDatabaseManager gameDatabaseManager = new GameDatabaseManager(unitName);
  private static QuestionAnswerPairDatabaseManager questionAnswerPairDatabaseManager =
          new QuestionAnswerPairDatabaseManager(unitName, gameDatabaseManager);

  @Test
  void testAddNewGame() {
    var gameManager = new GameManager(gameDatabaseManager, questionAnswerPairDatabaseManager);
    gameManager.handleUserRequest((long)-2, " ");
    var game = gameDatabaseManager.get((long)-2);
    assertTrue(game.isPresent());
    try {
      gameDatabaseManager.remove((long)-2);
    } catch (DataHandlingException e) {/*can't do anything*/ }
  }

  @Test
  void testGetCurrentQuestion() {
    var gameManager = new GameManager(gameDatabaseManager, questionAnswerPairDatabaseManager);
    gameManager.handleUserRequest((long)-2, " ");
    var game = gameDatabaseManager.get((long)-2);
    assertEquals("2+2", game.get().getCurrentQuestion());
    try {
      gameDatabaseManager.remove((long)-2);
    } catch (DataHandlingException e) {/*can't do anything*/ }
  }

  @Test
  void testScoreIncrement() {
    var gameManager = new GameManager(gameDatabaseManager, questionAnswerPairDatabaseManager);
    gameManager.handleUserRequest((long)-2, " ");
    var game = gameDatabaseManager.get((long)-2);
    assertEquals(0, game.get().getScore());

    gameManager.handleUserRequest((long)-2, "4");
    game = gameDatabaseManager.get((long)-2);
    assertEquals(1, game.get().getScore());
    try {
      gameDatabaseManager.remove((long)-2);
    } catch (DataHandlingException e) {/*can't do anything*/ }
  }

  @Test
  void testWrongAnswer() {
    var gameManager = new GameManager(gameDatabaseManager, questionAnswerPairDatabaseManager);
    gameManager.handleUserRequest((long)-2, " ");

    var expectedScore = gameDatabaseManager.get((long)-2).get().getScore();
    var expectedPairId = gameDatabaseManager.get((long)-2).get().getCurrentPairId();

    gameManager.handleUserRequest((long)-2, "0");
    var game = gameDatabaseManager.get((long)-2);
    assertEquals(expectedScore, game.get().getScore());
    assertEquals(expectedPairId, game.get().getCurrentPairId());
    try {
      gameDatabaseManager.remove((long)-2);
    } catch (DataHandlingException e) {/*can't do anything*/ }
  }

  @Test
  void testNoScoreIncrementIfWrongAnswer() {
    var gameManager = new GameManager(gameDatabaseManager, questionAnswerPairDatabaseManager);
    gameManager.handleUserRequest((long)-2, " ");
    gameManager.handleUserRequest((long)-2, "3");
    var game = gameDatabaseManager.get((long)-2);
    assertEquals(0, game.get().getScore());
    try {
      gameDatabaseManager.remove((long)-2);
    } catch (DataHandlingException e) {/*can't do anything*/ }
  }

  @Test
  void testStopCommand() {
    var gameManager = new GameManager(gameDatabaseManager, questionAnswerPairDatabaseManager);
    gameManager.handleUserRequest((long)-2, " ");
    gameManager.handleUserRequest((long)-2, "/stop");
    assertFalse(gameDatabaseManager.isExistent((long)-2));
    try {
      gameDatabaseManager.remove((long)-2);
    } catch (DataHandlingException e) {/*can't do anything*/ }
  }

  @Test
  void testRemoveAfterEndOfQuestions() {
    var gameManager = new GameManager(gameDatabaseManager, questionAnswerPairDatabaseManager);
    gameManager.handleUserRequest((long)-2, " ");
    gameManager.handleUserRequest((long)-2, "4");
    gameManager.handleUserRequest((long)-2, "6");
    assertFalse(gameDatabaseManager.isExistent((long)-2));
    try {
      gameDatabaseManager.remove((long)-2);
    } catch (DataHandlingException e) {/*can't do anything*/ }
  }
}
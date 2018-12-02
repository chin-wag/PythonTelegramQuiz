package test.java;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import main.java.*;

class GameManagerTest {
  private static long id = 0;

  @Test
  void testAddNewGame() {
    var dataBaseMock = new DatabaseManagerMock();
    var gameManager = new GameManager(dataBaseMock);
    gameManager.handleUserRequest(id, Optional.of(" "));
    var game = dataBaseMock.getGame(id);
    assertTrue(game.isPresent());
  }

  @Test
  void testGetCurrentQuestion() {
    var dataBaseMock = new DatabaseManagerMock();
    var gameManager = new GameManager(dataBaseMock);
    gameManager.handleUserRequest(id, Optional.of(" "));
    var game = dataBaseMock.getGame(id);
    assertEquals("2+2", game.get().getCurrentQuestion());
  }

  @Test
  void testScoreIncrement() {
    var dataBaseMock = new DatabaseManagerMock();
    var gameManager = new GameManager(dataBaseMock);
    gameManager.handleUserRequest(id, Optional.of(" "));
    var game = dataBaseMock.getGame(id);
    assertEquals(0, game.get().getScore());

    gameManager.handleUserRequest(id, Optional.of("4"));
    game = dataBaseMock.getGame(id);
    assertEquals(1, game.get().getScore());
  }

  @Test
  void testWrongAnswer() {
    var dataBaseMock = new DatabaseManagerMock();
    var gameManager = new GameManager(dataBaseMock);
    gameManager.handleUserRequest(id, Optional.of(" "));

    var expectedScore = dataBaseMock.getGame(id).get().getScore();
    var expectedPairId = dataBaseMock.getGame(id).get().getCurrentPairId();

    gameManager.handleUserRequest(id, Optional.of("0"));
    var game = dataBaseMock.getGame(id);
    assertEquals(expectedScore, game.get().getScore());
    assertEquals(expectedPairId, game.get().getCurrentPairId());
  }

  @Test
  void testNoScoreIncrementIfWrongAnswer() {
    var dataBaseMock = new DatabaseManagerMock();
    var gameManager = new GameManager(dataBaseMock);
    gameManager.handleUserRequest(id, Optional.of(" "));
    gameManager.handleUserRequest(id, Optional.of("3"));
    var game = dataBaseMock.getGame(id);
    assertEquals(0, game.get().getScore());
  }

  @Test
  void testStopCommand() {
    var dataBaseMock = new DatabaseManagerMock();
    var gameManager = new GameManager(dataBaseMock);
    gameManager.handleUserRequest(id, Optional.of(" "));
    gameManager.handleUserRequest(id, Optional.of("/stop"));
    assertFalse(dataBaseMock.isGameExistent(id));
  }

  @Test
  void testRemoveAfterEndOfQuestions() {
    var dataBaseMock = new DatabaseManagerMock();
    var gameManager = new GameManager(dataBaseMock);
    gameManager.handleUserRequest(id, Optional.of(" "));
    gameManager.handleUserRequest(id, Optional.of("4"));
    gameManager.handleUserRequest(id, Optional.of("5"));
    assertFalse(dataBaseMock.isGameExistent(id));
  }
}
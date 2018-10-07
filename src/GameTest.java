import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
  @Test
  void testGetScore() throws DataHandlingException {
    var game = new Game(new TestQuestionManager());
    assertEquals(0, game.getScore());
  }

  @Test
  void testCheckAnswer() throws DataHandlingException{
      var game = new Game(new TestQuestionManager());
      assertTrue(game.checkAnswer("Ответ"));
  }

  @Test
  void testStopGame() throws DataHandlingException {
    var game = new Game(new TestQuestionManager());
    assertTrue(game.isGameContinued);

    game.stopGame();
    assertFalse(game.isGameContinued);
  }

  @Test
  void testGetCurrentQuestion() throws DataHandlingException {
    var game = new Game(new TestQuestionManager());
    assertEquals("Вопрос", game.getCurrentQuestion());

  }
}
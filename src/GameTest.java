import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
  @Test
  void testGetScore() throws DataHandlingException {
    var game = new Game();
    assertEquals(0, game.getScore());
  }

  @Test
  void testCheckAnswer() {
    try {
      var game = new Game();
      var curPairField = game.getClass().getDeclaredField("curPair");
      curPairField.setAccessible(true);
      var answerField = curPairField.get(game).getClass().getDeclaredField("answer");
      assertTrue(game.checkAnswer(answerField.get(curPairField.get(game)).toString()));
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @Test
  void testStopGame() throws DataHandlingException {
    var game = new Game();
    assertTrue(game.isGameContinued);
    game.stopGame();
    assertFalse(game.isGameContinued);
  }

  @Test
  void testGetCurrentQuestion() throws DataHandlingException {
    var game = new Game();
    try {
      var curPairField = game.getClass().getDeclaredField("curPair");
      curPairField.setAccessible(true);
      var questionField = curPairField.get(game).getClass().getDeclaredField("question");
      assertEquals(questionField.get(curPairField.get(game)).toString(), game.getCurrentQuestion());
    } catch (Exception e) {
      System.out.println(e);
    }

  }
}
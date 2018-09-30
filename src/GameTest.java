import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
  @Test
  void testGetScore() throws DataHandlingException {
    var game = new Game();
    assertEquals(0, game.getScore());
  }

  @Test
  void testCheckAnswer() throws DataHandlingException {
    var game = new Game();
    try {
      var curPairField = game.getClass().getDeclaredField("curPair");
      curPairField.setAccessible(true);
      var cur = curPairField.get(game).getClass().getDeclaredField("answer");
      assertTrue(game.checkAnswer(cur.get(curPairField.get(game)).toString()));
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
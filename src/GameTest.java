import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
  @Test
  void testGetScore() {
    var game = new Game();
    assertEquals("0", game.getScore());
  }
}
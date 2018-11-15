import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
  @Test
  void testGetScore() {
    var game = new Game(new QuestionManagerMock(new QuestionAnswerPair("2**11", "2048")),
            new DatabaseManagerMock());
    assertEquals(0, game.getScore());
  }

  @Test
  void testCheckAnswer() {
      var game = new Game(new QuestionManagerMock(new QuestionAnswerPair("2+2*2", "6")),
              new DatabaseManagerMock());
      assertTrue(game.checkAnswer("6"));
  }

  @Test
  void testStopGame() {
    var game = new Game(new QuestionManagerMock(new QuestionAnswerPair("5*5*5", "125")),
            new DatabaseManagerMock());
    assertTrue(game.isGameContinued);

    game.stopGame();
    assertFalse(game.isGameContinued);
  }

  @Test
  void testGetCurrentQuestion() {
    var game = new Game(new QuestionManagerMock(new QuestionAnswerPair("10-1","9")),
            new DatabaseManagerMock());
    assertEquals("10-1", game.getCurrentQuestion());
  }
}
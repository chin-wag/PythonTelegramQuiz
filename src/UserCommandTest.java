import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCommandTest {
  @Test
  void testIsUserInputCommand() {
    assertTrue(UserCommand.isUserInputCommand("/score"));
    assertTrue(UserCommand.isUserInputCommand("/blablabla"));
    assertFalse(UserCommand.isUserInputCommand("score"));
  }

  @Test
  void testIsUserInputValidCommand() {
    assertTrue(UserCommand.isValidUserCommand("SCORE"));
    assertTrue(UserCommand.isValidUserCommand("HELP"));
    assertTrue(UserCommand.isValidUserCommand("STOP"));
    assertFalse(UserCommand.isValidUserCommand("BLABLABLA"));
  }

  @Test
  void testHelp() {
    try {
      assertEquals(
              "Команды: /score - узнать количество очков, /help - справка, /stop - остановить " +
              "викторину",
              UserCommand.HELP.execute(
                      new Game(new QuestionManagerMock(new QuestionAnswerPair("2+2 4")))));
    } catch (DataHandlingException e) {
      e.printStackTrace();
    }
  }

}
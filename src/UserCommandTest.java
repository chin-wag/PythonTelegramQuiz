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

}
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
    assertTrue(UserCommand.isValidUserCommand("/score"));
    assertTrue(UserCommand.isValidUserCommand("/help"));
    assertTrue(UserCommand.isValidUserCommand("/stop"));
    assertFalse(UserCommand.isValidUserCommand("/blablabla"));
  }

}
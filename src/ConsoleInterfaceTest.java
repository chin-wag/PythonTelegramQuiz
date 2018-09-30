import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleInterfaceTest {

  @Test
  void testIsUserInputCommand() {
    var consoleInterface = new ConsoleInterface();
    try {
      var isUserInputCommandMethod = consoleInterface.getClass().getDeclaredMethod("isUserInputCommand", String.class);
      isUserInputCommandMethod.setAccessible(true);

      assertTrue((Boolean) isUserInputCommandMethod.invoke(consoleInterface, "/stop"));
      assertTrue((Boolean) isUserInputCommandMethod.invoke(consoleInterface, "/smthg"));
      assertFalse((Boolean) isUserInputCommandMethod.invoke(consoleInterface, "smthg"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}

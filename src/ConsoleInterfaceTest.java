import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

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

  @Test
  void testDoStep() {
    var stdin = System.in;
    var stdout = System.out;
    try {
      var data = new ArrayList<Pair<String, String>>() {{
        add(new Pair<>("4", "true"));
        add(new Pair<>("4", "false"));
        add(new Pair<>("/score", "1"));
      }};

      for (var pair : data) {
        var outputContent = new ByteArrayOutputStream();
        System.setIn(new ByteArrayInputStream(pair.getKey().getBytes()));
        System.setOut(new PrintStream(outputContent));
        var consoleInterface = new ConsoleInterface();

        var gameField = consoleInterface.getClass().getDeclaredField("game");
        gameField.setAccessible(true);
        gameField.set(consoleInterface, new Game());

        var doStepMethod = consoleInterface.getClass().getDeclaredMethod("doStep");
        doStepMethod.setAccessible(true);
        doStepMethod.invoke(consoleInterface);

        var answer = outputContent.toString().split("[ \r\n]");
        assertEquals(pair.getValue(), answer[answer.length - 1]);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      System.setIn(stdin);
      System.setOut(stdout);
    }
  }
}

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleInterface {
  private static Scanner in = new Scanner(System.in);
  private static PrintStream out = new PrintStream(System.out);
  private static Game game = new Game();

  public static void main(String[] args) {
    salute();
    play();
  }

  private static void play() {
  }

  private static void doStep() {
  }

  private static void salute() {
  }

  private static Boolean isUserInputCommand(String input) {
    return null;
  }
}

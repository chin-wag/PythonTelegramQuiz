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
    out.println(game.getHelp());
    out.println();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }

    while (game.isGameContinued)
      doStep();

    out.println("Викторина окончена. Количество очков - " + game.getScore());
  }

  private static void doStep() {
    out.println("Вопрос: " + game.getCurrentQuestion());
    out.print("Ваш ответ: ");
    var curInput = in.nextLine();

    if (isUserInputCommand(curInput)) {
      out.println(game.handleCommand(curInput));
    }
    else {
      out.println(game.checkAnswer(curInput));
      out.println();
    }
  }

  private static void salute() {
    out.println("Это математическая викторина. Я задаю вопрос - вы отвечаете.\n" +
            "За каждый правильный ответ получаете очки. Начинаем!");
  }

  private static Boolean isUserInputCommand(String input) {
    return null;
  }
}

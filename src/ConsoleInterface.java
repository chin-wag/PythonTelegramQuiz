import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleInterface {
  private static Scanner in = new Scanner(System.in);
  private static PrintStream out = new PrintStream(System.out);
  private static Game game;

  public static void main(String[] args) {
    try {
      game = new Game(new QuestionManager());
    } catch (DataHandlingException e){
      System.out.println(e.getMessage());
      return;
    }
    salute();
    play();
  }

  private static void play() {
    UserCommand.HELP.execute(game);
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

    if (UserCommand.isUserInputCommand(curInput)) {
      handleUserCommand(curInput);
    } else {
      out.println(game.checkAnswer(curInput));
      out.println();
    }
  }

  private static void salute() {
    out.println("Это математическая викторина. Я задаю вопрос - вы отвечаете.\n" +
            "За каждый правильный ответ получаете очки. Начинаем!");
  }

  private static void handleUserCommand(String userInput){
    var userCommand = userInput.substring(1).toUpperCase();
    if(UserCommand.isValidUserCommand(userCommand)){
      var command = UserCommand.valueOf(userCommand);
      command.execute(game);
    } else {
      out.println("Команды " + userInput + " не существует");
    }
  }

  public static void printLine(String line) {
    out.println(line);
  }
}

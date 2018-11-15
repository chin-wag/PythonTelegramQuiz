import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleInterface {
  private static Scanner in = new Scanner(System.in);
  private static PrintStream out = new PrintStream(System.out);
  private static Game game;

  public static void main(String[] args) {
    var databaseManager = new DatabaseManager();
    try {
      game = new Game(new QuestionManager(databaseManager),(long)-1, databaseManager);
    } catch (DataHandlingException e){
      System.out.println(e.getMessage());
      return;
    }
    salute();
    play();

    try {
      game = null;
      databaseManager.removeGame((long)-1);
    } catch (DataHandlingException e) {/*game is already not in database*/}
  }

  private static void play() {
    out.println(UserCommand.HELP.execute(game));
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
    var currentInput = in.nextLine();

    if (UserCommand.isUserInputCommand(currentInput)) {
      handleUserCommand(currentInput);
    } else {
      out.println(game.checkAnswer(currentInput));
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
      out.println(command.execute(game));
    } else {
      out.println("Команды " + userInput + " не существует");
    }
  }
}

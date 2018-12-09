package main.java;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class ConsoleInterface {
  private static Scanner in = new Scanner(System.in);
  private static PrintStream out = new PrintStream(System.out);
  private static Game game;
  private static QuizDatabaseManager databaseManager = new QuizDatabaseManager();

  public static void main(String[] args) {
    game = new Game((long)-1, databaseManager);
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

    while (game.isGameContinued())
      doStep();

    out.println(String.format("Викторина окончена. Количество очков - %s", game.getScore()));
  }

  private static void doStep() {
    if (!game.isEditMode) {
      out.println("Вопрос: " + game.getCurrentQuestion());
      out.print("Ваш ответ: ");
    }
    var currentInput = in.nextLine();

    if (EditModeCommand.isUserInputStartingEditMode(currentInput, game) || game.isEditMode) {
      out.println(handleEditModeCommand(game, currentInput));
      return;
    }

    if (UserCommand.isUserInputCommand(currentInput)) {
      handleUserCommand(currentInput);
    } else {
      var isAnswerCorrect = game.checkAnswer(currentInput);
      if (isAnswerCorrect) {
        databaseManager.updateGame(game);
      }
      out.println(isAnswerCorrect ? Answers.CORRECT.getMessage() : Answers.INCORRECT.getMessage());
      out.println();
    }
  }

  private static void salute() {
    out.println("Это математическая викторина. Я задаю вопрос - вы отвечаете.\n" +
            "За каждый правильный ответ получаете очки. Начинаем!");
  }

  private static void handleUserCommand(String userInput){
    var userCommand = userInput.substring(1).toUpperCase();
    if (UserCommand.isValidUserCommand(userCommand)){
      var command = UserCommand.valueOf(userCommand);
      out.println(command.execute(game));
    } else {
      out.println(String.format("Команды %s не существует", userCommand));
    }
  }

  private static String handleEditModeCommand(Game game, String userInput) {
    var arguments = userInput.split(" ");
    var editModeCommand = arguments[0].substring(1).toUpperCase();
    if (EditModeCommand.isValidEditModeCommand(editModeCommand, game)) {
      var command = EditModeCommand.valueOf(editModeCommand);
      return command.execute(game, String.join(" ", Arrays.copyOfRange(arguments, 1, arguments.length)));
    } else {
      return String.format("Команды %s не существует", arguments[0]);
    }
  }
}

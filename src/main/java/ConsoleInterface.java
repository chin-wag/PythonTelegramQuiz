package main.java;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleInterface {
  private static Scanner in = new Scanner(System.in);
  private static PrintStream out = new PrintStream(System.out);
  private static Game game;
  private static GameDatabaseManager gameDatabaseManager = new GameDatabaseManager("QuizUnit");
  private static UserInputHandler userInputHandler = new UserInputHandler();

  public static void main(String[] args) {
    game = new Game((long)-1, new QuestionAnswerPairDatabaseManager("QuizUnit", gameDatabaseManager));
//    gameDatabaseManager.save(game);
    salute();
    play();

    try {
      game = null;
      gameDatabaseManager.remove((long)-1);
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

    if (userInputHandler.isUserInputCommand(currentInput, game)) {
      out.println(userInputHandler.handle(game, currentInput));
    } else {
      var isAnswerCorrect = game.checkAnswer(currentInput);
      if (isAnswerCorrect) {
        gameDatabaseManager.update(game);
      }
      out.println(isAnswerCorrect ? Answers.CORRECT.getMessage() : Answers.INCORRECT.getMessage());
      out.println();
    }
  }

  private static void salute() {
    out.println("Это математическая викторина. Я задаю вопрос - вы отвечаете.\n" +
            "За каждый правильный ответ получаете очки. Начинаем!");
  }
}

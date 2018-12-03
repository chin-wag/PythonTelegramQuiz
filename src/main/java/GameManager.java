package main.java;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GameManager {
  static List<Long> adminsIds = Arrays.asList((long)185902976, (long)219230796, (long)-1);
  private DatabaseManager databaseManager;

  public GameManager(DatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  GameManager() {
    databaseManager = new QuizDatabaseManager();
  }

  private void addNewUser(long id) throws DataHandlingException {
    var game = new Game(new QuizQuestionManager(id, databaseManager), id);
    databaseManager.saveGame(game);
  }

  private void removeUser(long id) throws DataHandlingException {
      databaseManager.removeGame(id);
  }

  private boolean isNewUser(long id) {
    return !databaseManager.isGameExistent(id);
  }

  String getQuestion(long id) throws DataHandlingException {
    return databaseManager.getExistentGame(id).getCurrentQuestion();
  }

  private String salute() {
    return "Это викторина по python. Я задаю вопрос - вы отвечаете.\n" +
            "За каждый правильный ответ получаете очки. Начинаем!";
  }

  public String handleUserRequest(long id, Optional<String> input) {
    if (isNewUser(id)) {
      try {
      addNewUser(id);
      return salute() + "\n" + UserCommand.HELP.execute(databaseManager.getGame(id).get());
      } catch (DataHandlingException e) {
        System.out.println(e.toString());
        return Answers.ERROR.getMessage();
      }
    }

    var userMessage = input.orElse("");
    var currentGame = databaseManager.getGame(id).get();
    var answer  = "";

    if (EditModeCommand.isUserInputStartingEditMode(userMessage, currentGame) || currentGame.isEditMode) {
        return handleEditModeCommand(currentGame, userMessage);
    }

    if (currentGame.isGameContinued()) {
      if (UserCommand.isUserInputCommand(userMessage)) {
        answer = handleUserCommand(currentGame, userMessage);
      } else {
        var isAnswerCorrect = currentGame.checkAnswer(userMessage);
        if (isAnswerCorrect) {
          databaseManager.updateGame(currentGame);
        }
        answer = isAnswerCorrect ? Answers.CORRECT.getMessage() : Answers.INCORRECT.getMessage();
      }
    }

    if (!currentGame.isGameContinued()) {
      try {
        answer += String.format("\nВикторина окончена. Количество очков - %s", currentGame.getScore());
        removeUser(id);
      } catch (DataHandlingException e) {
        System.out.println(e.toString());
        return Answers.ERROR.getMessage();
      }
    }

    return answer;
  }

  private String handleUserCommand(Game game, String userInput) {
    var userCommand = userInput.substring(1).toUpperCase();
    if (UserCommand.isValidUserCommand(userCommand)) {
      var command = UserCommand.valueOf(userCommand);
      return command.execute(game);
    } else {
      return String.format("Команды %s не существует", userCommand);
    }
  }

  private String handleEditModeCommand(Game game, String userInput) {
    var arguments = userInput.split(" ");
    var editModeCommand = arguments[0].substring(1).toUpperCase();
    if (EditModeCommand.isValidEditModeCommand(editModeCommand, game)) {
      var command = EditModeCommand.valueOf(editModeCommand);
      return command.execute(game, String.join(" ", Arrays.copyOfRange(arguments, 1, arguments.length)));
    } else {
      return String.format("Команды %s не существует", arguments[0]);
    }
  }

  boolean isGameContinued(long id) {
    try {
      return databaseManager.getExistentGame(id).isGameContinued();
    } catch (DataHandlingException e) {
      System.out.println(e.toString());
    }

    return false;
  }

  boolean isGameExistent(long id) {
    return databaseManager.isGameExistent(id);
  }

  boolean isGameEditMode(long id) {
    try {
      return databaseManager.getExistentGame(id).isEditMode;
    } catch (DataHandlingException e) {
      return false;
    }
  }
}

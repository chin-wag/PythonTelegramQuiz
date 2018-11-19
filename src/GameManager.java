import java.util.Optional;

class GameManager {
  private DatabaseManager databaseManager;

  GameManager(DatabaseManager databaseManager) {
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

  String handleUserRequest(long id, Optional<String> input) {
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
}

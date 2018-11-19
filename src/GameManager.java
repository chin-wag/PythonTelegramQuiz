import java.util.Optional;

class GameManager {
  private DatabaseManager databaseManager;

  GameManager(DatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  GameManager() {
    databaseManager = new QuizDatabaseManager();
  }

  private void addNewUser(long id) {
    try {
      var game = new Game(new QuizQuestionManager(id, databaseManager), id);
      databaseManager.saveGame(game);
    } catch (DataHandlingException e) {
      System.out.println(e.getMessage());
    }
  }

  private void removeUser(long id) {
    try {
      databaseManager.removeGame(id);
    } catch (DataHandlingException e) {
      System.out.println(e.getMessage());
    }
  }

  private boolean isNewUser(long id) {
    return !databaseManager.isGameExistent(id);
  }

  String getQuestion(long id) {
    try {
      return databaseManager.getExistentGame(id).getCurrentQuestion();
    } catch (DataHandlingException e) {
      System.out.println(e.getMessage());
    }

    return "";
  }

  private String salute() {
    return "Это викторина по python. Я задаю вопрос - вы отвечаете.\n" +
            "За каждый правильный ответ получаете очки. Начинаем!";
  }

  String handleUserRequest(long id, Optional<String> input) {
    if (isNewUser(id)) {
      addNewUser(id);
      return salute() + "\n" + UserCommand.HELP.execute(databaseManager.getGame(id).get());
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
      answer += "\nВикторина окончена. Количество очков - " + currentGame.getScore();
      removeUser(id);
    }

    return answer;
  }

  private String handleUserCommand(Game game, String userInput) {
    var userCommand = userInput.substring(1).toUpperCase();
    if (UserCommand.isValidUserCommand(userCommand)){
      var command = UserCommand.valueOf(userCommand);
      return command.execute(game);
    } else {
      return "Команды " + userInput + " не существует";
    }
  }

  boolean isGameContinued(long id) {
    try {
      return databaseManager.getExistentGame(id).isGameContinued();
    } catch (DataHandlingException e) {
      System.out.println(e.getMessage());
    }

    return false;
  }

  boolean isGameExistent(long id) {
    return databaseManager.isGameExistent(id);
  }
}

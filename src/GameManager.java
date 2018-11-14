import java.util.Optional;

class GameManager {
  private void addNewUser(Long id) {
      try {
        var game = new Game(new QuestionManager(id), id);
        DataBaseManager.saveGame(game);
      } catch (DataHandlingException e) {
        System.out.println(e.getMessage());
      }
  }

  private void removeUser(Long id) {
    try {
      DataBaseManager.removeGame(id);
    } catch (DataHandlingException e) {
      System.out.println(e.getMessage());
    }
  }

  private Boolean isNewUser(Long id) {
    return !DataBaseManager.isGameExistent(id);
  }

  String getQuestion(Long id) {
    try {
      return DataBaseManager.getExistentGame(id).getCurrentQuestion();
    } catch (DataHandlingException e) {
      System.out.println(e.getMessage());
    }

    return "";
  }

  private String salute() {
    return "Это викторина по python. Я задаю вопрос - вы отвечаете.\n" +
            "За каждый правильный ответ получаете очки. Начинаем!";
  }

  String handleUserRequest(Long id, Optional<String> input) {
    if (isNewUser(id)) {
      addNewUser(id);
      return salute() + "\n" + UserCommand.HELP.execute(DataBaseManager.getGame(id).get());
    }

    var userMessage = input.orElse("");

    var currentGame = DataBaseManager.getGame(id).get();
    var answer  = "";
    if (currentGame.isGameContinued) {
      if (UserCommand.isUserInputCommand(userMessage)) {
        answer = handleUserCommand(currentGame, userMessage);
      } else {
        answer = Boolean.toString(currentGame.checkAnswer(userMessage));
      }
    }

    if (!currentGame.isGameContinued) {
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

  Boolean isGameContinued(Long id) {
    try {
      return DataBaseManager.getExistentGame(id).isGameContinued;
    } catch (DataHandlingException e) {
      System.out.println(e.getMessage());
    }

    return false;
  }

  Boolean isGameExistent(Long id) {
    return DataBaseManager.isGameExistent(id);
  }
}

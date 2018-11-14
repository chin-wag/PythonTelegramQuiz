import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class GameManager {
  private Map<Long, Game> games = new HashMap<>() {};

  private void addNewUser(Long id) {
      var existentGame = DataBaseManager.getExistentGame(id);
      try {
      if (existentGame.isPresent()) {
        var game = existentGame.get();
        var resultGame = new Game(new QuestionManager(game.getId()), game.getId(), game.getScore());
        games.put(id, resultGame);
      } else {
        var resultGame = new Game(new QuestionManager(id), id);
        DataBaseManager.saveGame(resultGame);
        games.put(id, resultGame);
      }
      } catch (DataHandlingException e) {
        System.out.println(e.getMessage());
      }
  }

  private void removeUser(Long id) {
    var currentGame = games.get(id);
    games.remove(id);
    DataBaseManager.removeGame(currentGame);
  }

  private Boolean isNewUser(Long id) {
    return !games.containsKey(id);
  }

  String getQuestion(Long id) {
    return games.get(id).getCurrentQuestion();
  }

  private String salute() {
    return "Это викторина по python. Я задаю вопрос - вы отвечаете.\n" +
            "За каждый правильный ответ получаете очки. Начинаем!";
  }

  String handleUserRequest(Long id, Optional<String> input) {
    if (isNewUser(id)) {
      addNewUser(id);
      return salute() + "\n" + UserCommand.HELP.execute(games.get(id));
    }

    var userMessage = input.orElse("");

    var currentGame = games.get(id);
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
    return games.get(id).isGameContinued;
  }

  Boolean isGameExistent(Long id) {
    return games.containsKey(id);
  }

  private Integer getScore(Long id) {
    return games.get(id).getScore();
  }
}

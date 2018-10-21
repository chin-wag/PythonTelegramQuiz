import java.util.HashMap;
import java.util.Map;

class GameManager {
  private Map<Long, Game> games = new HashMap<>() {};

  private void addNewUser(Long id) {
    try {
      games.put(id, new Game(new QuestionManager()));
    } catch (DataHandlingException e) {
      System.out.println(e.getMessage());
//      TODO: обработать выход в этом случае
    }
  }

  private void removeUser(Long id) {
    games.remove(id);
  }

  private Boolean isNewUser(Long id) {
    return !games.containsKey(id);
  }

  String getQuestion(Long id) {
    return games.get(id).getCurrentQuestion();
  }

  private String salute() {
    return "Это математическая викторина. Я задаю вопрос - вы отвечаете.\n" +
            "За каждый правильный ответ получаете очки. Начинаем!";
  }

  String handleUserRequest(Long id, String input) {
    if (isNewUser(id)) {
      addNewUser(id);
      return salute() + "\n" + UserCommand.HELP.execute(games.get(id));
    }

    if (!isGameContinued(id)) {
      var score = getScore(id);
      removeUser(id);
      return "Викторина окончена. Количество очков - " + score;
    }

    if (UserCommand.isUserInputCommand(input)) {
      var answer = handleUserCommand(id, input);
      if (!isGameContinued(id)) {
        answer += "Викторина окончена. Количество очков - " + getScore(id);
        removeUser(id);
      }
      return answer;
    } else {
      var answer = Boolean.toString(games.get(id).checkAnswer(input));
      if (!isGameContinued(id)) {
        answer += "\nВикторина окончена. Количество очков - " + getScore(id);
        removeUser(id);
      }
      return answer;
    }
  }

  private String handleUserCommand(Long id, String userInput) {
    var userCommand = userInput.substring(1).toUpperCase();
    if (UserCommand.isValidUserCommand(userCommand)){
      var command = UserCommand.valueOf(userCommand);
      return command.execute(games.get(id));
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

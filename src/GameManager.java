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
      return "Викторина окончена. Количество очков - " + getScore(id);
    }

    if (UserCommand.isUserInputCommand(input)) {
      return handleUserCommand(id, input) + (isGameContinued(id)
              ? "" : "Викторина окончена. Количество очков - " + getScore(id));
    } else {
      return Boolean.toString(games.get(id).checkAnswer(input));
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

  private Integer getScore(Long id) {
    return games.get(id).getScore();
  }
}

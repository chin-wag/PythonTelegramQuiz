import java.util.HashMap;
import java.util.Map;

public class GameManager {
  private Map<Integer, Game> games = new HashMap<>() {};
  private QuestionManager questionManager;

  GameManager() {
    try {
      questionManager = new QuestionManager();
    } catch (DataHandlingException e) {
      System.out.println(e.getMessage());
//      TODO: обработать выход в этом случае
    }
  }

  private void addNewUser(Integer id) {
    games.put(id, new Game(questionManager));
  }

  private Boolean isNewUser(Integer id) {
    return games.containsKey(id);
  }

  String handleUserRequest(Integer id, String input) {
    if (isNewUser(id)) {
      addNewUser(id);
    }

    if (UserCommand.isUserInputCommand(input)) {
      return handleUserCommand(id, input);
    } else {
      return Boolean.toString(games.get(id).checkAnswer(input));
    }
  }

  private String handleUserCommand(Integer id, String userInput) {
    var userCommand = userInput.substring(1).toUpperCase();
    if (UserCommand.isValidUserCommand(userCommand)){
      var command = UserCommand.valueOf(userCommand);
      return command.execute(games.get(id));
    } else {
      return "Команды " + userInput + " не существует";
    }
  }
}

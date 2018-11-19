import java.util.ArrayList;

enum UserCommand {
  SCORE {
    String getDescription() { return "узнать количество очков"; }

    String execute(Game game) {
      return "Ваш счёт: " + game.getScore();
    }
  },
  HELP {
    String getDescription() {
      return "справка";
    }

    String execute(Game game) {
      return "Команды: " + createCommandsDescription();
    }

    private String createCommandsDescription() {
      var result = new ArrayList<String>();
      for (var userCommand : UserCommand.values()) {
        var command = userCommand.name().toLowerCase();
        var description = userCommand.getDescription();

        result.add(String.format("/%s - %s", command, description));
      }

      return String.join(", ", result);
    }
  },
  STOP {
    String getDescription() {
      return "остановить викторину";
    }

    String execute(Game game) {
      game.stopGame();
      return "Игра закончена по желанию игрока.";
    }
  };

  abstract String getDescription();

  abstract String execute(Game game);

  static boolean isUserInputCommand(String text) {
    return text.length() > 0 && text.charAt(0) == '/';
  }

  static boolean isValidUserCommand(String text){
    for (UserCommand command : UserCommand.values()) {
      if (command.name().equals(text)) {
        return true;
      }
    }

    return false;
  }
}
import java.util.ArrayList;

enum UserCommand {
  SCORE
  {
    public String description = "узнать количество очков";

    public String execute(Game game) {
      return "Ваш счёт: " + game.getScore();
    }
  },
  HELP
  {
    public String description = "справка";

    public String execute(Game game) {
      return "Команды: " + createCommandsDescription();
    }

    private String createCommandsDescription() {
      var result = new ArrayList<String>();
      for (var userCommand : UserCommand.values()) {
        var command = userCommand.name().toLowerCase();
        var description = userCommand.description;

        result.add(String.format("/%s - %s", command, description));
      }

      return String.join(", ", result);
    }
  },
  STOP {
    public String description = "остановить викторину";

    public String execute(Game game) {
      game.stopGame();
      return "Игра закончена по желанию игрока.";
    }
  };

  public String description;

  public abstract String execute(Game game);

  public static boolean isUserInputCommand(String text) {
    return text.length() > 0 && text.charAt(0) == '/';
  }

  public static Boolean isValidUserCommand(String text){
    for (UserCommand command : UserCommand.values()) {
      if (command.name().equals(text)) {
        return true;
      }
    }

    return false;
  }
}
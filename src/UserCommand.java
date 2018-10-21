import java.util.ArrayList;

enum UserCommand {
  SCORE
  {
    public String execute(Game game) {
      return "Ваш счёт: " + game.getScore();
    }

    public String getDescription() {
      return "узнать количество очков";
    }
  },
  HELP
  {
    public String execute(Game game) {
      return "Команды: " + createCommandsDescription();
    }

    public String getDescription() {
      return "справка";
    }

    private String createCommandsDescription() {
      var result = new ArrayList<String>();
      for (var userCommand : UserCommand.values()) {
        var commandBuilder = new StringBuilder();
        commandBuilder.append('/');
        commandBuilder.append(userCommand.name().toLowerCase());
        commandBuilder.append(" - ");
        commandBuilder.append(userCommand.getDescription());

        result.add(commandBuilder.toString());
      }

      return String.join(", ", result);
    }
  },
  STOP {
    public String execute(Game game) {
      game.stopGame();
      return "Игра закончена по желанию игрока.\n";
    }

    public String getDescription() {
      return "остановить викторину";
    }
  };

  public abstract String getDescription();

  public abstract String execute(Game game);

  public static boolean isUserInputCommand(String text) {
    return text.charAt(0) == '/';
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
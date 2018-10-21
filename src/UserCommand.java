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
      var result = new StringBuilder("Команды: ");
      for (UserCommand command : UserCommand.values()){
        result.append("/" + command.name().toLowerCase());
        result.append(" - ");
        result.append(command.getDescription());
        result.append(", ");
      }
      result.deleteCharAt(result.length() - 1);
      result.deleteCharAt(result.length() - 1);
      return result.toString();
    }

    public String getDescription() {
      return "справка";
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
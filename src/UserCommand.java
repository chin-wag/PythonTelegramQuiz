enum UserCommand {
  SCORE
  {
    public void execute(Game game) {
      ConsoleInterface.printLine("Ваш счёт: " + game.getScore());
    }

    public String getDescription() {
      return "узнать количество очков";
    }
  },
  HELP
  {
    public void execute(Game game) {
      var result = new StringBuilder("Команды: ");
      for (UserCommand command : UserCommand.values()){
        result.append(command.name().toLowerCase());
        result.append(" - ");
        result.append(command.getDescription());
        result.append(", ");
      }
      result.deleteCharAt(result.length() - 1);
      result.deleteCharAt(result.length() - 1);
      ConsoleInterface.printLine(result.toString());
    }

    public String getDescription() {
      return "справка";
    }
  },
  STOP {
    public void execute(Game game) {
      ConsoleInterface.printLine("Игра закончена по желанию игрока.");
      SCORE.execute(game);
      game.stopGame();
    }

    public String getDescription() {
      return "остановить викторину";
    }
  };

  public abstract String getDescription();

  public abstract void execute(Game game);

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
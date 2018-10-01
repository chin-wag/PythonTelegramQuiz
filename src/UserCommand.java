import java.io.PrintStream;

enum UserCommand {
  SCORE
  {
    public void execute(Game game, PrintStream out){
      out.println("Ваш счёт: " + game.getScore());
    }
  },
  HELP
  {
    public void execute(Game game, PrintStream out)
    {
      out.println(game.getHelp());
    }
  },
  STOP {
    public void execute(Game game, PrintStream out)
    {
      out.println("Игра закончена по желанию игрока.");
      SCORE.execute(game, out);
      game.stopGame();
    }
  };

  public abstract void execute(Game game, PrintStream out);

  public static boolean isUserCommand(String text)
  {
    return text.charAt(0) == '/';
  }
}
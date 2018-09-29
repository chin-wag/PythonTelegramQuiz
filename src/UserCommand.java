enum UserCommand {
  SCORE
  {
    public void execute(){

    }
  },
  HELP
  {
    public void execute()
    {

    }
  },
  STOP {
    public void execute()
    {

    }
  };

  public abstract void execute();
}
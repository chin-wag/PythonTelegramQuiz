package main.java;


public class UserInputHandler implements InputHandler {
  public boolean canHandle(Game game, String userMessage) {
    if (isNotInputCommand(userMessage)) {
      return false;
    }

    var arguments = userMessage.split(" ");
    var userCommand = arguments[0].substring(1).toUpperCase();
    return UserCommand.isValidUserCommand(userCommand);
  }

  public String handle(Game currentGame, String userInput) {
    var arguments = userInput.split(" ");
    var userCommand = arguments[0].substring(1).toUpperCase();
    var command = UserCommand.valueOf(userCommand);
    return command.execute(currentGame);
  }
}

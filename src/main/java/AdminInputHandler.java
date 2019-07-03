package main.java;

import java.util.Arrays;

public class AdminInputHandler implements InputHandler {
  @Override
  public boolean canHandle(Game game, String userMessage) {
    if (isNotInputCommand(userMessage) && !game.isEditMode) {
      return false;
    }

    var arguments = userMessage.split(" ");
    var userCommand = arguments[0].substring(1).toUpperCase();
    return AdminCommand.isValidEditModeCommand(userCommand, game);
  }

  @Override
  public String handle(Game game, String userMessage) {
    var arguments = userMessage.split(" ");
    var userCommand = arguments[0].substring(1).toUpperCase();
    var command = AdminCommand.valueOf(userCommand);
    return command.execute(game, String.join(" ", Arrays.copyOfRange(arguments, 1, arguments.length)));
  }
}

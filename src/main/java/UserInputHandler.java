package main.java;

import java.util.Arrays;

public class UserInputHandler {
  public String handle(Game currentGame, String userInput) {
    var arguments = userInput.split(" ");
    var userCommand = arguments[0].substring(1).toUpperCase();
    if (EditModeCommand.isValidEditModeCommand(userCommand, currentGame)) {
      var command = EditModeCommand.valueOf(userCommand);
      return command.execute(currentGame, String.join(" ", Arrays.copyOfRange(arguments, 1, arguments.length)));
    } else if (UserCommand.isValidUserCommand(userCommand)) {
      var command = UserCommand.valueOf(userCommand);
      return command.execute(currentGame);
    } else {
      return String.format("Команды %s не существует", arguments[0]);
    }
  }
}

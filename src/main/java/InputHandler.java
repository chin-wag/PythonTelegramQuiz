package main.java;


public interface InputHandler {
  boolean canHandle(Game game, String userMessage);
  String handle(Game game, String userMessage);

  default boolean isNotInputCommand(String input) {
    return input.length() == 0 || input.charAt(0) != '/';
  }
}

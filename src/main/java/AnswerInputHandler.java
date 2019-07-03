package main.java;

public class AnswerInputHandler implements InputHandler {
  @Override
  public boolean canHandle(Game game, String userMessage) {
    return isNotInputCommand(userMessage);
  }

  @Override
  public String handle(Game game, String userMessage) {
    var isAnswerCorrect = game.checkAnswer(userMessage);
    return isAnswerCorrect ? Answers.CORRECT.getMessage() : Answers.INCORRECT.getMessage();
  }
}

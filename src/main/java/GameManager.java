package main.java;

import java.util.List;
import java.util.Optional;

public class GameManager {
  private UserInputHandler userInputHandler = new UserInputHandler();
  private GameDatabaseManager gameDatabaseManager;
  private QuestionAnswerPairDatabaseManager questionAnswerPairDatabaseManager;

  public GameManager(GameDatabaseManager gameDatabaseManager, QuestionAnswerPairDatabaseManager questionAnswerPairDatabaseManager) {
    this.gameDatabaseManager = gameDatabaseManager;
    this.questionAnswerPairDatabaseManager = questionAnswerPairDatabaseManager;
  }

  GameManager() {
    gameDatabaseManager = new GameDatabaseManager("QuizUnit");
    this.questionAnswerPairDatabaseManager = new QuestionAnswerPairDatabaseManager("QuizUnit", gameDatabaseManager);
  }

  private void addNewUser(long id) {
    var game = new Game(id, questionAnswerPairDatabaseManager);
    gameDatabaseManager.save(game);
  }

  private void removeUser(long id) throws DataHandlingException {
      gameDatabaseManager.remove(id);
  }

  private boolean isNewUser(long id) {
    return !gameDatabaseManager.isExistent(id);
  }

  String getQuestion(long id) throws DataHandlingException {
    return gameDatabaseManager.getExistent(id).getCurrentQuestion();
  }

  private String salute() {
    return "Это викторина по python. Я задаю вопрос - вы отвечаете.\n" +
            "За каждый правильный ответ получаете очки. Начинаем!";
  }

  public interface InputHandler {
    boolean canHandle(String userMessage);
    String handle(Game game, String userMessage);

    default Optional<String> tryHandle(Game game, String userMessage) {
      return canHandle(userMessage) ? Optional.of(handle(game, userMessage)) : Optional.empty();
    }
  }

  public String handleUserRequest(long id, String userMessage) {
    if (isNewUser(id)) {
        addNewUser(id);
        return salute() + "\n" + UserCommand.HELP.execute(gameDatabaseManager.get(id).get());
    }

    var currentGame = gameDatabaseManager.get(id).get();
    var answer  = "";

    if (currentGame.isGameContinued()) {
//      List<InputHandler> handlers;
//      for (var h : handlers) {
//        if (h.canHandle(userMessage)) {
//          answer = h.handle(currentGame, userMessage);
//          break;
//        }
//      }
      if (userInputHandler.isUserInputCommand(userMessage, currentGame)) {
        answer = userInputHandler.handle(currentGame, userMessage);
      } else {
        var isAnswerCorrect = currentGame.checkAnswer(userMessage);
        if (isAnswerCorrect) {
          gameDatabaseManager.update(currentGame);
        }
        answer = isAnswerCorrect ? Answers.CORRECT.getMessage() : Answers.INCORRECT.getMessage();
      }
    }

    if (!currentGame.isGameContinued()) {
      try {
        answer += String.format("\nВикторина окончена. Количество очков - %s", currentGame.getScore());
        removeUser(id);
      } catch (DataHandlingException e) {
        System.out.println(e.toString());
        return Answers.ERROR.getMessage();
      }
    }

    return answer;
  }

  boolean isGameContinued(long id) {
    try {
      return gameDatabaseManager.getExistent(id).isGameContinued();
    } catch (DataHandlingException e) {
      System.out.println(e.toString());
    }

    return false;
  }

  boolean isGameExistent(long id) {
    return gameDatabaseManager.isExistent(id);
  }

  boolean isGameEditMode(long id) {
    try {
      return gameDatabaseManager.getExistent(id).isEditMode;
    } catch (DataHandlingException e) {
      return false;
    }
  }
}

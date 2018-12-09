package main.java;

public class GameManager {
  private UserInputHandler userInputHandler = new UserInputHandler();
  private GameDatabaseManager databaseManager;
  private QuestionAnswerPairDatabaseManager questionAnswerPairDatabaseManager;

  public GameManager(GameDatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
    this.questionAnswerPairDatabaseManager = new QuestionAnswerPairDatabaseManager(databaseManager);
  }

  GameManager() {
    databaseManager = new GameDatabaseManager();
    this.questionAnswerPairDatabaseManager = new QuestionAnswerPairDatabaseManager(databaseManager);
  }

  private void addNewUser(long id) {
    var game = new Game(id, questionAnswerPairDatabaseManager);
    databaseManager.save(game);
  }

  private void removeUser(long id) throws DataHandlingException {
      databaseManager.remove(id);
  }

  private boolean isNewUser(long id) {
    return !databaseManager.isGameExistent(id);
  }

  String getQuestion(long id) throws DataHandlingException {
    return databaseManager.getExistent(id).getCurrentQuestion();
  }

  private String salute() {
    return "Это викторина по python. Я задаю вопрос - вы отвечаете.\n" +
            "За каждый правильный ответ получаете очки. Начинаем!";
  }

  public String handleUserRequest(long id, String userMessage) {
    if (isNewUser(id)) {
        addNewUser(id);
        return salute() + "\n" + UserCommand.HELP.execute(databaseManager.get(id).get());
    }

    var currentGame = databaseManager.get(id).get();
    var answer  = "";

    if (currentGame.isGameContinued()) {
      if (userInputHandler.isUserInputCommand(userMessage, currentGame)) {
        answer = userInputHandler.handle(currentGame, userMessage);
      } else {
        var isAnswerCorrect = currentGame.checkAnswer(userMessage);
        if (isAnswerCorrect) {
          databaseManager.update(currentGame);
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
      return databaseManager.getExistent(id).isGameContinued();
    } catch (DataHandlingException e) {
      System.out.println(e.toString());
    }

    return false;
  }

  boolean isGameExistent(long id) {
    return databaseManager.isGameExistent(id);
  }

  boolean isGameEditMode(long id) {
    try {
      return databaseManager.getExistent(id).isEditMode;
    } catch (DataHandlingException e) {
      return false;
    }
  }
}

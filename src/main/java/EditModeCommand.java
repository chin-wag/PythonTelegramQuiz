package main.java;

import java.util.ArrayList;

public enum EditModeCommand {
  EDIT {
    String getDescription() {
      return "включить режим редактирования";
    }

    public String execute(Game game, String query) {
      game.isEditMode = true;
      return "Режим редактирования начат\n" + HELP.execute(game, query);
    }
  },
  EXIT {
    String getDescription() {
      return "выйти из режима редактирования";
    }

    public String execute(Game game, String query) {
      game.isEditMode = false;
      return "Режим редактирования закончен";
    }
  },
  GET {
    String getDescription() {
      return "получить спиок вопросов";
    }

    public String execute(Game game, String query) {
      try {
        var pairs = questionAnswerPairDatabaseManager.getPairs(game.getId());
        var answer = new StringBuilder();
        for (QuestionAnswerPair currentPair: pairs) {
          answer.append(String.format("%s. %s ; %s\n", currentPair.getId(), currentPair.getQuestion(), currentPair.getAnswer()));
        }
        return answer.toString().trim();
      } catch (DataHandlingException e) {
        return Answers.ERROR.getMessage();
      }
    }
  },
  ADD {
    String getDescription() {
      return "добавить новый вопрос; аргументы - вопрос, ответ";
    }

    public String execute(Game game, String query) {
      var arguments = query.split(" ");
      if (arguments.length != 2) {
        return "Неправильное количество аргументов";
      }
      questionAnswerPairDatabaseManager.save(new QuestionAnswerPair(arguments[0], arguments[1]));
      return "пара была добавлена";
    }
  },
  DELETE {
    String getDescription() {
      return "удалить существующий вопрос; аргументы - id";
    }

    public String execute(Game game, String query) {
      var arguments = query.split(" ");
      if (arguments.length != 1) {
        return "Неправильное количество аргументов";
      }

      try {
        var id = Integer.parseInt(arguments[0]);
        questionAnswerPairDatabaseManager.remove(id);
        return "вопрос был удален";
      } catch (Exception e) {
        return e.getMessage();
      }
    }
  },
  MODIFY {
    String getDescription() {
      return "изменить существующий вопрос; аргументы - id, вопрос, ответ";
    }

    public String execute(Game game, String query) {
      var arguments = query.split(" ");
      if (arguments.length != 3) {
        return "Неправильное количество аргументов";
      }

      try {
        var id = Integer.parseInt(arguments[0]);
        questionAnswerPairDatabaseManager.update(id, arguments);
        return "вопрос был изменен";
      } catch (Exception e) {
        return e.getMessage();
      }
    }
  },
  HELP {
    String getDescription() {
      return "справка";
    }

    public String execute(Game game, String query) {
      return "Команды: " + createCommandsDescription();
    }

    private String createCommandsDescription() {
      var result = new ArrayList<String>();
      for (var editModeCommand : EditModeCommand.values()) {
        var command = editModeCommand.name().toLowerCase();
        var description = editModeCommand.getDescription();

        result.add(String.format("/%s - %s", command, description));
      }

      return String.join(", ", result);
    }
  },;

  GameDatabaseManager gameDatabaseManager = new GameDatabaseManager("QuizUnit");
  QuestionAnswerPairDatabaseManager questionAnswerPairDatabaseManager = new QuestionAnswerPairDatabaseManager("QuizUnit", gameDatabaseManager);
  static AdminIdsDatabaseManager adminIdsDatabaseManager = new AdminIdsDatabaseManager("QuizUnit");

  abstract String getDescription();

  public abstract String execute(Game game, String query);

  public static boolean isValidEditModeCommand(String text, Game game){
    if (!(isUserInputStartingEditMode(text, game) || game.isEditMode)) {
      return false;
    }

    for (EditModeCommand command : EditModeCommand.values()) {
      if (command.name().equals(text)) {
        return true;
      }
    }

    return false;
  }

  public static boolean isUserInputStartingEditMode(String text, Game game) {
    if (!adminIdsDatabaseManager.isAdminId(game.getId())) {
      return false;
    }
    return text.startsWith(EDIT.name());
  }
}
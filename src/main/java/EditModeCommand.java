package main.java;

import java.util.ArrayList;

enum EditModeCommand {
  EDIT {
    String getDescription() {
      return "включить режим редактирования";
    }

    String execute(Game game, String query) {
      game.isEditMode = true;
      return "Режим редактирования начат\n" + HELP.execute(game, query);
    }
  },
  EXIT {
    String getDescription() {
      return "выйти из режима редактирования";
    }

    String execute(Game game, String query) {
      game.isEditMode = false;
      return "Режим редактирования закончен";
    }
  },
  GET {
    String getDescription() {
      return "получить спиок вопросов";
    }

    String execute(Game game, String query) {
      try {
        var pairs = editModeDatabaseManager.getQuestionAnswerPairs();
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
      return "добавить новый вопрос";
    }

    String execute(Game game, String query) {
      var arguments = query.split(" ");
      if (arguments.length != 2) {
        return "Неправильное количество аргументов";
      }
      editModeDatabaseManager.saveQuestionAnswerPair(new QuestionAnswerPair(arguments[0], arguments[1]));
      return "пара была добавлена";
    }
  },
  DELETE {
    String getDescription() {
      return "удалить существующий вопрос";
    }

    String execute(Game game, String query) {
      var arguments = query.split(" ");
      if (arguments.length != 1) {
        return "Неправильное количество аргументов";
      }

      try {
        var id = Integer.parseInt(arguments[0]);
        editModeDatabaseManager.removeQuestionAnswerPair(id);
        return "вопрос был удален";
      } catch (Exception e) {
        return e.getMessage();
      }
    }
  },
  MODIFY {
    String getDescription() {
      return "изменить существующий вопрос";
    }

    String execute(Game game, String query) {
      var arguments = query.split(" ");
      if (arguments.length != 3) {
        return "Неправильное количество аргументов";
      }

      try {
        var id = Integer.parseInt(arguments[0]);
        var currentPair = editModeDatabaseManager.getExistentQuestionAnswerPair(id);
        editModeDatabaseManager.updateQuestionAnswerPair(currentPair);
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

    String execute(Game game, String query) {
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

  EditModeDatabaseManager editModeDatabaseManager = new EditModeDatabaseManager();

  abstract String getDescription();

  abstract String execute(Game game, String query);

  static boolean isUserInputCommand(String text) {
    return text.length() > 0 && text.charAt(0) == '/';
  }

  static boolean isValidEditModeCommand(String text, Game game){
    if (!GameManager.adminsIds.contains(game.getId())) {
      return false;
    }

    for (EditModeCommand command : EditModeCommand.values()) {
      if (command.name().equals(text)) {
        return true;
      }
    }

    return false;
  }

  static boolean isUserInputStartingEditMode(String text, Game game) {
    if (!GameManager.adminsIds.contains(game.getId())) {
      return false;
    }
    return text.toUpperCase().substring(1).startsWith(EDIT.name());
  }
}
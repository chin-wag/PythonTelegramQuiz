package test.java;

import main.java.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminCommandTest {
  private static String unitName = "TestQuizUnit";
  private static QuestionAnswerPairDatabaseManager questionAnswerPairDatabaseManager =
          new QuestionAnswerPairDatabaseManager(unitName, new GameDatabaseManager(unitName));

  @Test
  void testIsUserInputStartAdminMode() {
    assertTrue(EditModeCommand.isUserInputStartingEditMode("EDIT", new Game((long)-1, questionAnswerPairDatabaseManager)));
  }

  @Test
  void testIsUserInputValidAdminCommand() {
    var game = new Game((long)-1, questionAnswerPairDatabaseManager);
    assertTrue(EditModeCommand.isValidEditModeCommand("EDIT", game));
    game.isEditMode = true;
    assertTrue(EditModeCommand.isValidEditModeCommand("GET", game));
    assertTrue(EditModeCommand.isValidEditModeCommand("ADD", game));
    assertTrue(EditModeCommand.isValidEditModeCommand("DELETE", game));
    assertTrue(EditModeCommand.isValidEditModeCommand("MODIFY", game));
    assertTrue(EditModeCommand.isValidEditModeCommand("HELP", game));
    assertFalse(EditModeCommand.isValidEditModeCommand("STOP", game));
    assertFalse(EditModeCommand.isValidEditModeCommand("ABRACADABRA", game));
    assertTrue(EditModeCommand.isValidEditModeCommand("EXIT", game));
    game.isEditMode = false;
    assertFalse(EditModeCommand.isValidEditModeCommand("ADD", game));
  }

  @Test
  void testHelp() {
    assertEquals(
            "Команды: /edit - включить режим редактирования, " +
                    "/exit - выйти из режима редактирования, /get - получить " +
                    "спиок вопросов, /add - добавить новый вопрос; аргументы - " +
                    "вопрос, ответ, /delete - удалить существующий вопрос; " +
                    "аргументы - id, /modify - изменить существующий вопрос; " +
                    "аргументы - id, вопрос, ответ, /help - справка",
            EditModeCommand.HELP.execute(new Game(questionAnswerPairDatabaseManager), "/help"));
  }
}

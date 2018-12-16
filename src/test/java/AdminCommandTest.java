package test.java;

import main.java.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminCommandTest {
  private static String unitName = "TestQuizUnit";
  private static QuestionAnswerPairDatabaseManager questionAnswerPairDatabaseManager =
          new QuestionAnswerPairDatabaseManager(unitName, new GameDatabaseManager(unitName));
  private static Game game = new Game((long)-1, questionAnswerPairDatabaseManager);

  @Test
  void testIsUserInputStartAdminMode() {
    assertTrue(AdminCommand.isUserInputStartingEditMode("EDIT", game));
  }

  @Test
  void testIsUserInputValidAdminCommand() {
    assertTrue(AdminCommand.isValidEditModeCommand("EDIT", game));
    game.isEditMode = true;
    assertTrue(AdminCommand.isValidEditModeCommand("GET", game));
    assertTrue(AdminCommand.isValidEditModeCommand("ADD", game));
    assertTrue(AdminCommand.isValidEditModeCommand("DELETE", game));
    assertTrue(AdminCommand.isValidEditModeCommand("MODIFY", game));
    assertTrue(AdminCommand.isValidEditModeCommand("HELP", game));
    assertFalse(AdminCommand.isValidEditModeCommand("STOP", game));
    assertFalse(AdminCommand.isValidEditModeCommand("ABRACADABRA", game));
    assertTrue(AdminCommand.isValidEditModeCommand("EXIT", game));
    game.isEditMode = false;
    assertFalse(AdminCommand.isValidEditModeCommand("ADD", game));
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
            AdminCommand.HELP.execute(game, "/help"));
  }
}

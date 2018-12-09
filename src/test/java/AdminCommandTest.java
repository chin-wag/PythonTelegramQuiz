package test.java;

import main.java.EditModeCommand;
import main.java.Game;
import main.java.UserCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminCommandTest {
  @Test
  void testIsUserInputStartAdminMode() {
    assertTrue(EditModeCommand.isUserInputStartingEditMode("/edit", new Game(new DatabaseManagerMock())));
    assertFalse(UserCommand.isUserInputCommand("admin"));
  }

  @Test
  void testIsUserInputValidAdminCommand() {
    assertTrue(EditModeCommand.isValidEditModeCommand("EDIT", new Game(new DatabaseManagerMock())));
    assertTrue(EditModeCommand.isValidEditModeCommand("EXIT", new Game(new DatabaseManagerMock())));
    assertTrue(EditModeCommand.isValidEditModeCommand("GET", new Game(new DatabaseManagerMock())));
    assertTrue(EditModeCommand.isValidEditModeCommand("ADD", new Game(new DatabaseManagerMock())));
    assertTrue(EditModeCommand.isValidEditModeCommand("DELETE", new Game(new DatabaseManagerMock())));
    assertTrue(EditModeCommand.isValidEditModeCommand("MODIFY", new Game(new DatabaseManagerMock())));
    assertTrue(EditModeCommand.isValidEditModeCommand("HELP", new Game(new DatabaseManagerMock())));
    assertFalse(EditModeCommand.isValidEditModeCommand("STOP", new Game(new DatabaseManagerMock())));
    assertFalse(EditModeCommand.isValidEditModeCommand("ABRACADABRA", new Game(new DatabaseManagerMock())));
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
            EditModeCommand.HELP.execute(new Game(new DatabaseManagerMock()), "/help"));
  }
}

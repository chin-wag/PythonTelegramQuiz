package test.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.java.*;

class UserCommandTest {
  @Test
  void testIsUserInputValidCommand() {
    assertTrue(UserCommand.isValidUserCommand("SCORE"));
    assertTrue(UserCommand.isValidUserCommand("HELP"));
    assertTrue(UserCommand.isValidUserCommand("STOP"));
    assertFalse(UserCommand.isValidUserCommand("BLABLABLA"));
  }

  @Test
  void testHelp() {
    var gameDatabaseManager = new GameDatabaseManager("TestQuizUnit");
      assertEquals(
              "Команды: /score - узнать количество очков, /help - справка, /stop - остановить " +
              "викторину",
              UserCommand.HELP.execute(new Game(new QuestionAnswerPairDatabaseManager(
                      "TestQuizUnit"), gameDatabaseManager)));
  }
}
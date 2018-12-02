package main.java;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class TelegramMain {
  public static void main(String[] args) {
    ApiContextInitializer.init();
    var telegramBotsApi = new TelegramBotsApi();
    var telegramInterface = new TelegramInterface();
    try {
      telegramBotsApi.registerBot(telegramInterface);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
}

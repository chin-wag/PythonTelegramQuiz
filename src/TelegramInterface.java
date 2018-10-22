import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class TelegramInterface extends TelegramLongPollingBot {
  private GameManager gameManager = new GameManager();

  @Override
  public void onUpdateReceived(Update update) {
    var currentId = update.getMessage().getChatId();
    System.out.println(currentId + ", " + update.getMessage().getFrom().getUserName() + ", " + update.getMessage().getText());
    var message = update.getMessage().getSticker() != null ?  "" : update.getMessage().getText();
    var answer = gameManager.handleUserRequest(currentId, message);
    sendMessageToUser(currentId, answer);
    if (gameManager.isGameExistent(currentId) && gameManager.isGameContinued(currentId)) {
      sendMessageToUser(currentId, gameManager.getQuestion(update.getMessage().getChatId()));
    }
  }

  private void sendMessageToUser(Long id, String text) {
    var sendMessage = new SendMessage();
    sendMessage.setChatId(id);
    sendMessage.setText(text);
    try {
      sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getBotUsername() {
    return "pythonQuizBot";
  }

  @Override
  public String getBotToken() {
    return "751797870:AAFogZy15LunuYlxG31iwe7jAoWTmq80tmI";
  }
}

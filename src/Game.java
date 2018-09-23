import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Game {
  private Integer score = 0;
  private List<QuestionAnswerPair> data = new ArrayList<>();
  private QuestionAnswerPair curPair;
  private Integer curPairIndex = 0;
  Boolean isGameContinued = true;

  
  String getHelp() {
    return "Команды: /help - справка, /score - узнать количество очков, /stop - остановить викторину";
  }

  String getScore() {
    return Integer.toString(score);
  }

  private void handleData() {
    try {
      var reader = new BufferedReader(new FileReader("src/data"));
      String text;

      while ((text = reader.readLine()) != null)
        data.add(new QuestionAnswerPair(text.split(" ")));
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
  }
}

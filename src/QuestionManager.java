import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class QuestionManager {
  private List<QuestionAnswerPair> data = new ArrayList<>();
  private ListIterator<QuestionAnswerPair> questionIterator;

  public QuestionManager() {
    handleData();
    questionIterator = data.listIterator();
  }

  public QuestionAnswerPair getNextPair() {
    if (questionIterator.hasNext()) {
      return questionIterator.next();
    } else {
      return null;
    }
  }
  private void handleData() {
    try {
      var reader = new BufferedReader(new FileReader("data"));
      String text;

      while ((text = reader.readLine()) != null)
        data.add(new QuestionAnswerPair(text.split(" ")));
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
  }
}

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

class QuestionManager {
  private List<QuestionAnswerPair> data = new ArrayList<>();
  private ListIterator<QuestionAnswerPair> questionIterator;

  QuestionManager() throws DataHandlingException{
    handleData();
    questionIterator = data.listIterator();
  }

  QuestionAnswerPair getNextPair() {
    if (questionIterator.hasNext()) {
      return questionIterator.next();
    } else {
      return null;
    }
  }
  private void handleData() throws DataHandlingException{
    try{
      var reader = new BufferedReader(new FileReader("data"));
      String text;

      while ((text = reader.readLine()) != null){
        var currentLine = text.split(" ");
        if(currentLine.length != 2)
          throw new DataHandlingException("Line " + text + " has more than 2 values");
        data.add(new QuestionAnswerPair(currentLine));
      }
    } catch (Exception e){
      throw new DataHandlingException(e.getMessage());
    }
  }
}

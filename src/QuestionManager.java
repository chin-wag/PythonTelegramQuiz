import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

interface QuestionManagerInterface {
  Optional<QuestionAnswerPair> getNextPair();
}

class QuestionManager implements QuestionManagerInterface {
  private List<QuestionAnswerPair> data = new ArrayList<>();
  private ListIterator<QuestionAnswerPair> questionIterator;

  QuestionManager() throws DataHandlingException{
    handleData();
    questionIterator = data.listIterator();
  }

  public Optional<QuestionAnswerPair> getNextPair() {
    return  questionIterator.hasNext() ? Optional.of(questionIterator.next()) : Optional.empty();
  }

  private void handleData() throws DataHandlingException{
    try{
      var reader = new BufferedReader(new FileReader("data"));
      String text;

      while ((text = reader.readLine()) != null){
        data.add(new QuestionAnswerPair(text));
      }
    } catch (Exception e){
      throw new DataHandlingException(e);
    }
  }
}

class TestQuestionManager implements QuestionManagerInterface {
  private QuestionAnswerPair pair;

  TestQuestionManager() throws DataHandlingException {
    pair = new QuestionAnswerPair("Вопрос Ответ");
  }

  public Optional<QuestionAnswerPair> getNextPair() {
    return Optional.of(pair);
  }
}

import java.sql.*;
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

  private static final String url = "jdbc:mysql://localhost:3306/quiz";
  private static final String user = "root";
  private static final String password = "12345";

  private Connection connection;
  private Statement statement;
  private ResultSet resultSet;

  QuestionManager() throws DataHandlingException{
    handleData();
    questionIterator = data.listIterator();
  }

  public Optional<QuestionAnswerPair> getNextPair() {
    return  questionIterator.hasNext() ? Optional.of(questionIterator.next()) : Optional.empty();
  }

  private void handleData() throws DataHandlingException{
    try{
      connection = DriverManager.getConnection(url, user, password);
      statement = connection.createStatement();
      resultSet = statement.executeQuery("select question, answer from questions_and_answers");

      while (resultSet.next()) {
        var question = resultSet.getString(1);
        var answer = resultSet.getString(2);
        data.add(new QuestionAnswerPair(question, answer));
      }
    } catch (Exception e){
      throw new DataHandlingException(e);
    } finally {
      try { if (connection != null) connection.close(); } catch(SQLException se) { /*can't do anything */ }
      try { if (statement != null) statement.close(); } catch(SQLException se) { /*can't do anything */ }
      try { if (resultSet != null) resultSet.close(); } catch(SQLException se) { /*can't do anything */ }
    }
  }
}


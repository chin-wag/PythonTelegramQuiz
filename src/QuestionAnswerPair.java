class QuestionAnswerPair
{
  private String question;
  private String answer;

  QuestionAnswerPair(String string) throws DataHandlingException {
    var currentLine = string.split(" ");
    if(currentLine.length != 2)
      throw new DataHandlingException("Line " + string + " has more than 2 values");
    question = currentLine[0];
    answer = currentLine[1];
  }

  String getQuestion(){
    return question;
  }

  String getAnswer(){
    return answer;
  }
}

class QuestionAnswerPair
{
  private String question;
  private String answer;

  QuestionAnswerPair(String question, String answer) {
    this.question = question;
    this.answer = answer;
  }

  String getQuestion(){
    return question;
  }

  String getAnswer(){
    return answer;
  }
}

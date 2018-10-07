class QuestionAnswerPair
{
  private String question;
  private String answer;

  QuestionAnswerPair(String[] arr) {
    question = arr[0];
    answer = arr[1];
  }

  public String getQuestion(){
    return question;
  }

  public String getAnswer(){
    return answer;
  }
}

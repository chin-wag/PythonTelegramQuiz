import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="questions_and_answers")
class QuestionAnswerPair implements Serializable
{
  @Id
  @GeneratedValue
  private Integer id;
  private String question;
  private String answer;

  QuestionAnswerPair(String question, String answer) {
    this.question = question;
    this.answer = answer;
  }

  public QuestionAnswerPair() {}

  String getQuestion(){
    return question;
  }

  String getAnswer(){
    return answer;
  }

  Integer getId() { return id; }
}

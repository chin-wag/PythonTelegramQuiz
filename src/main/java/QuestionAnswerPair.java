package main.java;

import org.eclipse.persistence.annotations.Cache;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="questions_and_answers")
public class QuestionAnswerPair implements Serializable {
  @Id
  @GeneratedValue
  private int id;
  private String question;
  private String answer;

  public QuestionAnswerPair(String question, String answer) {
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

  void setQuestion(String question) { this.question = question; }

  void setAnswer(String answer) { this.answer = answer; }

  int getId() { return id; }

  void setId(int id) { this.id = id; }
}

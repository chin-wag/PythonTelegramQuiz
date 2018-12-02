package main.java;

import java.io.Serializable;
import java.util.Optional;

public interface QuestionManager extends Serializable {
  Optional<QuestionAnswerPair> getNextPair();
}

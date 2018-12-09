package test.java;

import main.java.DataHandlingException;
import main.java.QuestionAnswerPair;

import java.util.*;

public class QuestionAnswerPairDatabaseManagerMock {
  private Map<Long, QuestionAnswerPair> pairs = new HashMap<>();
  private long index = (long)0;

  public void save(QuestionAnswerPair pair)
  {
    pairs.put(index, pair);
    index++;
  }

  public void update(QuestionAnswerPair pair)
  {

  }

  public void remove(QuestionAnswerPair pair)
  {
    pairs.remove(index);
  }

  public Optional<QuestionAnswerPair> get(long id) throws DataHandlingException
  {
    return Optional.ofNullable(pairs.get(id));
  }

  public List<QuestionAnswerPair> getPairs(long id) throws DataHandlingException
  {
    var res = new ArrayList<QuestionAnswerPair>();
    for (var i = id; i < pairs.size(); i++)
      res.add(pairs.get(i));

    return res;
  }
}

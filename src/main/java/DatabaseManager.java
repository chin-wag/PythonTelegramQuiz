package main.java;

import java.util.List;
import java.util.Optional;

public interface DatabaseManager {
  List<QuestionAnswerPair> getData(long id) throws DataHandlingException;
  void updateGame(Game game);
  void saveGame(Game game);
  void removeGame(long id) throws DataHandlingException;
  Optional<Game> getGame(long id);

  default Game getExistentGame(long id) throws DataHandlingException {
    return getGame(id).orElseThrow(()->
            new DataHandlingException(String.format("Game with id %s is not in database", id)));
  }

  default boolean isGameExistent(long id)  {
    return getGame(id).isPresent();
  }
}
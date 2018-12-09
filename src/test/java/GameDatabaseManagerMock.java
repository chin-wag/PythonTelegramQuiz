package test.java;

import main.java.DataHandlingException;
import main.java.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameDatabaseManagerMock {
  private Map<Long, Game> games = new HashMap<>();
  private long index = (long)0;

  public void save(Game game)
  {
    games.put(index, game);
    index++;
  }

  public void update() {}

  public void remove(long id)
  {
    games.remove(index);
  }

  public Optional<Game> get(long id) {
    return Optional.ofNullable(games.get(id));
  }

  public Game getExistence(long id) throws DataHandlingException
  {
    return Optional.ofNullable(games.get(id)).orElseThrow(()->
          new DataHandlingException(String.format("Game with id %s is not in database", id)));
  }
}

package main.java;

import java.util.Optional;

public class GameDatabaseManager extends GameAndPairDatabaseManager<Game, Long> {
  public GameDatabaseManager(String unitName) {
    super(unitName);
  }

  public Optional<Game> get(Long id)
  {
    return Optional.ofNullable(em.find(Game.class, id));
  }

  public boolean isExistent(long id)  {
    return get(id).isPresent();
  }
}

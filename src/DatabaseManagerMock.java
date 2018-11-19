import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Arrays;

class DatabaseManagerMock implements DatabaseManager {
  private Map<Long, Game> games = new HashMap<>();
  private long index = (long)0;

  public List<QuestionAnswerPair> getData(long id) {
    return Arrays.asList(
            new QuestionAnswerPair("2+2", "4"),
            new QuestionAnswerPair("2+3", "5")
    );
  }

  public void updateGame(Game game) {}

  public void saveGame(Game game) {
    games.put(index, game);
    index++;
  }

  public void removeGame(long id) {
    games.remove(id);
  }

  public Optional<Game> getGame(long id) {
    return Optional.ofNullable(games.get(id));
  }
}

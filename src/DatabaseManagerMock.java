import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class DatabaseManagerMock implements DatabaseManagerInterface {
  private Map<Long, Game> games = new HashMap<>();
  private Long index = (long)0;

  public ArrayList<QuestionAnswerPair> getData(Long id) throws DataHandlingException {
    return new ArrayList<>() {{add(new QuestionAnswerPair("2+2", "4"));
    add(new QuestionAnswerPair("2+3", "5"));}};
  }

  public void updateGame(Game game) {}

  public void saveGame(Game game) {
  games.put(index, game);
  index++;
  }

  public void removeGame(Long id) throws DataHandlingException {
    games.remove(id);
  }
  public Optional<Game> getGame(Long id) {
    return games.containsKey(id) ? Optional.of(games.get(id)) : Optional.empty();
  }

  public Game getExistentGame(Long id) throws DataHandlingException {
    return getGame(id).get();
  }

  public boolean isGameExistent(Long id) {
    return getGame(id).isPresent();
  }
}

package edu.ntnu.idatt2001.paths.filehandlers.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import edu.ntnu.idatt2001.paths.game.Game;
import edu.ntnu.idatt2001.paths.game.Player;
import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.goals.HealthGoal;
import edu.ntnu.idatt2001.paths.goals.ScoreGoal;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameFileHandlerTest {

  private static Path savedGamePath;
  private GameFileHandler gameFileHandler;
  private Game testGame;
  private Story testStory;
  private List<Goal> testGoals;

  @AfterAll
  static void cleanUp() {
    try {
      Files.delete(savedGamePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @BeforeEach
  void setUp() throws IOException {
    gameFileHandler = new GameFileHandler();
    Passage openingPassage = new Passage("Home", "You are at home.");
    testStory = new Story("Test Story", openingPassage);
    testStory.addPassage(new Passage("Forest", "You are in a forest."));
    testStory.addPassage(new Passage("Cave", "You are in a cave."));
    Player testPlayer = new Player.Builder("Test Player").build();
    testGoals = List.of(new HealthGoal(1), new ScoreGoal(100));
    testGame = new Game(testPlayer, testStory, testGoals);
    gameFileHandler = new GameFileHandler();
    Files.createDirectories(gameFileHandler.getFilePath());
    savedGamePath = gameFileHandler.getFilePath().resolve(testGame.getStory().getTitle() + ".json");
  }

  @Test
  void saveGameToFile_createsFileWithCorrectContent() throws IOException {
    gameFileHandler.saveGameToFile(testGame);

    Game loadedGame = gameFileHandler.loadGameFromStyle("Test Story");
    assertThat(loadedGame, equalTo(testGame));
  }

  @Test
  void loadGameFromFile_returnsCorrectGame() throws IOException {
    gameFileHandler.saveGameToFile(testGame);
    Game loadedGame = gameFileHandler.loadGameFromStyle("Test Story");

    assertThat(loadedGame, equalTo(testGame));
  }

  @Test
  void loadGameFromFile_ContainsAllPassages() throws IOException {
    gameFileHandler.saveGameToFile(testGame);
    Game loadedGame = gameFileHandler.loadGameFromStyle("Test Story");
    assertThat(
        loadedGame.getStory().getPassages().toArray(), equalTo(testStory.getPassages().toArray()));
  }

  @Test
  void loadGameFromFile_ContainsAllGoalsType() throws IOException {
    gameFileHandler.saveGameToFile(testGame);
    Game loadedGame = gameFileHandler.loadGameFromStyle("Test Story");
    assertThat(loadedGame.getGoals().toArray().getClass(), equalTo(testGoals.toArray().getClass()));
  }
}

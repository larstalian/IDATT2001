package edu.ntnu.idatt2001.paths.filehandlers;

import edu.ntnu.idatt2001.paths.game.Game;
import edu.ntnu.idatt2001.paths.game.Player;
import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.goals.HealthGoal;
import edu.ntnu.idatt2001.paths.goals.ScoreGoal;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class GameFileHandlerTest {

  private GameFileHandler gameFileHandler;
  private Game testGame;
  private Story testStory;

  private Player testPlayer;

  private Path dirPath;

  private List<Goal> testGoals;


  @BeforeEach
  void setUp() throws IOException {
    gameFileHandler = new GameFileHandler();
    Passage openingPassage = new Passage("Home", "You are at home.");
    testStory = new Story("Test Story", openingPassage);
    testPlayer = new Player.Builder("Test Player").build();
    testGoals = List.of(new HealthGoal( 1), new ScoreGoal(100));
    testGame = new Game(testPlayer, testStory, testGoals);
    gameFileHandler = new GameFileHandler();
    Files.createDirectories(gameFileHandler.getFilePath());
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

    assertThat("Loaded game should be equal to saved game", loadedGame, equalTo(testGame));
  }
}

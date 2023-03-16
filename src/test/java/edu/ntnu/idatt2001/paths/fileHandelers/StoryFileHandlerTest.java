package edu.ntnu.idatt2001.paths.fileHandelers;

import edu.ntnu.idatt2001.paths.actions.GoldAction;
import edu.ntnu.idatt2001.paths.filehandlers.StoryFileHandler;
import edu.ntnu.idatt2001.paths.game.Game;
import edu.ntnu.idatt2001.paths.game.Player;
import edu.ntnu.idatt2001.paths.goals.ScoreGoal;
import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class StoryFileHandlerTest {

  private static Path savedStoryPath;
  private StoryFileHandler storyFileHandler;
  private Story testStory;

  @AfterAll
  static void cleanUp() {
    try {
      Files.delete(savedStoryPath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @BeforeEach
  void setUp() {
    storyFileHandler = new StoryFileHandler();

    Passage openingPassage = new Passage("Opening Passage", "This is the opening passage");
    openingPassage.addLink(new Link("Go to the forest", "Forest"));
    Passage forestPassage = new Passage("Forest", "You are in a forest.");
    testStory = new Story("Test Story", openingPassage);
    testStory.addPassage(forestPassage);
    testStory.addPassage(new Passage("Cave", "You are in a cave."));
    testStory.getPassage(new Link("Forest", "Forest")).addLink(new Link("Go to the cave", "Cave"));
    testStory
        .getPassage(new Link("Forest", "Forest"))
        .getLinks()
        .get(0)
        .addAction(new GoldAction(10));
    testStory.addPassage(new Passage("River", "You are by a river."));
  }

  @Test
  void testSaveAndLoadStory() throws IOException {
    storyFileHandler.saveStoryToFile(testStory);
    savedStoryPath = storyFileHandler.getFilePath().resolve(testStory.getTitle() + ".json");
    assertThat("The story file should exist", Files.exists(savedStoryPath), is(true));
    Game game =
        new Game(new Player.Builder("player").build(), testStory, List.of(new ScoreGoal(100)));

    Story loadedStory = storyFileHandler.loadStoryFromFile(testStory.getTitle());
    assertThat(loadedStory.getTitle(), equalTo(testStory.getTitle()));

    assertThat(
        loadedStory.getOpeningPassage().getTitle(),
        equalTo(testStory.getOpeningPassage().getTitle()));

    assertThat(
        loadedStory.getOpeningPassage().getContent(),
        equalTo(testStory.getOpeningPassage().getContent()));
  }
}

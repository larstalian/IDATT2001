package edu.ntnu.idatt2001.paths.fileHandelers;

import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.actions.GoldAction;
import edu.ntnu.idatt2001.paths.actions.HealthAction;
import edu.ntnu.idatt2001.paths.filehandlers.StoryFileHandler;
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
import java.util.stream.IntStream;

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
  void setUp() throws IOException {
    storyFileHandler = new StoryFileHandler();

    Passage openingPassage = new Passage("Opening Passage", "This is the opening passage");
    openingPassage.addLink(new Link("Go to the forest", "Forest"));
    Passage forestPassage = new Passage("Forest", "You are in a forest.");
    testStory = new Story("Test Story", openingPassage);
    testStory.addPassage(forestPassage);
    testStory.addPassage(new Passage("Cave", "You are in a cave."));
    testStory.getPassage(new Link("Forest", "Forest")).addLink(new Link("Go to the cave", "Cave"));

    Action goldAction = new GoldAction(10);
    Action healthAction = new HealthAction(10);

    testStory.getPassage(new Link("Forest", "Forest")).getLinks().get(0).addAction(goldAction);
    testStory.getPassage(new Link("Forest", "Forest")).getLinks().get(0).addAction(healthAction);
    testStory.addPassage(new Passage("River", "You are by a river."));
    savedStoryPath = storyFileHandler.getFilePath().resolve(testStory.getTitle() + ".json");
    storyFileHandler.saveStoryToFile(testStory);
  }

  @Test
  void testSaveStory() {
    assertThat("The story file should exist", Files.exists(savedStoryPath), is(true));
  }

  @Test
  void testLoadStory() throws IOException {
    Story loadedStory = storyFileHandler.loadStoryFromFile(testStory.getTitle());
    assertThat(loadedStory.getTitle(), equalTo(testStory.getTitle()));
  }

  @Test
  void testLoadedStoryHasSameOpeningPassageTitle() throws IOException {
    Story loadedStory = storyFileHandler.loadStoryFromFile(testStory.getTitle());
    assertThat(
        loadedStory.getOpeningPassage().getTitle(),
        equalTo(testStory.getOpeningPassage().getTitle()));
  }

  @Test
  void testLoadedStoryHasSameOpeningPassageContent() throws IOException {
    Story loadedStory = storyFileHandler.loadStoryFromFile(testStory.getTitle());
    assertThat(
        loadedStory.getOpeningPassage().getContent(),
        equalTo(testStory.getOpeningPassage().getContent()));
  }

  @Test
  void testLoadedStoryHasTheSamePassages() throws IOException {
    Story loadedStory = storyFileHandler.loadStoryFromFile(testStory.getTitle());
    assertThat(loadedStory.getPassages().toArray(), equalTo(testStory.getPassages().toArray()));
  }

  @Test
  void testLoadedStoryHasTheSamePassageLinks() throws IOException {
    Story loadedStory = storyFileHandler.loadStoryFromFile(testStory.getTitle());
    assertThat(
        loadedStory.getPassage(new Link("Forest", "Forest")).getLinks().toArray(),
        equalTo(testStory.getPassage(new Link("Forest", "Forest")).getLinks().toArray()));
  }

  @Test
  void testLoadedStoryHasTheSameActions() throws IOException {
    Story loadedStory = storyFileHandler.loadStoryFromFile(testStory.getTitle());

    List<Action> testActions =
        testStory.getPassage(new Link("Forest", "Forest")).getLinks().get(0).getActions();
    List<Action> loadedActions =
        loadedStory.getPassage(new Link("Forest", "Forest")).getLinks().get(0).getActions();

    assertThat(testActions.size(), equalTo(loadedActions.size()));

    IntStream.range(0, testActions.size())
        .forEach(
            i ->
                assertThat(
                    testActions.get(i).getClass(), equalTo(loadedActions.get(i).getClass())));
  }
}

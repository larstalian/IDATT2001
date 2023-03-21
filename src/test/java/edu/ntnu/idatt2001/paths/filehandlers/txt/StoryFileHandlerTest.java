package edu.ntnu.idatt2001.paths.filehandlers.txt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import edu.ntnu.idatt2001.paths.actions.HealthAction;
import edu.ntnu.idatt2001.paths.actions.InventoryAction;
import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoryFileHandlerTest {

  private static final Path EMPTY_TEST_STORY_PATH =
      Paths.get("src", "main", "resources", "stories", "txt", "Empty Test Story.txt");
  private static final Path NO_ACTIONS_TEST_STORY_PATH =
      Paths.get("src", "main", "resources", "stories", "txt", "No Actions Test Story.txt");

  private Story story;
  private Story loadedStory;

  @AfterAll
  static void tearDown() {
    Path path = Path.of("src/main/resources/stories/txt/Test Story.txt");
    try {
      Files.delete(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @BeforeEach
  void setUp() throws IOException, ParseException {
    Passage openingPassage = new Passage("Opening Passage", "This is the opening passage.");
    Passage passage1 = new Passage("Passage1", "This is the first passage.");
    Link link1 = new Link("Go to the first passage", "Passage1");
    link1.addAction(new HealthAction(10));
    link1.addAction(new InventoryAction("Sword"));
    openingPassage.addLink(link1);
    Passage passage2 = new Passage("Forest", "This is the forest");
    Link link2 = new Link("Go to the forest", "Forest");
    link2.addAction(new HealthAction(-10));
    passage1.addLink(link2);
    story = new Story("Test Story", openingPassage);
    story.addPassage(passage1);
    story.addPassage(passage2);

    StoryFileWriter.saveStoryToFile(story);
    loadedStory = StoryFileReader.readStoryFromFile(story.getTitle());
  }

  @AfterEach
  void deleteTemporaryFiles() {
    try {
      Files.deleteIfExists(EMPTY_TEST_STORY_PATH);
      Files.deleteIfExists(NO_ACTIONS_TEST_STORY_PATH);
    } catch (IOException e) {
      e.printStackTrace();
      fail("IOException occurred while deleting temporary files.");
    }
  }

  @Test
  void whenStoryIsLoaded_itShouldBeEqualToWrittenStory() {
    assertThat(loadedStory, equalTo(story));
  }

  @Test
  void whenStoryIsSaved_itShouldContainCorrectContent() {
    assertThat(loadedStory.getPassages().toArray(), equalTo(story.getPassages().toArray()));
  }

  @Test
  void whenStoryIsSaved_itShouldContainCorrectActions() {
    assertThat(
        loadedStory
            .getPassage(new Link("Go to the first passage", "Passage1"))
            .getLinks()
            .get(0)
            .getActions(),
        hasItem(
            story
                .getPassage(new Link("Go to the first passage", "Passage1"))
                .getLinks()
                .get(0)
                .getActions()
                .get(0)));
  }

  @Test
  void whenOpeningPassageIsLoaded_itShouldHaveLink() {
    assertThat(
        loadedStory.getOpeningPassage().getLinks(),
        contains(story.getOpeningPassage().getLinks().get(0)));
  }

  @Test
  void whenOpeningPassageIsLoaded_itShouldHaveAction() {
    assertThat(loadedStory.getOpeningPassage().getLinks().get(0).getActions(), hasSize(2));
  }

  @Test
  void whenStoryIsSavedWithNoLinks_itShouldNotThrowException() {
    String invalidFileTitle = "Invalid Test Story";
    Path invalidFilePath = Path.of("src/main/resources/stories/txt/" + invalidFileTitle + ".txt");
    Passage openingPassage = new Passage("Empty Story", "This is an empty story.");
    Story emptyStory = new Story(invalidFileTitle, openingPassage);
    try {
      StoryFileWriter.saveStoryToFile(emptyStory);
      Story loadedEmptyStory = StoryFileReader.readStoryFromFile(emptyStory.getTitle());
      assertThat(loadedEmptyStory, equalTo(emptyStory));

    } catch (IOException e) {
      e.printStackTrace();
      fail("IOException occurred while writing invalid file content");
    } catch (ParseException e) {
      e.printStackTrace();
      fail("ParseException occurred while reading invalid file content");
    } finally {
      try {
        Files.delete(invalidFilePath);
      } catch (IOException e) {
        e.printStackTrace();
        fail("IOException occurred while deleting invalid file");
      }
    }
  }

  @Test
  void whenStoryIsSavedWithNoActions_itShouldNotThrowException() {
    String invalidFileTitle = "Invalid Test Story";
    Path invalidFilePath = Path.of("src/main/resources/stories/txt/" + invalidFileTitle + ".txt");
    Passage openingPassage = new Passage("No Actions Story", "This is a story with no actions.");
    Passage passage1 = new Passage("Passage1", "This is the first passage.");
    Link link1 = new Link("Go to the first passage", "Passage1");
    openingPassage.addLink(link1);
    Story noActionsStory = new Story(invalidFileTitle, openingPassage);
    noActionsStory.addPassage(passage1);

    try {
      StoryFileWriter.saveStoryToFile(noActionsStory);
      Story loadedNoActionsStory = StoryFileReader.readStoryFromFile(noActionsStory.getTitle());
      assertThat(loadedNoActionsStory, equalTo(noActionsStory));
    } catch (IOException | ParseException e) {
      e.printStackTrace();
      fail("IOException occurred while writing invalid file content");
    } finally {
      try {
        Files.delete(invalidFilePath);
      } catch (IOException e) {
        e.printStackTrace();
        fail("IOException occurred while deleting invalid file");
      }
    }
  }

  @Test
  void whenInvalidFileIsLoaded_itShouldThrowParseException() {
    String invalidFileTitle = "Invalid Test Story";
    Path invalidFilePath =
        Paths.get("src", "main", "resources", "stories", "txt", "InvalidPassageFormatStory.txt");
    try {
      Files.writeString(invalidFilePath, "Invalid file content");
      assertThrows(ParseException.class, () -> StoryFileReader.readStoryFromFile(invalidFileTitle));
    } catch (IOException e) {
      e.printStackTrace();
      fail("IOException occurred while writing invalid file content");
    } finally {
      try {
        Files.delete(invalidFilePath);
      } catch (IOException e) {
        e.printStackTrace();
        fail("IOException occurred while deleting invalid file");
      }
    }
  }

  @Test
  void whenInvalidPassageFormatIsLoaded_itShouldThrowParseException() {
    String invalidPassageContent =
        "Story Title\n\n::Passage1\nThis is the first passage.\nInvalid Link";
    Path path = Path.of("src/main/resources/stories/txt/InvalidPassageFormatStory.txt");
    try {
      Files.writeString(path, invalidPassageContent);
      assertThrows(
          ParseException.class,
          () -> StoryFileReader.readStoryFromFile("InvalidPassageFormatStory"));
    } catch (IOException e) {
      fail("Unable to create a temporary file for testing invalid passage format.");
    } finally {
      try {
        Files.delete(path);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

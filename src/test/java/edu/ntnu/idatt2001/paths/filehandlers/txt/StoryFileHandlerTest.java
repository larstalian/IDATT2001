package edu.ntnu.idatt2001.paths.filehandlers.txt;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2001.paths.actions.HealthAction;
import edu.ntnu.idatt2001.paths.actions.InventoryAction;
import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StoryFileHandlerTest {

  private final Path path = Path.of("src/main/resources/stories/txt");
  private Story story;

  @BeforeEach
  public void setUp() {
    Passage openingPassage = new Passage("Opening Passage", "This is the opening passage.");
    Passage passage1 = new Passage("Passage1", "This is the first passage.");
    Link link1 = new Link("Go to the first passage", "Passage1");
    link1.addAction(new HealthAction(10));
    link1.addAction(new InventoryAction("Sword"));
    openingPassage.addLink(link1);
    Passage passage2 = new Passage("Forest","This is the forest");
    passage1.addLink(new Link("Go to the forest","Forest"));
    story = new Story("Test Story", openingPassage);
    story.addPassage(passage1);
    story.addPassage(passage2);
  }

  @Test
  public void testSaveAndLoadStory() throws IOException {
    StoryFileWriter.saveStoryToFile(story);
//    Story loadedStory =  StoryFileReader.readStoryFromFile(story.getTitle());
//    System.out.println(loadedStory.getPassages());
//    assertThat(loadedStory, equalTo(story));
    // Save the story to a temporary file

    // Check if the file was created
//    assertTrue(Files.exists(path));

    // Load the story from the temporary file

    // Check if the loaded story is not null and has the correct title

    // Check if the loaded story has the correct passages and links
  }
}

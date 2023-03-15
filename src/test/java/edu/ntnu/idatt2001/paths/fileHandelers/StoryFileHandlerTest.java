package edu.ntnu.idatt2001.paths.fileHandelers;

import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoryFileHandlerTest {

    private StoryFileHandler storyFileHandler;
    private Story testStory;

    @BeforeEach
    public void setUp() {
        storyFileHandler = new StoryFileHandler();

        Passage openingPassage = new Passage("Opening", "Once upon a time...");
        openingPassage.addLink(new Link("Go to the forest", "Forest"));
        Passage forestPassage = new Passage("Forest", "You are in a forest.");
        testStory = new Story("Test Story", openingPassage);
        testStory.addPassage(forestPassage);
    }

    @Test
    public void testSaveAndLoadStory() throws IOException {
        storyFileHandler.saveStoryToFile(testStory);
        Path savedStoryPath = storyFileHandler.getFilePath().resolve(testStory.getTitle() + ".json");
        assertTrue(Files.exists(savedStoryPath), "The story file should exist");

        Story loadedStory = storyFileHandler.loadStoryFromFile(testStory.getTitle());
        assertEquals(testStory.getTitle(), loadedStory.getTitle(), "The story title should be the same");
        assertEquals(testStory.getOpeningPassage().getTitle(), loadedStory.getOpeningPassage().getTitle(), "The opening passage title should be the same");
        assertEquals(testStory.getOpeningPassage().getContent(), loadedStory.getOpeningPassage().getContent(), "The opening passage content should be the same");

        // Clean up the created story file
//        try {
//            Files.delete(savedStoryPath);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
package edu.ntnu.idatt2001.paths.story;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StoryTest {

    private Story story;
    private Passage openingPassage;
    private List<Passage> passages;

    @BeforeEach
    public void setup() {
        // Create a test Story object with an opening passage and two linked passages
        openingPassage = new Passage("Opening Passage", "This is the opening passage.");
        story = new Story("Test Story", openingPassage);

        Passage passage1 = new Passage("Passage 1", "This is passage 1.");
        passage1.addLink(new Link("Go to passage 2", "Passage 2"));
        story.addPassage(passage1);

        Passage passage2 = new Passage("Passage 2", "This is passage 2.");
        passage2.addLink(new Link("Go to opening passage", "Opening Passage"));
        story.addPassage(passage2);

        // Create a list of all passages in the story for testing
        passages = new ArrayList<>();
        passages.add(passage1);
        passages.add(passage2);
    }

    @Test
    public void testGetTitle() {
        assertThat("Test Story", is(story.getTitle()));
    }

    @Test
    public void testGetOpeningPassage() {
        assertThat(openingPassage, is (story.getOpeningPassage()));
    }

    @Test
    public void testAddPassage() {
        Passage newPassage = new Passage("Passage 3", "This is a new passage.");
        assertTrue(story.addPassage(newPassage));
    }

    @Test
    public void testGetPassage() {
        Link link = new Link("Passage 1", "Passage 1");
        Passage passage = story.getPassage(link);
        assertEquals("Passage 1", passage.getTitle());
        assertEquals("This is passage 1.", passage.getContent());
        assertThat(passage.getLinks(), hasItem(new Link("Go to passage 2", "Passage 2")));
    }

    @Test
    public void testGetPassages() {
        assertThat(story.getPassages(), containsInAnyOrder(passages.toArray()));
    }

    @Test
    public void testToString() {
        String expected = "Title: Test Story\n" +
                "Opening Passage:\n" +
                "This is the opening passage.\n" +
                "Passages:\n" +
                "- Passage 1: This is passage 1.\n" +
                "- Passage 2: This is passage 2.\n";
        String actual = story.toString();
        assertThat(actual, is(expected));
    }
}

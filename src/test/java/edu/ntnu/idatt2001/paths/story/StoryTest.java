package edu.ntnu.idatt2001.paths.story;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class StoryTest {

    private Story story;
    private Passage openingPassage;
    private List<Passage> passages;

    @BeforeEach
    void setup() {
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
    void testConstructor() {
        Passage passage = new Passage("Opening Passage", "Content");
        assertThrows(NullPointerException.class, () -> new Story(null, passage));
        assertThrows(IllegalArgumentException.class, () -> new Story("", null));
        assertThrows(NullPointerException.class, () -> new Story("Test Story", null));

        String tooLongTitle = new String(new char[Story.StoryConstants.MAX_TITLE_LENGTH + 1]).replace("\0", "a");
        assertThrows(IllegalArgumentException.class, () -> new Story(tooLongTitle, passage));
        String tooShortTitle = new String(new char[Story.StoryConstants.MIN_TITLE_LENGTH - 1]).replace("\0", "a");
        assertThrows(IllegalArgumentException.class, () -> new Story(tooShortTitle, passage));
    }

    @Test
    void testGetTitle() {
        assertThat("Test Story", is(story.getTitle()));
    }

    @Test
    void testGetOpeningPassage() {
        assertThat(openingPassage, is(story.getOpeningPassage()));
    }

    @Test
    void testAddPassage() {
        Passage newPassage = new Passage("Passage 3", "This is a new passage.");
        assertThat(story.addPassage(newPassage), is(true));
    }

    @Test
    void addPassage_ReturnsFalseIfKeyAlreadyExists() {
        assertThat(story.addPassage(new Passage("Passage 1", "Passage 1")), is(false));
    }


    @Test
    void testGetPassage() {
        Link link = new Link("Passage 1", "Passage 1");
        Passage passage = story.getPassage(link);
        assertThat("Passage 1", is(passage.getTitle()));
        assertThat("This is passage 1.", is(passage.getContent()));
        assertThat(passage.getLinks(), hasItem(new Link("Go to passage 2", "Passage 2")));
    }

    @Test
    void testGetPassage_ThrowsNullIfPassageDoesNotExist() {
        assertThrows(NullPointerException.class, () -> story.getPassage(new Link("Passage 3", "Passage 3")));
    }

    @Test
    void testGetPassage_ThrowsNullIfLinkIsNull() {
        assertThrows(NullPointerException.class, () -> story.getPassage(null));
    }

    @Test
    void testGetPassages() {
        assertThat(story.getPassages(), containsInAnyOrder(passages.toArray()));
    }

    @Test
    void testGetPassages_ReturnsEmptyListIfStoryHasNoPassages() {
        Story story = new Story("Test Story", new Passage("Opening Passage", "Content"));
        assertThat(story.getPassages(), is(empty()));
    }

    @Test
    void testToString() {
        String expected = "Title: Test Story\n" + "Opening Passage:\n" + "This is the opening passage.\n" + "Passages:\n" + "- Passage 1: This is passage 1.\n" + "- Passage 2: This is passage 2.\n";
        String actual = story.toString();
        assertThat(actual, is(expected));
    }
}

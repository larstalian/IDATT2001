package edu.ntnu.idatt2001.paths.model.story;

import static edu.ntnu.idatt2001.paths.model.story.Story.StoryConstants.MAX_TITLE_LENGTH;
import static edu.ntnu.idatt2001.paths.model.story.Story.StoryConstants.MIN_TITLE_LENGTH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoryTest {

  private Passage passage1;
  private Passage passage2;
  private Story story;
  private Passage openingPassage;
  private List<Passage> passages;

  @BeforeEach
  void setup() {
    // Define passages and links for testing
    openingPassage = new Passage("Opening Passage", "This is the opening passage.");
    story = new Story("Test Story", openingPassage);

    passage1 = new Passage("Passage 1", "This is passage 1.");
    passage1.addLink(new Link("Go to passage 2", "Passage 2"));

    passage2 = new Passage("Passage 2", "This is passage 2.");
    passages = new ArrayList<>();
  }

  @Test
  void testConstructor_TitleTooLongShouldThrowIllegalArgumentException() {
    String tooLongTitle = new String(new char[MAX_TITLE_LENGTH + 1]).replace("\0", "a");
    assertThrows(IllegalArgumentException.class, () -> new Story(tooLongTitle, passage1));
  }

  @Test
  void testConstructor_TitleTooShortShouldThrowIllegalArgumentException() {
    String tooShortTitle = new String(new char[MIN_TITLE_LENGTH - 1]).replace("\0", "a");
    assertThrows(IllegalArgumentException.class, () -> new Story(tooShortTitle, passage1));
  }

  @Test
  void testConstructor_ThrowsNullPointerExceptionIfPassageOrTitleIsNull() {
    assertThrows(NullPointerException.class, () -> new Story("Test Story", null));
    assertThrows(
        NullPointerException.class,
        () -> new Story(null, new Passage("Opening Passage", "Content")));
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
    assertThat(story.addPassage(passage1), is(true));
  }

  @Test
  void addPassage_ReturnsFalseIfKeyAlreadyExists() {
    story.addPassage(passage1);
    assertThat(
        story.addPassage(new Passage(passage1.getTitle(), passage1.getContent())), is(false));
  }

  @Test
  void testGetPassage() {
    story.addPassage(passage1);
    Link link = new Link(passage1.getTitle(), passage1.getTitle());
    Passage newPassage = story.getPassage(link);
    assertThat(newPassage, is(passage1));
  }

  @Test
  void testGetPassage_ThrowsNullIfPassageDoesNotExist() {
    assertThrows(
        NullPointerException.class, () -> story.getPassage(new Link("Passage 3", "Passage 3")));
  }

  @Test
  void testGetPassage_ThrowsNullIfLinkIsNull() {
    assertThrows(NullPointerException.class, () -> story.getPassage(null));
  }

  @Test
  void testGetPassages() {
    story.addPassage(passage1);
    story.addPassage(passage2);
    passages.add(passage1);
    passages.add(passage2);
    assertThat(story.getPassages(), containsInAnyOrder(passages.toArray()));
  }

  @Test
  void testGetPassages_ReturnsEmptyListIfStoryHasNoPassages() {
    Story story = new Story("Test Story", new Passage("Opening Passage", "Content"));
    assertThat(story.getPassages(), is(empty()));
  }

  @Test
  void testToString() {
    story.addPassage(passage1);
    story.addPassage(passage2);
    String expected =
        """
            Title: Test Story
            Opening Passage:
            This is the opening passage.
            Passages:
            - Passage 1: This is passage 1.
            - Passage 2: This is passage 2.
            """;
    String actual = story.toString();
    assertThat(actual, is(expected));
  }

  @Test
  void testRemovePassage_ThrowsIllegalArgumentExceptionIfPassageDoesNotExist() {
    assertThrows(
        NullPointerException.class, () -> story.removePassage(new Link("Passage 3", "Passage 3")));
  }

  @Test
  void testRemovePassage_ThrowsIllegalArgumentExceptionIfLinkIsNull() {
    assertThrows(NullPointerException.class, () -> story.removePassage(null));
  }

  @Test
  void testRemovePassage_ReturnsFalseIfOtherPassageHasLinkToGivenPassage() {
    story.addPassage(passage1);
    story.addPassage(passage2);
    Link link = new Link("Passage 1", "Passage 1");
    passage2.addLink(link);
    assertThat(story.removePassage(link), is(false));
  }

  @Test
  void testRemovePassage_ReturnsTrueIfPassageIsRemoved() {
    story.addPassage(passage1);
    Link link = new Link("Passage 1", "Passage 1");
    assertThat(story.removePassage(link), is(true));
  }

  @Test
  void testGetBrokenLinks_ReturnsEmptySetIfAllLinksAreValid() {
    story.addPassage(passage1);
    story.addPassage(passage2);
    passage2.addLink(new Link(passage1.getTitle(), passage1.getTitle()));
    assertThat(story.getBrokenLinks(), is(empty()));
  }

  @Test
  void testGetBrokenLinks_ReturnsSetWithInvalidLink() {
    Link link = new Link("Go to invalid passage", "Invalid Passage");
    story.addPassage(passage2);
    passage2.addLink(link);
    assertThat(story.getBrokenLinks(), contains(link));
  }

  @Test
  void testGetBrokenLinks_ReturnsSetWithMultipleInvalidLinks() {
    Link link1 = new Link("Go to invalid passage 1", "Invalid Passage 1");
    Link link2 = new Link("Go to invalid passage 2", "Invalid Passage 2");
    story.addPassage(passage2);
    passage2.addLink(link1);
    passage2.addLink(link2);
    assertThat(story.getBrokenLinks(), containsInAnyOrder(link1, link2));
  }
}

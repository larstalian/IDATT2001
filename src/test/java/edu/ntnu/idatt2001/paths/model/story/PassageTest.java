package edu.ntnu.idatt2001.paths.model.story;

import static edu.ntnu.idatt2001.paths.model.story.Mood.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PassageTest {

  private Passage passage;
  private Link link;

  @BeforeEach
  void setUp() {
    passage = new Passage("A4-110", "Classroom");
    link = new Link("Go through door", "A4-112");
  }

  @Test
  void testGetTitle() {
    assertThat(passage.getTitle(), is("A4-110"));
  }

  @Test
  void testAddLink_ShouldThrowIllegalArgumentExceptionIfLinkAlreadyExists() {
    passage.addLink(link);
    assertThrows(IllegalArgumentException.class, () -> passage.addLink(link));
  }

  @Test
  void testGetContent() {
    assertThat(passage.getContent(), is("Classroom"));
  }

  @Test
  void testGetLinks() {
    passage.addLink(link);
    assertThat(passage.getLinks(), contains(link));
  }

  @Test
  void testAddLink_shouldAddLinkToPassage() {
    assertThat(passage.addLink(link), is(true));
    assertThat(passage.getLinks(), contains(link));
  }

  @Test
  void testHasLinks_ShouldReturnTrueIfThereAreNoLinks() {
    assertThat(passage.hasLinks(), is(false));
  }

  @Test
  void testHasLinks_shouldReturnTrueIfThereAreLinks() {
    passage.addLink(link);
    assertThat(passage.hasLinks(), is(true));
  }

  @Test
  void testToString() {
    Link link2 = new Link("Go through window", "A4-114");
    passage.addLink(link);
    passage.addLink(link2);

    String expected =
        """
                    Title: A4-110
                    Content: Classroom
                    Links:
                    - Go through door: A4-112
                    - Go through window: A4-114
                    """;

    assertThat(passage.toString(), is(expected));
  }

  @Test
  void testToString_shouldReturnPassageWithoutLinksIfThereAreNoLinks() {
    String expected =
        """
            Title: A4-110
            Content: Classroom
            Links:
            """;
    assertThat(passage.toString(), is(expected));
  }

  @Test
  @DisplayName("Test if the passage is single visit only")
  void isSingleVisitOnly() {
    Passage passage = new Passage("A4-110", "Classroom", NONE, true);
    assertThat(passage.isSingleVisitOnly(), is(true));
  }

  @Test
  @DisplayName("Test that the set single visit only works correctly")
  void setSingleVisitOnly() {
    Passage passage = new Passage("A4-110", "Classroom", NONE, true);
    passage.setSingleVisitOnly(false);
    assertThat(passage.isSingleVisitOnly(), is(false));
  }

  @Test
  @DisplayName("Test if the passage has correct Mood")
  void getMood() {
    Passage passage = new Passage("A4-110", "Classroom", BOSS_BATTLE, true);
    assertThat(passage.getMood(), is(BOSS_BATTLE));
  }

  @Test
  @DisplayName("Test that the mood changes correctly")
  void setMood() {
    Passage passage = new Passage("A4-110", "Classroom", BOSS_BATTLE, true);
    passage.setMood(NONE);
    assertThat(passage.getMood(), is(NONE));
  }

  @Test
  @DisplayName("Test if the passage is single visit only")
  void setContent() {
    Passage passage = new Passage("A4-110", "Classroom", BOSS_BATTLE, true);
    passage.setContent("New content");
    assertThat(passage.getContent(), is("New content"));
  }

  @Test
  @DisplayName("Test if constructor throws exception when title is out of bounds")
  void testConstructorThrowsExceptionWhenTitleIsOutOfBounds() {
    assertThrows(IllegalArgumentException.class, () -> new Passage("", "Classroom"));
    assertThrows(IllegalArgumentException.class, () -> new Passage("a".repeat(30), "Classroom"));
  }

  @Test
  @DisplayName("Test if constructor throws exception when content is out of bounds")
  void testConstructorThrowsExceptionWhenContentIsOutOfBounds() {
    assertThrows(IllegalArgumentException.class, () -> new Passage("A4-110", ""));
    assertThrows(IllegalArgumentException.class, () -> new Passage("A4-110", "a".repeat(401)));
  }
}

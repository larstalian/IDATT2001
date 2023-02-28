package edu.ntnu.idatt2001.paths.story;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
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

    String expected = "Title: A4-110\n" +
        "Content: Classroom\n" +
        "Links:\n" +
        "- Go through door: A4-112\n" +
        "- Go through window: A4-114\n";

    assertThat(passage.toString(),
        is(expected));
  }

  @Test
  void testToString_shouldReturnPassageWithoutLinksIfThereAreNoLinks() {
    String expected = "Title: A4-110\n" +
        "Content: Classroom\n" +
        "Links:\n";
    assertThat(passage.toString(), is(expected));
  }
}
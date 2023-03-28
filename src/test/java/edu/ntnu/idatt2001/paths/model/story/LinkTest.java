package edu.ntnu.idatt2001.paths.model.story;

import static edu.ntnu.idatt2001.paths.model.story.Link.LinkConstants.REF_MIN_LENGTH;
import static edu.ntnu.idatt2001.paths.model.story.Link.LinkConstants.TEXT_MIN_LENGTH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2001.paths.model.actions.Action;
import edu.ntnu.idatt2001.paths.model.actions.GoldAction;
import edu.ntnu.idatt2001.paths.model.actions.HealthAction;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkTest {

  private Link link;

  @BeforeEach
  void setUp() {
    link = new Link("Go to the next passage", "Go to the next passage");
  }

  @Test
  void testGetText() {
    assertThat(link.getText(), is("Go to the next passage"));
  }

  @Test
  void testGetRef() {
    assertThat(link.getRef(), is("Go to the next passage"));
  }

  @Test
  void testGetActions() {
    GoldAction action = new GoldAction(10);
    HealthAction action2 = new HealthAction(10);
    link.addAction(action);
    link.addAction(action2);
    Collection<Action> actions = List.of(action, action2);
    assertThat(link.getActions(), containsInAnyOrder(actions.toArray()));
  }

  @Test
  void testGetActions_ShouldReturnEmptyListWhenThereAreNoActions() {
    assertThat(link.getActions(), empty());
  }

  @Test
  void testAddAction() {
    GoldAction action = new GoldAction(10);
    link.addAction(action);
    assertThat(link.getActions(), contains(action));
  }

  @Test
  void testToString() {
    String expectedString =
        "Game.Link{text='Go to the next passage', ref='Go to the next passage', actions=[]}";
    assertThat(link.toString(), equalTo(expectedString));
  }

  @Test
  void testConstructor_ShouldThrowIllegalArgumentExceptionIfTextIsTooShort() {
    String tooShortText = "a".repeat(TEXT_MIN_LENGTH - 1);
    String ref = "a".repeat(REF_MIN_LENGTH);
    assertThrows(IllegalArgumentException.class, () -> new Link(tooShortText, ref));
  }

  @Test
  void testConstructor_ShouldThrowIllegalArgumentExceptionIfRefIsTooShort() {
    String text = "a".repeat(TEXT_MIN_LENGTH);
    String tooShortRef = "a".repeat(REF_MIN_LENGTH - 1);
    assertThrows(IllegalArgumentException.class, () -> new Link(text, tooShortRef));
  }
}

package edu.ntnu.idatt2001.paths.story;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2001.paths.actions.Action;
import java.util.List;
import org.junit.jupiter.api.Test;

class LinkTest {

  @Test
  void getText() {
    String expectedText = "hello world";
    String expectedRef = "HELLO WORLD";
    Link example = new Link(expectedText, expectedRef);

    String actualText = example.getText();

    assertEquals(expectedText, actualText);
  }

  @Test
  void getRef() {
    String expectedText = "hello world";
    String expectedRef = "HELLO WORLD";
    Link example = new Link(expectedText, expectedRef);

    String actualRef = example.getRef();

    assertEquals(expectedRef, actualRef);
  }

  @Test
  void getActions() {
    Link link = new Link("Go to the next room", "room2");
    List<Action> actions = link.getActions();

    assertNotNull(actions);

    assertTrue(actions.isEmpty());

    Action action = player -> System.out.println("Executing");
    link.addAction(action);

    assertTrue(actions.contains(action));
  }

  @Test
  void addAction() {

    Link link = new Link("Go to another room", "room3");

    Action action = player -> System.out.println("Executing");
    link.addAction(action);

    assertTrue(link.getActions().contains(action));
  }

  @Test
  void testToString() {

    Link link = new Link("Go to another room", "room 4");

    Action action1 = player -> System.out.println("Executing 1");
    Action action2 = player -> System.out.println("Executing 2");
    link.addAction(action1);
    link.addAction(action2);

    String expected =
        "Game.Link{text='Go to another room', ref='room 4', actions=[" + action1 + ", " + action2
            + "]}";
    assertEquals(expected, link.toString());
  }
}
package edu.ntnu.idatt2001.paths.story;

import edu.ntnu.idatt2001.paths.actions.Action;
import java.util.List;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        Action action = player -> System.out.println("Executing action");
        link.addAction(action);

        assertTrue(actions.contains(action));
    }

    @Test
    void addAction() {
    }

    @Test
    void testToString() {
    }
}
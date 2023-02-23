package edu.ntnu.idatt2001.paths.story;

import edu.ntnu.idatt2001.paths.actions.Action;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

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

    }

    @Test
    void addAction() {
    }

    @Test
    void testToString() {
    }
}
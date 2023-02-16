package edu.ntnu.idatt2001.paths;

import edu.ntnu.idatt2001.paths.story.Link;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkTest {

    @Test
    void getText() {
        // Arrange
        String expectedText = "hello world";
        String expectedRef = "HELLO WORLD";
        Link example = new Link(expectedText, expectedRef);

        // Act
        String actualText = example.getText();

        // Assert
        assertEquals(expectedText, actualText);
    }

    @Test
    void getRef() {
        // Arrange
        String expectedText = "hello world";
        String expectedRef = "HELLO WORLD";
        Link example = new Link(expectedText, expectedRef);

        // Act
        String actualRef = example.getRef();

        // Assert
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
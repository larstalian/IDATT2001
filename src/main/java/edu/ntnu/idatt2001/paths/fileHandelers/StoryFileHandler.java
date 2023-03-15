package edu.ntnu.idatt2001.paths.fileHandelers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2001.paths.story.Story;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class StoryFileHandler {

    private final ObjectMapper objectMapper;

    private final Path filePath;

    public StoryFileHandler() {
        objectMapper = new ObjectMapper();
        filePath = Paths.get("src/main/resources/stories");
        try {
            Files.createDirectories(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the given story to a file with the given filename.
     *
     * @param story the story to be saved
     * @throws IOException if there is an issue writing the story to the file
     */
    public void saveStoryToFile(Story story) throws IOException {
        Objects.requireNonNull(story, "Story cannot be null");
        String filename = story.getTitle();

        String jsonString = objectMapper.writeValueAsString(story);
        Path storyFilePath = filePath.resolve(filename + ".json"); // Add ".json" extension
        Files.write(storyFilePath, jsonString.getBytes());
    }

    /**
     * Loads a story from a file with the given filename.
     *
     * @param filename the name of the file to load the story from
     * @return the loaded story
     * @throws IOException if there is an issue reading the story from the file
     */
    public Story loadStoryFromFile(String filename) throws IOException {
        Objects.requireNonNull(filename, "Filename cannot be null");

        Path storyFilePath = filePath.resolve(filename + ".json"); // Add ".json" extension
        String jsonString = new String(Files.readAllBytes(storyFilePath));
        return objectMapper.readValue(jsonString, Story.class);
    }
}

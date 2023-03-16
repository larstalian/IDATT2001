package edu.ntnu.idatt2001.paths.filehandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.ntnu.idatt2001.paths.story.Story;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Class for handling saving and reading of Story objects to and from files. The files are stored in
 * JSON format with a ".json" extension in the "src/main/resources/stories" directory.
 *
 * <p>Files are being saved and serialized using default jackson serialization, and uses custom
 * deserialization for the {@link Story} class because Jacksons default deserialization cannot
 * deserialize the map.
 *
 * @see StoryDeserializer
 */
public class StoryFileHandler {

  private final ObjectMapper objectMapper;

  private final Path filePath;

  /**
   * Constructs a new StoryFileHandler with default settings. Initializes an ObjectMapper and sets
   * the file path for the stories. Registers the custom deserializer for the Story class.
   */
  public StoryFileHandler() {
    objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Story.class, new StoryDeserializer());
    objectMapper.registerModule(module);
    filePath = Paths.get("src/main/resources/stories");
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

    String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(story);
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

  /**
   * Returns the file path where stories are stored.
   *
   * @return the file path
   */
  public Path getFilePath() {
    return filePath;
  }
}

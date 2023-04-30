package edu.ntnu.idatt2001.paths.model.filehandlers.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.serializers.LinkDeserializer;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.serializers.StoryDeserializer;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.serializers.StorySerializer;
import edu.ntnu.idatt2001.paths.model.story.Link;
import edu.ntnu.idatt2001.paths.model.story.Story;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Class for handling saving and reading of Story objects to and from files. The files are stored in
 * JSON format with a ".json" extension in the "src/main/resources/stories" directory.
 *
 * <p>Files are being saved and serialized using default jackson serialization, and uses custom
 * serializing deserializing for the {@link Story} class and custom deserializing for the {@link
 * Link} class because Jacksons default deserialization cannot deserialize the map in the {@link
 * Story} class, or the {@code actions} List in the {@link Link} class.
 *
 * <p>To use this class to write and read stories to and from files, create a new instance of this
 * class and use the {@link #saveStoryToFile(Story)} and {@link #loadStoryFromFile(String)} methods.
 * For example:
 *
 * <pre>{@code
 * StoryFileHandler storyFileHandler = new StoryFileHandler();
 * Story myStory = new Story("My Story", openingPassage);
 * storyFileHandler.saveStoryToFile(myStory);
 * Story loadedStory = storyFileHandler.loadStoryFromFile("My Story");
 * }</pre>
 *
 * @see StoryDeserializer
 * @see StorySerializer
 * @see LinkDeserializer
 */
public class StoryFileHandler {

  private static final Path filePath = Paths.get("src/main/resources/stories/json");
  private static final Path customMediaPath = Paths.get("src/main/resources/stories/");
  private final ObjectMapper objectMapper;

  /**
   * Constructs a new StoryFileHandler with default settings. Initializes an ObjectMapper and sets
   * the file path for the stories. Registers the custom deserializer for the Story class.
   */
  public StoryFileHandler() {
    objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addSerializer(Story.class, new StorySerializer());
    module.addDeserializer(Story.class, new StoryDeserializer());
    module.addDeserializer(Link.class, new LinkDeserializer());
    objectMapper.registerModule(module);
  }

  /**
   * Returns an array of all the saved stories.
   *
   * @return an array of all the saved stories
   */
  public static String[] getSavedStories() {
    File folder = new File(filePath.toString());
    return folder.list();
  }

  /**
   * Retrieves a collection of custom sound files for a given story.
   *
   * @param storyName The name of the story for which custom sound files should be retrieved.
   * @return A collection of file names from the "sounds" folder in the custom media path.
   */
  public static Collection<String> getCustomSoundFiles(String storyName) {
    File soundsFolder = new File(customMediaPath + "/" + storyName + "/sounds");
    return soundsFolder.list() == null ? Collections.emptyList() : Arrays.asList(Objects.requireNonNull(soundsFolder.list()));
  }

  /**
   * Retrieves a collection of custom image files for a given story.
   *
   * @param storyTitle The name of the story for which custom image files should be retrieved.
   * @return A collection of file names from the "images" folder in the custom media path.
   */
  public static Collection<String> getCustomImageFiles(String storyTitle) {
    storyTitle = FilenameUtils.removeExtension(storyTitle);
    File imagesFolder = new File(customMediaPath + "/" + storyTitle + "/images");
    return imagesFolder.list() == null ? Collections.emptyList() : Arrays.asList(Objects.requireNonNull(imagesFolder.list()));
  }

  /**
   * Retrieves a collection of custom media files (sounds and images) for a given story that do not match any passage titles.
   * The method compares file names without their file extensions to the passage titles.
   *
   * @param story The story object containing the passages to check against the custom media files.
   * @return A collection of file names from the "sounds" and "images" folders that do not match any passage titles.
   */
  public static Collection<String> getBrokenFiles(Story story) {
    Collection<String> sounds = getCustomSoundFiles(story.getTitle());
    Collection<String> images = getCustomImageFiles(story.getTitle());
    Collection<String> brokenFiles = new ArrayList<>();
    brokenFiles.addAll(sounds);
    brokenFiles.addAll(images);

    brokenFiles.removeIf(fileName -> {
      String fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
      return story.getPassages().stream().anyMatch(passage -> passage.getTitle().equals(fileNameWithoutExtension));
    });

    return brokenFiles;
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
    Files.createDirectories(filePath);
    Path storyFilePath = filePath.resolve(filename + ".json");
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
    filename = FilenameUtils.removeExtension(filename);
    Objects.requireNonNull(filename, "Filename cannot be null");

    Path storyFilePath = filePath.resolve(filename + ".json");
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

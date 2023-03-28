package edu.ntnu.idatt2001.paths.model.filehandlers.txt;

import edu.ntnu.idatt2001.paths.model.actions.Action;
import edu.ntnu.idatt2001.paths.model.story.Link;import edu.ntnu.idatt2001.paths.model.story.Passage;import edu.ntnu.idatt2001.paths.model.story.Story;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The StoryFileWriter class provides utility methods for saving stories to text files. This class
 * is designed to be used in a static context, and should not be instantiated.
 *
 * <p>The class offers methods to save a story object to a text file, and to build strings
 * representing the content of story components, such as passages, links, and actions.
 *
 * <p>The static method {@link #saveStoryToFile( Story )} takes a story object and saves it to a text
 * file. The file is created in the specified file path, with the story's title as the filename. The
 * content of the story is formatted using helper methods provided in this class. If any I/O error
 * occurs while saving the story, an IOException is thrown.
 *
 * <p>The StoryFileWriter class utilizes the {@link Story}, {@link Passage}, {@link Link}, and
 * {@link Action} classes as components to build the content of the story into an editable txt file.
 *
 * @see StoryFileReader
 */
public class StoryFileWriter {
  private static final String NEWLINE = System.lineSeparator();
  private static final String FILE_ENDING = ".txt";
  private static final String FILEPATH = "src/main/resources/stories/txt/";
  private static final String PASSAGE_PREFIX = "::";
  private static final String LINK_TEXT_PREFIX = "[";
  private static final String LINK_TEXT_SUFFIX = "]";
  private static final String LINK_REF_PREFIX = "(";
  private static final String LINK_REF_SUFFIX = ")";
  private static final String ACTION_PREFIX = "{";
  private static final String ACTION_SUFFIX = "}";

  private static final String SEPARATOR = ",";

  private static final Path FILE_PATH = Paths.get(FILEPATH);

  private StoryFileWriter() {}

  /**
   * Saves the given story to a text file with the story's title as the filename.
   *
   * @param story the story to be saved
   * @throws IOException if an I/O error occurs while saving the story
   */
  public static void saveStoryToFile(Story story) throws IOException {
    Objects.requireNonNull(story, "Story cannot be null");
    String storyContent = buildStoryContent(story);
    writeFile(story.getTitle(), storyContent);
  }

  /**
   * Builds the content of a story as a string.
   *
   * @param story the story to build content for
   * @return the story content as a string
   */
  private static String buildStoryContent(Story story) {

    String openingPassageContent =
        buildPassageContent(story.getOpeningPassage())
            + buildLinksContent(story.getOpeningPassage().getLinks());

    String passagesContent =
        story.getPassages().stream()
            .map(passage -> buildPassageContent(passage) + buildLinksContent(passage.getLinks()))
            .collect(Collectors.joining());

    return String.join(NEWLINE, story.getTitle(), "", openingPassageContent, passagesContent);
  }

  /**
   * Builds the content of a passage as a string.
   *
   * @param passage the passage to build content for
   * @return the passage content as a string
   */
  private static String buildPassageContent(Passage passage) {
    Objects.requireNonNull(passage, "Passage cannot be null");
    return PASSAGE_PREFIX + passage.getTitle() + NEWLINE + passage.getContent() + NEWLINE;
  }

  /**
   * Builds the content of a link as a string.
   *
   * @param link the link to build content for
   * @return the link content as a string
   */
  private static String buildLinkContent(Link link) {
    Objects.requireNonNull(link, "Link cannot be null");
    String linkContent =
        LINK_TEXT_PREFIX
            + link.getText()
            + LINK_TEXT_SUFFIX
            + LINK_REF_PREFIX
            + link.getRef()
            + LINK_REF_SUFFIX
            + NEWLINE;

    return linkContent + buildActionContent(link.getActions());
  }

  /**
   * Builds the content of a collection of links as a string.
   *
   * @param links the collection of links to build content for
   * @return the links content as a string
   */
  private static String buildLinksContent(Collection<Link> links) {
    Objects.requireNonNull(links, "Links collection cannot be null");
    StringBuilder sb = new StringBuilder();
    links.forEach(link -> sb.append(buildLinkContent(link)));
    sb.append(NEWLINE);
    return sb.toString();
  }

  /**
   * Builds the content of a collection of actions as a string.
   *
   * @param actions the collection of actions to build content for
   * @return the actions content as a string
   */
  private static String buildActionContent(Collection<Action> actions) {
    Objects.requireNonNull(actions, "Actions collection cannot be null");
    return actions.stream()
        .map(Action::toString)
        .collect(Collectors.joining(SEPARATOR, ACTION_PREFIX, ACTION_SUFFIX + NEWLINE));
  }

  /**
   * Writes the given content to a text file with the specified filename.
   *
   * @param filename the filename to use when saving the content
   * @param content the content to be written to the file
   * @throws IOException if an I/O error occurs while writing the content to the file
   */
  private static void writeFile(String filename, String content) throws IOException {
    Objects.requireNonNull(filename, "Filename cannot be null");
    Files.createDirectories(FILE_PATH);
    Path filePath = FILE_PATH.resolve(filename + FILE_ENDING);
    Files.writeString(filePath, content);
  }
}

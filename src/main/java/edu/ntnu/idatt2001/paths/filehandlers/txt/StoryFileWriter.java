package edu.ntnu.idatt2001.paths.filehandlers.txt;

import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

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

  private static Path FILE_PATH = Paths.get(FILEPATH);

  public static void saveStoryToFile(Story story) throws IOException {
    String storyContent = buildStoryContent(story);
    writeFile(story.getTitle(), storyContent);
  }

  private static String buildStoryContent(Story story) {
    StringBuilder sb = new StringBuilder();
    sb.append(story.getTitle()).append(NEWLINE).append(NEWLINE);
    sb.append(buildPassageContent(story.getOpeningPassage()));
    sb.append(buildLinksContent(story.getOpeningPassage().getLinks()));

    for (Passage passage : story.getPassages()) {
      sb.append(buildPassageContent(passage));
      sb.append(buildLinksContent(passage.getLinks()));
    }
    return sb.toString();
  }

  private static String buildPassageContent(Passage passage) {
    StringBuilder sb = new StringBuilder();
    sb.append(PASSAGE_PREFIX).append(passage.getTitle()).append(NEWLINE);
    sb.append(passage.getContent()).append(NEWLINE);
    return sb.toString();
  }

  private static String buildLinkContent(Link link) {
    StringBuilder sb = new StringBuilder();
    sb.append(LINK_TEXT_PREFIX)
        .append(link.getText())
        .append(LINK_TEXT_SUFFIX)
        .append(LINK_REF_PREFIX)
        .append(link.getRef())
        .append(LINK_REF_SUFFIX)
        .append(NEWLINE);
    sb.append(buildActionContent(link.getActions()));
    return sb.toString();
  }

  private static String buildLinksContent(Collection<Link> links) {
    StringBuilder sb = new StringBuilder();
    links.forEach(link -> sb.append(buildLinkContent(link)));
    sb.append(NEWLINE);
    return sb.toString();
  }

  private static String buildActionContent(Collection<Action> actions) {
    StringBuilder sb = new StringBuilder(ACTION_PREFIX);
    actions.forEach(action -> sb.append(action.toString()).append(", "));
    sb.append(ACTION_SUFFIX).append(NEWLINE);
    return sb.toString();
  }

  private static void writeFile(String filename, String content) throws IOException {
    Files.createDirectories(FILE_PATH);
    FILE_PATH = FILE_PATH.resolve(filename + FILE_ENDING);
    Files.write(FILE_PATH, content.getBytes());
  }
}

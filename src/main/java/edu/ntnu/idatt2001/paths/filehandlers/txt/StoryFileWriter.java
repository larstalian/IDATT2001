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
import java.util.stream.Collectors;

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

  private StoryFileWriter() {
  }

  public static void saveStoryToFile(Story story) throws IOException {
    String storyContent = buildStoryContent(story);
    writeFile(story.getTitle(), storyContent);
  }

  private static String buildStoryContent(Story story) {
    String openingPassageContent = buildPassageContent(story.getOpeningPassage()) +
            buildLinksContent(story.getOpeningPassage().getLinks());

    String passagesContent = story.getPassages().stream()
            .map(passage -> buildPassageContent(passage) + buildLinksContent(passage.getLinks()))
            .collect(Collectors.joining());

    return String.join(NEWLINE, story.getTitle(), "", openingPassageContent, passagesContent);
  }


  private static String buildPassageContent(Passage passage) {
    return PASSAGE_PREFIX + passage.getTitle() + NEWLINE + passage.getContent() + NEWLINE;
  }

  private static String buildLinkContent(Link link) {
    String linkContent = LINK_TEXT_PREFIX + link.getText() + LINK_TEXT_SUFFIX +
            LINK_REF_PREFIX + link.getRef() + LINK_REF_SUFFIX + NEWLINE;
    return linkContent + buildActionContent(link.getActions());
  }

  private static String buildLinksContent(Collection<Link> links) {
    StringBuilder sb = new StringBuilder();
    links.forEach(link -> sb.append(buildLinkContent(link)));
    sb.append(NEWLINE);
    return sb.toString();
  }

  private static String buildActionContent(Collection<Action> actions) {
    return actions.stream()
        .map(Action::toString)
        .collect(Collectors.joining(SEPARATOR, ACTION_PREFIX, ACTION_SUFFIX + NEWLINE));
  }


  private static void writeFile(String filename, String content) throws IOException {
    Files.createDirectories(FILE_PATH);
    Path filePath = FILE_PATH.resolve(filename + FILE_ENDING);
    Files.write(filePath, content.getBytes());
  }
}

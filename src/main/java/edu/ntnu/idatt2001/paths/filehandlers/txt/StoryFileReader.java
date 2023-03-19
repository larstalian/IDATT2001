package edu.ntnu.idatt2001.paths.filehandlers.txt;

import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StoryFileReader {

  private static final String DELIMITER = System.getProperty("line.separator");
  private static final String FILE_ENDING = ".txt";
  private static final String FILEPATH = "src/main/resources/stories/txt/";
  private static final String PASSAGE_PREFIX = "::";
  private static final String LINK_TEXT_PREFIX = "\\[";
  private static final String LINK_TEXT_SUFFIX = "\\]";
  private static final String LINK_REF_PREFIX = "\\(";
  private static final String LINK_REF_SUFFIX = "\\)";

  private static Path FILE_PATH = Paths.get(FILEPATH);

  public static Story readStoryFromFile(String fileName) throws IOException {
    String storyContent = readFile(fileName);
    return parseStoryContent(storyContent);
  }

  private static String readFile(String fileName) throws IOException {
    Path filePath = FILE_PATH.resolve(fileName + FILE_ENDING);
    return Files.readString(filePath);
  }

  private static Story parseStoryContent(String storyContent) {
    String[] lines = storyContent.split(DELIMITER);
    String storyTitle = lines[0];
    String openingPassageTitle = lines[2].substring(PASSAGE_PREFIX.length());
    String openingPassageContent = lines[3];
    Passage openingPassage = new Passage(openingPassageTitle, openingPassageContent);
    addLinks(openingPassage, 4, lines);
    Story story = new Story(storyTitle, openingPassage);

    // now for the rest of the passages
    for (int i = 4; i < lines.length; i++) {
      if (lines[i].startsWith(PASSAGE_PREFIX)) {
        String passageTitle = lines[i].substring(PASSAGE_PREFIX.length());
        String passageContent = lines[i + 1];
        Passage passage = new Passage(passageTitle, passageContent);
        addLinks(passage, i + 2, lines);
        story.addPassage(passage);
      }
    }

    return story;
  }

  private static void addLinks(Passage passage, int index, String[] lines) {
    for (int i = index; i < lines.length; i++) {
      if (lines[i].startsWith(PASSAGE_PREFIX) || lines[i].isEmpty()) {
        break;
      }
      String[] linkTextAndRef = lines[i].split(LINK_TEXT_SUFFIX + LINK_REF_PREFIX);
      String linkText = linkTextAndRef[0].substring(LINK_TEXT_PREFIX.length());
      String linkRef =
          linkTextAndRef[1].substring(0, linkTextAndRef[1].length() - LINK_REF_SUFFIX.length());
      passage.addLink(new Link(linkText, linkRef));
    }
  }
}

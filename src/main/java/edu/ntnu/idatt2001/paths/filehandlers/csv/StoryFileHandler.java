package edu.ntnu.idatt2001.paths.filehandlers.csv;

import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

public class StoryFileHandler {

  private static final String NEWLINE = "\n";
  private static final String FILE_ENDING = ".csv";
  private static final String FILE_ENDING_TXT = ".txt";
  private static final String PASSAGE_PREFIX = "::";
  private static final String FILEPATH = "src/main/resources/stories/csv/";
  private static final String LINK_TEXT_PREFIX = "[";
  private static final String LINK_TEXT_SUFFIX = "]";
  private static final String LINK_REF_PREFIX = "(";
  private static final String LINK_REF_SUFFIX = ")";

  public static void saveStoryToFile(Story story, String fileName) throws IOException {
    File file = new File(FILEPATH + fileName + FILE_ENDING);
    StringBuilder sb = storyStringBuilder(story);
    fileWriter(file, sb);
  }

  private static StringBuilder storyStringBuilder(Story story) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append(story.getTitle()).append(NEWLINE).append(NEWLINE);
    sb.append(passageStringBuilder(story.getOpeningPassage())).append(NEWLINE);

    for (Passage passage : story.getPassages()) {
      sb.append(passageStringBuilder(passage));
      sb.append(linksStringBuilder(passage.getLinks()));
    }
    return sb;
  }

  private static StringBuilder passageStringBuilder(Passage passage) {
    StringBuilder sb = new StringBuilder();
    sb.append(PASSAGE_PREFIX).append(passage.getTitle()).append(NEWLINE);
    sb.append(passage.getContent()).append(NEWLINE);
    return sb;
  }

  private static StringBuilder linkStringBuilder(Link link) {
    StringBuilder sb = new StringBuilder();
    sb.append(LINK_TEXT_PREFIX)
        .append(link.getText())
        .append(LINK_TEXT_SUFFIX)
        .append(LINK_REF_PREFIX)
        .append(link.getRef())
        .append(LINK_REF_SUFFIX)
        .append(NEWLINE);
    return sb;
  }

  private static StringBuilder linksStringBuilder(Collection<Link> links) {
    StringBuilder sb = new StringBuilder();
    links.forEach(link -> sb.append(linkStringBuilder(link)));
    sb.append(NEWLINE);
    return sb;
  }

  public static void main(String[] args) {
    Passage openingPassage = new Passage("Opening Passage", "This is the opening passage");
    Story story = new Story("Test Story", openingPassage);
    openingPassage.addLink(new Link("Go to the forest", "Forest"));
    Passage forestPassage = new Passage("Forest", "You are in a forest.");
    story.addPassage(forestPassage);
    story.addPassage(new Passage("Cave", "You are in a cave."));
    story.getPassage(new Link("Forest", "Forest")).addLink(new Link("Go to the cave", "Cave"));
    story.getPassage(new Link("Forest", "Forest")).addLink(new Link("Go to the river", "River"));
    story.addPassage(new Passage("River", "You are by a river."));
    try {
      Files.createDirectories(Paths.get(FILEPATH));
      saveStoryToFile(story, story.getTitle());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void fileWriter(File file, StringBuilder sb) {
    try (FileWriter fileWriter = new FileWriter(file)) {
      fileWriter.write(sb.toString());

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}

package edu.ntnu.idatt2001.paths.model.media;

import edu.ntnu.idatt2001.paths.model.story.Passage;
import javafx.scene.layout.Region;

public class BackgroundHandler {

  private static final String IMAGE_PATH = "/images/";
  private static final String STORIES_PATH = "/stories/";
  private static final String IMAGE_EXTENSION = ".png";
  private static BackgroundHandler instance;

  private BackgroundHandler() {
  }

  public static BackgroundHandler getInstance() {
    if (instance == null) {
      instance = new BackgroundHandler();
    }
    return instance;
  }
  public boolean hasBackground(Passage passage, String storyTitle) {
    String path = STORIES_PATH + storyTitle + IMAGE_PATH;
    String fileName = passage.getTitle() + IMAGE_EXTENSION;
    return getClass().getResource(path + fileName) != null;
  }

  public void updateBackground(Region region, Passage passage, String storyTitle) {
    String backgroundImageUrl;
    if (hasBackground(passage, storyTitle)) {
      String path = "/stories/" + storyTitle + "/images/";
      String fileName = passage.getTitle().toLowerCase() + IMAGE_EXTENSION;
      backgroundImageUrl = getClass().getResource(path + fileName).toExternalForm();
    } else {
      String defaultBackground = passage.getMood().toString().toLowerCase() + IMAGE_EXTENSION;
      backgroundImageUrl = getClass().getResource(IMAGE_PATH + defaultBackground).toExternalForm();
    }
    setBackgroundImageUrl(region, backgroundImageUrl);
  }

  private void setBackgroundImageUrl(Region region, String backgroundImageUrl) {
    region.setStyle("-fx-background-image: url('" + backgroundImageUrl + "');");
  }
}

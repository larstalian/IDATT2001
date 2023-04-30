package edu.ntnu.idatt2001.paths.model.media;

import edu.ntnu.idatt2001.paths.model.story.Passage;
import javafx.scene.layout.Region;

/**
 * A singleton class responsible for handling and updating background images based on the given
 * passage and story title. This class is responsible for determining if a passage has a custom
 * background image and updating the background image of a given region accordingly. If a custom
 * background is not available, a default background based on the passage's mood will be used.
 */
public class BackgroundHandler {

  private static final String IMAGE_PATH = "/images/passage-moods/";
  private static final String STORIES_PATH = "/stories/";
  private static final String IMAGE_EXTENSION = ".png";
  private static BackgroundHandler instance;

  /** Private constructor to prevent multiple instances. */
  private BackgroundHandler() {}

  /**
   * Retrieves the singleton instance of BackgroundHandler.
   *
   * @return the instance of BackgroundHandler.
   */
  public static BackgroundHandler getInstance() {
    if (instance == null) {
      instance = new BackgroundHandler();
    }
    return instance;
  }

  /**
   * Checks if the passage has a custom background image.
   *
   * @param passage the passage to check for a background image.
   * @param storyTitle the title of the story containing the passage.
   * @return {@code true} if the passage has a custom background, {@code false} otherwise.
   */
  public boolean hasBackground(Passage passage, String storyTitle) {
    String path = STORIES_PATH + storyTitle + IMAGE_PATH;
    String fileName = passage.getTitle() + IMAGE_EXTENSION;
    return getClass().getResource(path + fileName) != null;
  }

  /**
   * Updates the background image of the given region based on the passage and story title. If the
   * passage has a custom background, it will be used. Otherwise, a default background based on the
   * passage's mood will be applied.
   *
   * @param region the region to update the background image for.
   * @param passage the passage used to determine the background image.
   * @param storyTitle the title of the story containing the passage.
   */
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

  /**
   * Sets the background image of the given region using the specified URL.
   *
   * @param region the region to set the background image for.
   * @param backgroundImageUrl the URL of the background image.
   */
  private void setBackgroundImageUrl(Region region, String backgroundImageUrl) {
    region.setStyle("-fx-background-image: url('" + backgroundImageUrl + "');");
  }
}

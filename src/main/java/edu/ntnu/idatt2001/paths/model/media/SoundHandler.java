package edu.ntnu.idatt2001.paths.model.media;

import edu.ntnu.idatt2001.paths.model.story.Passage;
import java.util.Objects;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * A singleton class responsible for handling and updating the background music based on the given
 * passage and story title. This class is responsible for determining if a passage has custom music
 * and updating the background music accordingly. If custom music is not available, a default music
 * based on the passage's mood will be used. Additionally, this class manages the playback of menu
 * music.
 */
public class SoundHandler {

  private static final String SOUND_PATH = "/sound/";
  private static final String SOUND_EXTENSION = ".mp3";
  private static SoundHandler instance;
  private MediaPlayer mediaPlayer;
  private String currentMusicFile;

  /** Private constructor to prevent multiple instances. */
  private SoundHandler() {}

  /**
   * Retrieves the singleton instance of SoundHandler.
   *
   * @return the instance of SoundHandler.
   */
  public static SoundHandler getInstance() {
    if (instance == null) {
      instance = new SoundHandler();
    }
    return instance;
  }

  /**
   * Checks if the passage has custom background music.
   *
   * @param passage the passage to check for custom background music.
   * @param storyTitle the title of the story containing the passage.
   * @return true if the passage has custom background music, false otherwise.
   */
  public boolean hasMusic(Passage passage, String storyTitle) {
    String path = SOUND_PATH + storyTitle + SOUND_PATH;
    String fileName = passage.getTitle() + SOUND_EXTENSION;
    return getClass().getResource(path + fileName) != null;
  }

  /**
   * Updates the background music based on the passage and story title. If the passage has custom
   * music, it will be used. Otherwise, a default music based on the passage's mood will be applied.
   *
   * @param passage the passage used to determine the background music.
   * @param storyTitle the title of the story containing the passage.
   */
  public void updateMusic(Passage passage, String storyTitle) {
    String musicUrl;
    if (hasMusic(passage, storyTitle)) {
      String path = SOUND_PATH + storyTitle + "/sound/";
      String fileName = passage.getTitle().toLowerCase() + SOUND_EXTENSION;
      musicUrl = Objects.requireNonNull(getClass().getResource(path + fileName)).toExternalForm();
    } else {
      musicUrl = SOUND_PATH + passage.getMood().toString().toLowerCase() + SOUND_EXTENSION;
    }
    if (!musicUrl.equals(currentMusicFile)) { // check if the music file has changed
      playBackgroundMusic(musicUrl);
      currentMusicFile = musicUrl; // update the current music file
    }
  }

  /**
   * Plays the background music using the specified URL.
   *
   * @param musicFileUrl the URL of the background music.
   */
  private void playBackgroundMusic(String musicFileUrl) {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
      mediaPlayer.dispose();
    }

    String musicUrl = Objects.requireNonNull(getClass().getResource(musicFileUrl)).toExternalForm();
    Media media = new Media(musicUrl);
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.play();
  }

  /**
   * Plays the menu music. If the menu music is already playing, the method will return without
   * doing anything.
   */
  public void playMenuMusic() {
    String menuMusicUrl = SOUND_PATH + "main_menu" + SOUND_EXTENSION;
    playBackgroundMusic(menuMusicUrl);
  }
}

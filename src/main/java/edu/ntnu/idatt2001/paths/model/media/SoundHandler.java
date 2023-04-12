package edu.ntnu.idatt2001.paths.model.media;

import edu.ntnu.idatt2001.paths.model.story.Passage;
import java.util.Objects;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundHandler {

  private static final String SOUND_PATH = "/sound/";
  private static final String SOUND_EXTENSION = ".mp3";
  private static SoundHandler instance;
  private MediaPlayer mediaPlayer;
  private String currentMusicFile;

  private SoundHandler() {}

  public static SoundHandler getInstance() {
    if (instance == null) {
      instance = new SoundHandler();
    }
    return instance;
  }

  public boolean hasMusic(Passage passage, String storyTitle) {
    String path = SOUND_PATH + storyTitle + SOUND_PATH;
    String fileName = passage.getTitle() + SOUND_EXTENSION;
    return getClass().getResource(path + fileName) != null;
  }

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

  public void playMenuMusic() {
    if (isMenuMusicPlaying()) {
      return;
    }

    String menuMusicUrl = SOUND_PATH + "main_menu" + SOUND_EXTENSION;
    playBackgroundMusic(menuMusicUrl);
  }

  private boolean isMenuMusicPlaying() {
    return mediaPlayer != null; 
  }
}

package edu.ntnu.idatt2001.paths.model.media;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2001.paths.model.story.Mood;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for SoundHandler only testing the hasMusic method. Additional testing is difficult to
 * implement because of the MediaPlayer.
 */
class SoundHandlerTest {
  private SoundHandler soundHandler;
  private Passage passage;

  @BeforeEach
  void setUp() {
    soundHandler = SoundHandler.getInstance();
    passage = new Passage("Test Title", "Test Text", Mood.HAPPY, true);
  }

  @Test
  void testHasMusic() {
    assertFalse(soundHandler.hasMusic(passage, "Nonexistent Story"));
  }
}

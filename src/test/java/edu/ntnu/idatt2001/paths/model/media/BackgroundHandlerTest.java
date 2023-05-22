package edu.ntnu.idatt2001.paths.model.media;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import edu.ntnu.idatt2001.paths.model.story.Mood;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import javafx.scene.layout.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BackgroundHandlerTest {
  private BackgroundHandler backgroundHandler;
  private Passage passage;
  private Region region;
  private String storyTitle;

  @BeforeEach
  void setUp() {
    backgroundHandler = BackgroundHandler.getInstance();
    passage = new Passage("Test Title", "Test Content", Mood.HAPPY, true);
    region = new Region();
    storyTitle = "Test Story";
  }

  @Test
  @DisplayName("Should return false when custom background image does not exist")
  void testNoBackground() {
    boolean result = backgroundHandler.hasBackground(passage, storyTitle);
    assertThat(result, is(false));
  }

  @Test
  @DisplayName("Should update background image to default when custom background does not exist")
  void testUpdateBackgroundDefault() {
    backgroundHandler.updateBackground(region, passage, storyTitle);
    String style = region.getStyle();
    assertThat(style, containsString("/images/passage-moods/happy.png"));
  }
}

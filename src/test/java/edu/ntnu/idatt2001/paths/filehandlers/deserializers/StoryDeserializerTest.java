package edu.ntnu.idatt2001.paths.filehandlers.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class StoryDeserializerTest {
  private Story story;

  @BeforeEach
  public void setUp() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Story.class, new StoryDeserializer());
    objectMapper.registerModule(module);

    String json =
        "{\"title\":\"Test Story\",\"openingPassage\":{\"title\":\"Opening\",\"content\":\"This is the beginning.\"},\"passages\":{\"Passage1\":{\"title\":\"Passage1\",\"content\":\"This is passage 1.\"}}}";
    story = objectMapper.readValue(json, Story.class);
  }

  @Test
  public void deserialize_validJson_storyTitleIsCorrect() {
    assertThat(story.getTitle(), is(equalTo("Test Story")));
  }

  @Test
  public void deserialize_validJson_openingPassageIsCorrect() {
    Passage openingPassage = story.getOpeningPassage();
    assertThat(openingPassage.getTitle(), is(equalTo("Opening")));
    assertThat(openingPassage.getContent(), is(equalTo("This is the beginning.")));
  }

  @Test
  public void deserialize_validJson_passage1IsCorrect() {
    Passage passage1 = story.getPassage(new Link("Passage1", "Passage1"));
    assertThat(passage1, is(notNullValue()));
    assertThat(passage1.getTitle(), is(equalTo("Passage1")));
    assertThat(passage1.getContent(), is(equalTo("This is passage 1.")));
  }
}

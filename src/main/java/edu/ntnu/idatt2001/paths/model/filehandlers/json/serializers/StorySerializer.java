package edu.ntnu.idatt2001.paths.model.filehandlers.json.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import edu.ntnu.idatt2001.paths.model.story.Story;
import java.io.IOException;

/**
 * A custom serializer for the {@link Story} class, using the Jackson library. This class is
 * responsible for serializing a {@link Story} object into a JSON representation. The custom
 * serialization is necessary due to the passage map in the {@link Story} being of type
 * {@code Map<Link, Passage>}.
 *
 * <p>When de-serializing use the {@link StoryDeserializer} class.
 *
 * <p>To use this serializer with an {@link com.fasterxml.jackson.databind.ObjectMapper}. Register
 * it with a {@link com.fasterxml.jackson.databind.module.SimpleModule} and add the module to the
 * ObjectMapper. For example:
 *
 * <pre>{@code
 * ObjectMapper objectMapper = new ObjectMapper();
 * SimpleModule module = new SimpleModule();
 * module.addSerializer(Story.class, new StorySerializer());
 * objectMapper.registerModule(module);
 * }</pre>
 *
 * @see Story
 * @see StoryDeserializer
 * @see com.fasterxml.jackson.databind.ObjectMapper
 */
public class StorySerializer extends JsonSerializer<Story> {

  @Override
  public void serialize(
      Story story, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("title", story.getTitle());
    jsonGenerator.writeObjectField("openingPassage", story.getOpeningPassage());

    jsonGenerator.writeObjectFieldStart("passages");
    for (Passage passage : story.getPassages()) {
      jsonGenerator.writeFieldName(passage.getTitle());
      jsonGenerator.writeObject(passage);
    }
    jsonGenerator.writeEndObject();

    jsonGenerator.writeEndObject();
  }
}

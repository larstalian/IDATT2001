package edu.ntnu.idatt2001.paths.filehandlers.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.idatt2001.paths.story.Link;

import java.io.IOException;

/**
 * A custom deserializer for the Link class, using Jackson library. This class is responsible for
 * deserializing JSON data into a Link object.
 *
 * @see Link
 * @see edu.ntnu.idatt2001.paths.filehandlers.StoryFileHandler
 */
public class LinkDeserializer extends JsonDeserializer<Link> {

  /**
   * Deserializes JSON data into a Link object.
   *
   * @param jsonParser the JSON parser
   * @param deserializationContext the deserialization context
   * @return the deserialized Link object
   * @throws IOException if there is an issue reading from the JSON parser
   */
  @Override
  public Link deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String text = node.get("text").asText();
    String ref = node.get("ref").asText();

    return new Link(text, ref);
  }
}

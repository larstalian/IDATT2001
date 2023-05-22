package edu.ntnu.idatt2001.paths.model.filehandlers.json.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import edu.ntnu.idatt2001.paths.model.story.Story;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * A custom deserializer for the {@link Story} class, using the Jackson library. This class is
 * responsible for deserializing JSON node to a {@link Story} object. The custom deserialization is
 * necessary due to the passage map in the {@link Story} being of type {@code Map<Link, Passage>}.
 *
 * <p>When serializing use the {@link StorySerializer} class.
 *
 * <p>To use the deserializer with an {@link com.fasterxml.jackson.databind.ObjectMapper}. Register
 * it with a {@link com.fasterxml.jackson.databind.module.SimpleModule} and add the module to the
 * ObjectMapper. For example:
 *
 * <pre>{@code
 * ObjectMapper objectMapper = new ObjectMapper();
 * SimpleModule module = new SimpleModule();
 * module.addDeserializer(Story.class, new StoryDeserializer());
 * objectMapper.registerModule(module);
 * }</pre>
 *
 * @see Story
 * @see StoryDeserializer
 * @see com.fasterxml.jackson.databind.ObjectMapper
 */
public class StoryDeserializer extends JsonDeserializer<Story> {

  /**
   * Deserializes JSON data into a Story object.
   *
   * @param jsonParser             the JSON parser
   * @param deserializationContext the deserialization context
   * @return the deserialized Story object
   * @throws IOException             if there is an issue reading from the JSON parser
   * @throws JsonProcessingException if there is an issue processing the JSON data
   */
  @Override
  public Story deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    String title = node.get("title").asText();
    Passage openingPassage =
        jsonParser.getCodec().treeToValue(node.get("openingPassage"), Passage.class);
    Story story = new Story(title, openingPassage);

    JsonNode passagesNode = node.get("passages");
    Iterator<Map.Entry<String, JsonNode>> fields = passagesNode.fields();

    while (fields.hasNext()) {
      Map.Entry<String, JsonNode> entry = fields.next();
      Passage passage = createPassageFromJsonNode(jsonParser, entry.getValue());
      story.addPassage(passage);
    }
    return story;
  }

  /**
   * Creates a Passage object from the given JSON node.
   *
   * @param jsonParser  the JSON parser
   * @param passageNode the JSON node containing the passage data
   * @return the created Passage object
   * @throws JsonProcessingException if there is an issue processing the JSON data
   */
  private Passage createPassageFromJsonNode(JsonParser jsonParser, JsonNode passageNode)
      throws JsonProcessingException {
    return jsonParser.getCodec().treeToValue(passageNode, Passage.class);
  }
}

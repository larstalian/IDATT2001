package edu.ntnu.idatt2001.paths.filehandlers.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.actions.ActionFactory;
import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Story;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom deserializer for the {@link Link} class, using the Jackson library. This class is
 * responsible for deserializing JSON data into a {@link Link} object. The custom deserialization is
 * necessary because the {@link Link} object contains a list of {@link Action} objects that should
 * be deserialized according to their respective types. The {@link Link} objects are also a key in
 * in the {@code Map<Link, Passage>} in the {@link Story} class.
 *
 * <p>There is not a custom serializer for {@link Link} class as Jackson can serialize the class
 * using the default serializer.
 *
 * <p>To use this deserializer with an {@link com.fasterxml.jackson.databind.ObjectMapper}, register
 * it with a {@link com.fasterxml.jackson.databind.module.SimpleModule} and add the module to the
 * ObjectMapper. For example:
 *
 * <pre>{@code
 * ObjectMapper objectMapper = new ObjectMapper();
 * SimpleModule module = new SimpleModule();
 * module.addDeserializer(Link.class, new LinkDeserializer());
 * objectMapper.registerModule(module);
 * }</pre>
 *
 * @see Link
 * @see edu.ntnu.idatt2001.paths.actions.Action
 * @see edu.ntnu.idatt2001.paths.actions.ActionFactory
 * @see edu.ntnu.idatt2001.paths.story.Story
 * @see com.fasterxml.jackson.databind.ObjectMapper
 */
public class LinkDeserializer extends JsonDeserializer<Link> {

  @Override
  public Link deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    String text = node.get("text").asText();
    String ref = node.get("ref").asText();

    // Deserialize actions
    JsonNode actionsNode = node.get("actions");
    List<Action> actions = new ArrayList<>();

    for (JsonNode actionNode : actionsNode) {
      Action action = createActionFromJsonNode(actionNode);
      actions.add(action);
    }
    Link link = new Link(text, ref);
    link.getActions().addAll(actions);

    return link;
  }

  /**
   * Creates an Action object from the given JSON node.
   *
   * @param actionNode the JSON node containing the action data
   * @return the created Action object
   */
  private Action createActionFromJsonNode(JsonNode actionNode) {
    String actionName = actionNode.fieldNames().next();
    String actionValue = actionNode.get(actionName).asText();
    return ActionFactory.createAction(actionName, actionValue);
  }
}

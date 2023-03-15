package edu.ntnu.idatt2001.paths.filehandlers.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.actions.ActionFactory;
import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Passage;

import java.io.IOException;

/**
 * A custom deserializer for the Passage class, using Jackson library. This class is responsible for
 * deserializing JSON data into a Passage object.
 *
 * @see Passage
 * @see edu.ntnu.idatt2001.paths.filehandlers.StoryFileHandler
 */
public class PassageDeserializer extends JsonDeserializer<Passage> {

  /**
   * Deserializes JSON data into a Passage object.
   *
   * @param jp the JSON parser
   * @param ctxt the deserialization context
   * @return the deserialized Passage object
   * @throws IOException if there is an issue reading from the JSON parser
   */
  @Override
  public Passage deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode node = jp.getCodec().readTree(jp);
    String title = node.get("title").asText();
    String content = node.get("content").asText();

    Passage passage = new Passage(title, content);

    if (node.has("links")) {
      for (JsonNode linkNode : node.get("links")) {
        Link link = createLinkFromJsonNode(linkNode);
        passage.addLink(link);
      }
    }

    return passage;
  }

  /**
   * Creates a Link object from the given JSON node.
   *
   * @param linkNode the JSON node containing the link data
   * @return the created Link object
   */
  private Link createLinkFromJsonNode(JsonNode linkNode) {
    String linkText = linkNode.get("text").asText();
    String linkRef = linkNode.get("ref").asText();
    Link link = new Link(linkText, linkRef);

    if (linkNode.has("actions")) {
      for (JsonNode actionNode : linkNode.get("actions")) {
        Action action = createActionFromJsonNode(actionNode);
        link.addAction(action);
      }
    }

    return link;
  }

  /**
   * Creates an Action object from the given JSON node.
   *
   * @param actionNode the JSON node containing the action data
   * @return the created Action object
   */
  private Action createActionFromJsonNode(JsonNode actionNode) {
    String actionName = actionNode.get("name").asText();
    String actionValue = actionNode.get("value").asText();
    return ActionFactory.createAction(actionName, actionValue);
  }
}

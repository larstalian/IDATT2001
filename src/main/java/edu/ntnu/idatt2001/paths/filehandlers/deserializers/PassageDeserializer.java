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

public class PassageDeserializer extends JsonDeserializer<Passage> {

    @Override
    public Passage deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String title = node.get("title").asText();
        String content = node.get("content").asText();

        Passage passage = new Passage(title, content);

        if (node.has("links")) {
            for (JsonNode linkNode : node.get("links")) {
                String linkText = linkNode.get("text").asText();
                String linkRef = linkNode.get("ref").asText();
                Link link = new Link(linkText, linkRef);

                if (linkNode.has("actions")) {
                    for (JsonNode actionNode : linkNode.get("actions")) {
                        String actionName = actionNode.get("name").asText();
                        String actionValue = actionNode.get("value").asText();
                        Action action = ActionFactory.createAction(actionName, actionValue);
                        link.addAction(action);
                    }
                }

                passage.addLink(link);
            }
        }

        return passage;
    }
}

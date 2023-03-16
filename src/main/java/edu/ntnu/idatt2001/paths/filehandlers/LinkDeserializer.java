package edu.ntnu.idatt2001.paths.filehandlers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.actions.ActionFactory;
import edu.ntnu.idatt2001.paths.story.Link;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LinkDeserializer extends JsonDeserializer<Link> {

    @Override
    public Link deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String text = node.get("text").asText();
        String ref = node.get("ref").asText();

        // Deserialize actions
        JsonNode actionsNode = node.get("actions");
        List<Action> actions = new ArrayList<>();
        ActionFactory actionFactory = new ActionFactory();

        for (JsonNode actionNode : actionsNode) {
            Action action = createActionFromJsonNode(actionNode);
            actions.add(action);
        }
        Link link = new Link(text, ref);
        link.getActions().addAll(actions);

        return link;
    }

    private Action createActionFromJsonNode(JsonNode actionNode) {
        String actionName = actionNode.fieldNames().next();
        String actionValue = actionNode.get(actionName).asText();
        return ActionFactory.createAction(actionName, actionValue);
    }
}
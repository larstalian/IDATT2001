package edu.ntnu.idatt2001.paths.filehandlers.deserializers;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class StoryDeserializer extends JsonDeserializer<Story> {

    @Override
    public Story deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String title = node.get("title").asText();
        Passage openingPassage = jsonParser.getCodec().treeToValue(node.get("openingPassage"), Passage.class);
        Story story = new Story(title, openingPassage);

        JsonNode passagesNode = node.get("passages");
        Iterator<Map.Entry<String, JsonNode>> fields = passagesNode.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            Passage passage = jsonParser.getCodec().treeToValue(entry.getValue(), Passage.class);
            story.addPassage(passage);
        }

        return story;
    }
}
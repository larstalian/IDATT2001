package edu.ntnu.idatt2001.paths.fileHandelers.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.idatt2001.paths.story.Link;

import java.io.IOException;

public class LinkDeserializer extends JsonDeserializer<Link> {

  @Override
  public Link deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String text = node.get("text").asText();
    String ref = node.get("ref").asText();

    return new Link(text, ref);
  }
}

package edu.ntnu.idatt2001.paths.filehandlers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;

import java.io.IOException;

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

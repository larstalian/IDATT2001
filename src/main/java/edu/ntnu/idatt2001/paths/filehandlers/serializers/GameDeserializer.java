package edu.ntnu.idatt2001.paths.filehandlers.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.idatt2001.paths.game.Game;
import edu.ntnu.idatt2001.paths.game.Player;
import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.filehandlers.factories.GoalFactory;
import edu.ntnu.idatt2001.paths.story.Story;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom deserializer for the {@link Game} class, using the Jackson library. This class is
 * responsible for deserializing JSON data into a {@link Game} object. The custom deserialization is
 * necessary because the {@link Game} object contains a {@link Player}, a {@link Story}, and a list
 * of {@link Goal} objects that should be deserialized according to their respective types. The
 * {@link Goal} objects are deserialized using the {@link GoalFactory}.
 *
 * <p>To use this deserializer with an {@link com.fasterxml.jackson.databind.ObjectMapper}, register
 * it with a {@link com.fasterxml.jackson.databind.module.SimpleModule} and add the module to the
 * ObjectMapper. For example:
 *
 * <pre>{@code
 * ObjectMapper objectMapper = new ObjectMapper();
 * SimpleModule module = new SimpleModule();
 * module.addDeserializer(Game.class, new GameDeserializer());
 * objectMapper.registerModule(module);
 * }</pre>
 *
 * @see Game
 * @see Player
 * @see Story
 * @see Goal
 * @see GoalFactory
 * @see com.fasterxml.jackson.databind.ObjectMapper
 */
public class GameDeserializer extends JsonDeserializer<Game> {

  @Override
  public Game deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    JsonNode gameNode = jsonParser.getCodec().readTree(jsonParser);
    Player player = jsonParser.getCodec().treeToValue(gameNode.get("player"), Player.class);
    Story story = jsonParser.getCodec().treeToValue(gameNode.get("story"), Story.class);
    List<Goal> goals = new ArrayList<>();

    JsonNode goalsNode = gameNode.get("goals");
    for (JsonNode goalNode : goalsNode) {
      String goalType = goalNode.fieldNames().next();
      Goal goal = GoalFactory.createGoal(goalType, goalNode);
      goals.add(goal);
    }

    return new Game(player, story, goals);
  }
}

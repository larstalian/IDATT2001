package edu.ntnu.idatt2001.paths.model.filehandlers.json.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.idatt2001.paths.model.filehandlers.factories.GoalFactory;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.GameData;
import edu.ntnu.idatt2001.paths.model.game.Game;
import edu.ntnu.idatt2001.paths.model.game.Player;
import edu.ntnu.idatt2001.paths.model.goals.Goal;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import edu.ntnu.idatt2001.paths.model.story.Story;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom deserializer for the {@link GameData} class, using the Jackson library. This class is
 * responsible for deserializing JSON data into a {@link GameData} object containing the {@link
 * Game} and {@link Passage} objects. The custom deserialization is necessary because the {@link
 * Game} object contains a {@link Player}, a {@link Story}, and a list of {@link Goal} objects that
 * should be deserialized according to their respective types. The {@link Goal} objects are
 * deserialized using the {@link GoalFactory}.
 *
 * <p>To use this deserializer with an {@link com.fasterxml.jackson.databind.ObjectMapper}, register
 * it with a {@link com.fasterxml.jackson.databind.module.SimpleModule} and add the module to the
 * ObjectMapper. For example:
 *
 * <pre>{@code
 * ObjectMapper objectMapper = new ObjectMapper();
 * SimpleModule module = new SimpleModule();
 * module.addDeserializer(GameData.class, new GameDeserializer());
 * objectMapper.registerModule(module);
 * }</pre>
 *
 * @see GameData
 * @see Game
 * @see Player
 * @see Story
 * @see Goal
 * @see GoalFactory
 * @see com.fasterxml.jackson.databind.ObjectMapper
 */
public class GameDeserializer extends JsonDeserializer<GameData> {

  @Override
  public GameData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode gameDataNode = jsonParser.getCodec().readTree(jsonParser);
    JsonNode gameNode = gameDataNode.get("game");
    Player player = jsonParser.getCodec().treeToValue(gameNode.get("player"), Player.class);
    Story story = jsonParser.getCodec().treeToValue(gameNode.get("story"), Story.class);
    List<Goal> goals = new ArrayList<>();

    JsonNode goalsNode = gameNode.get("goals");
    for (JsonNode goalNode : goalsNode) {
      String goalType = goalNode.fieldNames().next();
      Goal goal = GoalFactory.createGoal(goalType, goalNode);
      goals.add(goal);
    }

    Game game = new Game(player, story, goals);
    Passage passage = jsonParser.getCodec().treeToValue(gameDataNode.get("passage"), Passage.class);

    return new GameData(game, passage);
  }
}

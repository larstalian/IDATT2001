package edu.ntnu.idatt2001.paths.filehandlers.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.idatt2001.paths.game.Game;
import edu.ntnu.idatt2001.paths.game.Player;
import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.goals.GoalFactory;
import edu.ntnu.idatt2001.paths.story.Story;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

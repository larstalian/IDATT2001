package edu.ntnu.idatt2001.paths.model.filehandlers.factories;

import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.idatt2001.paths.model.goals.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory class responsible for creating instances of {@link Goal} subclasses based on their goal
 * type and a JsonNode containing the necessary data. This factory class is useful when
 * deserializing goals from a JSON file.
 */
public class GoalFactory {

  /**
   * Creates an instance of a {@link Goal} subclass based on the provided goal type and a JsonNode
   * containing the necessary data. This method is particularly useful when deserializing goals from
   * a JSON file.
   */
  public static Goal createGoal(String goalType, JsonNode goalNode) {
    switch (goalType) {
      case "minimumHealth" -> {
        int minimumHealth = goalNode.get("minimumHealth").asInt();
        return new HealthGoal(minimumHealth);
      }
      case "minimumGold" -> {
        int minimumGold = goalNode.get("minimumGold").asInt();
        return new GoldGoal(minimumGold);
      }
      case "mandatoryItems" -> {
        List<String> mandatoryItems = new ArrayList<>();
        goalNode.get("mandatoryItems").forEach(item -> mandatoryItems.add(item.asText()));
        return new InventoryGoal(mandatoryItems);
      }
      case "minimumScore" -> {
        int minimumScore = goalNode.get("minimumScore").asInt();
        return new ScoreGoal(minimumScore);
      }
      default -> throw new IllegalArgumentException("Invalid goal type: " + goalType);
    }
  }
}

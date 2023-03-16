package edu.ntnu.idatt2001.paths.goals;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class GoalFactory {

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

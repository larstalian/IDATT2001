package edu.ntnu.idatt2001.paths.model.filehandlers.factories;

import edu.ntnu.idatt2001.paths.model.actions.*;

/**
 * Factory class responsible for creating instances of {@link Action} subclasses based on their
 * action name and associated value. This factory class is useful when deserializing actions from a
 * JSON file.
 */
public class ActionFactory {

  /**
   * Creates an Action instance based on the provided action name and value. Used when deserializing
   * a JSON file.
   *
   * @param actionName the name of the action
   * @param actionValue the value associated with the action
   * @return an instance of the Action class corresponding to the action name
   * @throws IllegalArgumentException if the action name is not recognized or the value is invalid
   */
  public static Action createAction(String actionName, String actionValue) {
    switch (actionName) {
      case "goldChange" -> {
        int gold = Integer.parseInt(actionValue);
        return new GoldAction(gold);
      }
      case "healthChange" -> {
        int health = Integer.parseInt(actionValue);
        return new HealthAction(health);
      }
      case "item" -> {
        return new InventoryAction(actionValue);
      }
      case "scoreChange" -> {
        int points = Integer.parseInt(actionValue);
        return new ScoreAction(points);
      }
      default -> throw new IllegalArgumentException("Unrecognized action name: " + actionName);
    }
  }
}

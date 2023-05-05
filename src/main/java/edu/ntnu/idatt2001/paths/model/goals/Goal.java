package edu.ntnu.idatt2001.paths.model.goals;

import edu.ntnu.idatt2001.paths.model.game.Player;

/**
 * The Goal interface represents a goal in the game. It provides a method for checking if the goal
 * is fulfilled.
 *
 * <p>The interface is immutable and cannot be modified.
 *
 * @see Player
 * @see HealthGoal
 * @see ScoreGoal
 * @see InventoryGoal
 * @see GoldGoal
 */
public interface Goal {

  /**
   * Returns true if the goal is fulfilled for the given player, false otherwise.
   *
   * @param player the player to check if the goal is fulfilled for
   * @return true if the goal is fulfilled for the given player, false otherwise
   */
  boolean isFulfilled(Player player);
}

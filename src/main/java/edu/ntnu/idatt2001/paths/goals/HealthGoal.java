package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.game.Player;

/**
 * <p>The <em>HealthGoal</em> class implements the <em>Goal</em> interface and represents a goal of
 * the game
 * based on the minimum amount of health a player must have. </p>
 *
 * <p>The <em>HealthGoal</em> class is a part of the <em>Paths</em> game.
 *
 * <p>It specifies the minimum amount of health a player needs to have to fulfill the goal
 * by calling the {@link #isFulfilled} method and passing in a Player object.</p>
 *
 * @see Goal
 * @see Player
 */
public class HealthGoal implements Goal {

  private final int minimumHealth;

  /**
   * Creates a new <em>HealthGoal</em> with the given minimum health amount.
   *
   * @param minimumHealth the minimum amount of health a player needs to have to fulfill the goal
   */
  public HealthGoal(int minimumHealth) {
    this.minimumHealth = minimumHealth;
  }

  /**
   * Checks if the player has at least the minimum amount of health to fulfill the goal.
   *
   * @param player the player to check for the goal fulfillment
   * @return {@code true} if the player has at least the required amount of health {@code false}
   * otherwise
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getHealth() >= minimumHealth;
  }
}

package edu.ntnu.idatt2001.paths.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ntnu.idatt2001.paths.game.Player;

/**
 * The HealthAction class represents an action that changes the health value of a player. The amount
 * of health to change is specified in the constructor.
 *
 * @see Action
 */
public class HealthAction implements Action {
  @JsonProperty private final int healthChange;

  @JsonCreator
  public HealthAction(@JsonProperty int health) {
    this.healthChange = health;
  }

  /**
   * Executes this action on the given player by adding the healthChange value to the player's
   * health value.
   *
   * @param player the player object to execute the action on
   */
  @Override
  public void execute(Player player) {
    player.addHealth(healthChange);
  }
}

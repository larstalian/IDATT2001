package edu.ntnu.idatt2001.paths.model.goals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ntnu.idatt2001.paths.model.game.Player;

import java.util.HashSet;
import java.util.List;

/**
 * The <em>InventoryGoal</em> class implements the <em>Goal</em> interface and represents a goal of
 * the game based on the inventory of items a player must have.
 *
 * <p>It specifies a list of mandatory items that a player must have in their inventory to fulfill
 * the goal by calling the {@link #isFulfilled} method and passing in a Player object.
 *
 * <p>The <em>InventoryGoal</em> class is a part of the <em>Paths</em> game.
 *
 * @see Goal
 * @see Player
 */
public class InventoryGoal implements Goal {

  @JsonProperty private final List<String> mandatoryItems;

  /**
   * Constructs a new `InventoryGoal` object with the specified list of mandatory items.
   *
   * @param mandatoryItems the list of mandatory items that a player must collect to fulfill the
   *     goal
   */
  @JsonCreator
  public InventoryGoal(@JsonProperty List<String> mandatoryItems) {
    this.mandatoryItems = mandatoryItems;
  }

  /**
   * Checks if the specified `Player` object has collected all the mandatory items in their
   * inventory.
   *
   * @param player the player whose inventory is to be checked
   * @return {@code true}` if the player has collected all the mandatory items, {@code false}
   *     otherwise
   */
  @Override
  public boolean isFulfilled(Player player) {
    return new HashSet<>(player.getInventory()).containsAll(mandatoryItems);
  }
}

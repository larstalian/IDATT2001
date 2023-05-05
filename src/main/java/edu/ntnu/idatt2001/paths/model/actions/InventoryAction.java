package edu.ntnu.idatt2001.paths.model.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ntnu.idatt2001.paths.model.game.Player;
import lombok.EqualsAndHashCode;

/**
 * The InventoryAction class represents an action that adds an item to the player's inventory. The
 * item to add is specified in the constructor.
 *
 * @see Action
 */
@EqualsAndHashCode
public class InventoryAction implements Action {

  @JsonProperty private final String item;

  @JsonCreator
  public InventoryAction(@JsonProperty String item) {
    this.item = item;
  }

  /**
   * Executes this action on the given player by adding the item to the player's inventory.
   *
   * @param player the player object to execute the action on
   */
  @Override
  public void execute(Player player) {
    player.addToInventory(item);
  }

  @Override
  public String toString() {
    return "I:" + item;
  }
}

package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.game.Player;

/**
 * The InventoryAction class represents an action that adds an item to the player's inventory.
 * The item to add is specified in the constructor.
 *
 * @see Action
 */
public class InventoryAction implements Action {
    private final String item;

    public InventoryAction(String item) {
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
}

package edu.ntnu.idatt2001.paths.model.actions;

import edu.ntnu.idatt2001.paths.model.game.Player;

/**
 * The Action interface represents an action that can be performed in the game. Actions can be
 * executed on a given player object.
 *
 * @see Player
 */
public interface Action {

  /**
   * Executes this Action on the given player.
   *
   * @param player the player object that the action should be executed on
   */
  void execute(Player player);

  @Override
  String toString();
}

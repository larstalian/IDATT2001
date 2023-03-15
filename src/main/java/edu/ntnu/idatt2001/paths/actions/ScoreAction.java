package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.game.Player;

/**
 * The ScoreAction class represents an action that changes the score value of a player. The amount
 * of score to change is specified in the constructor.
 *
 * @see Action
 */
public class ScoreAction implements Action {

  private final int scoreChange;

  public ScoreAction(int points) {
    this.scoreChange = points;
  }

  /**
   * Executes this action on the given player by adding the scoreChange value to the player's score
   * value.
   *
   * @param player the player object to execute the action on
   */
  @Override
  public void execute(Player player) {
    player.addScore(player.getScore() + scoreChange);
  }
}

package edu.ntnu.idatt2001.paths.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ntnu.idatt2001.paths.game.Player;

/**
 * The ScoreAction class represents an action that changes the score value of a player. The amount
 * of score to change is specified in the constructor.
 *
 * @see Action
 */
public class ScoreAction implements Action {

  @JsonProperty private final int scoreChange;

  @JsonCreator
  public ScoreAction(@JsonProperty int points) {
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

  @Override
  public String toString() {
    return "S:" + scoreChange;
  }
}

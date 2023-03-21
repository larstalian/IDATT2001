package edu.ntnu.idatt2001.paths.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ntnu.idatt2001.paths.game.Player;

/**
 * The GoldAction class represents an action that changes the gold value of a player. The amount of
 * gold to change is specified in the constructor.
 *
 * @see Action
 */
public class GoldAction implements Action {

  @JsonProperty private final int goldChange;

  @JsonCreator
  public GoldAction(@JsonProperty int gold) {
    this.goldChange = gold;
  }

  /**
   * Executes this action on the given player by adding the goldChange value to the player's gold
   * value.
   *
   * @param player the player object to execute the action on
   */
  @Override
  public void execute(Player player) {
    player.addGold(player.getGold() + goldChange);
  }

  @Override
  public String toString() {
    return goldChange + "gold";
  }
}

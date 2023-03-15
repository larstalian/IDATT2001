package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.game.Player;

public interface Goal {

  boolean isFulfilled(Player player);
}

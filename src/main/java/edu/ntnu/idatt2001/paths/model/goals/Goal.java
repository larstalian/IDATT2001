package edu.ntnu.idatt2001.paths.model.goals;

import edu.ntnu.idatt2001.paths.model.game.Player;

public interface Goal {

  boolean isFulfilled(Player player);
}

package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.game.Player;

public class GoldAction implements Action {
    private final int goldChange;

    public GoldAction(int gold) {
        this.goldChange = gold;
    }

    @Override
    public void execute(Player player) {
        player.addGold(player.getGold() + goldChange);
    }
}

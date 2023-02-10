package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;

public class HealthAction implements Action {
    private final int healthChange;

    public HealthAction(int health) {
        this.healthChange = health;
    }

    @Override
    public void execute(Player player) {
        player.addHealth(healthChange);
    }
}

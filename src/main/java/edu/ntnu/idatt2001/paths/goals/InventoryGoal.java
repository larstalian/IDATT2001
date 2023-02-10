package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.Player;

import java.util.HashSet;
import java.util.List;

public class InventoryGoal implements Goal {
    private final List<String> mandatoryItems;

    public InventoryGoal(List<String> mandatoryItems) {
        this.mandatoryItems = mandatoryItems;
    }

    @Override
    public boolean isFulfilled(Player player) {
        return new HashSet<>(player.getInventory()).containsAll(mandatoryItems);
    }
}

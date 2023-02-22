package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class InventoryGoalTest {

    private Player player;
    private InventoryGoal inventoryGoal;

    @BeforeEach
    void setUp() {
        player = new Player("PlayerName", 100, 0, 0);
        inventoryGoal = new InventoryGoal(List.of("Sword", "Shield"));
    }

    @Test
    void isFulfilled() {
        player.addToInventory("Sword");
        player.addToInventory("Shield");
        assertThat(inventoryGoal.isFulfilled(player), is(true));

    }

    @Test
    void isFulfilled_ShouldReturnFalseIfNotFulfilled() {
        player.addToInventory("Sword");
        assertThat(inventoryGoal.isFulfilled(player), is(false));
    }
}
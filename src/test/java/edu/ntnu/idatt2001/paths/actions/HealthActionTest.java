package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class HealthActionTest {
    private Player player;
    private HealthAction healthAction;

    @BeforeEach
    void setUp() {
        player = new Player("PlayerName", 100, 0, 0);
        healthAction = new HealthAction(-50);
    }

    @Test
    void execute() {
        healthAction.execute(player);
        assertThat(player.getHealth(), is(50));
    }
}
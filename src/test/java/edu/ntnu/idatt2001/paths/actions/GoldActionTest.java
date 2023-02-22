package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class GoldActionTest {
    private Player player;
    private GoldAction goldAction;

    @BeforeEach
    void setUp() {
        player = new Player("PlayerName", 100, 0, 0);
        goldAction = new GoldAction(100);
    }

    @Test
    void execute() {
        goldAction.execute(player);
        assertThat(player.getGold(), is(100));
    }
}
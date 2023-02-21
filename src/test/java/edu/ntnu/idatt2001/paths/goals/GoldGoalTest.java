package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class GoldGoalTest {
    Player player;
    Goal goldGoal;

    @BeforeEach
    void setUp() {
        player = new Player("PlayerName", 100, 0, 0);
        goldGoal = new GoldGoal(100);
    }

    @Test
    void testIsFulfilled() {
        player.addGold(100);
        assertThat(goldGoal.isFulfilled(player), is(true));
    }

    @Test
    void testIsFulfilled_ShouldReturnFalseIfNotFulfilled() {
        player.addGold(50);
        assertThat(goldGoal.isFulfilled(player), is(false));
    }
}
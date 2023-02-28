package edu.ntnu.idatt2001.paths.goals;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import edu.ntnu.idatt2001.paths.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HealthGoalTest {

  private Player player;
  private HealthGoal healthGoal;

  @BeforeEach
  void setUp() {
    player = new Player("PlayerName", 80, 0, 0);
    healthGoal = new HealthGoal(100);
  }

  @Test
  void isFulfilled() {
    player.addHealth(20);
    assertThat(healthGoal.isFulfilled(player), is(true));
  }

  @Test
  void isFulfilled_ShouldReturnFalseIfNotFulfilled() {
    player.addHealth(-20);
    assertThat(healthGoal.isFulfilled(player), is(false));
  }
}
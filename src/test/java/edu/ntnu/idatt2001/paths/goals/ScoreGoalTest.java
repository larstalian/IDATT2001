package edu.ntnu.idatt2001.paths.goals;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import edu.ntnu.idatt2001.paths.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreGoalTest {

  private Player player;
  private ScoreGoal scoreGoal;

  @BeforeEach
  void setUp() {
    player = new Player.Builder("PlayerName").build();
    scoreGoal = new ScoreGoal(100);
  }

  @Test
  void isFulfilled() {
    player.addScore(100);
    assertThat(scoreGoal.isFulfilled(player), is(true));
  }

  @Test
  void isFulfilled_ShouldReturnFalseIfNotFulfilled() {
    player.addScore(50);
    assertThat(scoreGoal.isFulfilled(player), is(false));
  }
}
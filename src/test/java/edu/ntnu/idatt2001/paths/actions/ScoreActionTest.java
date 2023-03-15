package edu.ntnu.idatt2001.paths.actions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import edu.ntnu.idatt2001.paths.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreActionTest {

  private Player player;
  private ScoreAction scoreAction;

  @BeforeEach
  void setUp() {
    player = new Player.Builder("PlayerName").build();
    scoreAction = new ScoreAction(100);
  }

  @Test
  void execute() {
    scoreAction.execute(player);
    assertThat(player.getScore(), is(100));
  }
}
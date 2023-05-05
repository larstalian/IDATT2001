package edu.ntnu.idatt2001.paths.model.actions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import edu.ntnu.idatt2001.paths.model.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HealthActionTest {

  private Player player;
  private HealthAction healthAction;

  @BeforeEach
  void setUp() {
    player = new Player.Builder("PlayerName").health(100).build();
    healthAction = new HealthAction(-50);
  }

  @Test
  void execute() {
    healthAction.execute(player);
    assertThat(player.getHealth(), is(50));
  }
}

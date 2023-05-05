package edu.ntnu.idatt2001.paths.model.actions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;

import edu.ntnu.idatt2001.paths.model.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryActionTest {

  private Player player;
  private InventoryAction inventoryAction;

  @BeforeEach
  void setUp() {
    player = new Player.Builder("PlayerName").build();
    inventoryAction = new InventoryAction("Sword");
  }

  @Test
  void testExecute() {
    inventoryAction.execute(player);
    assertThat(player.getInventory(), everyItem(is("Sword")));
  }
}

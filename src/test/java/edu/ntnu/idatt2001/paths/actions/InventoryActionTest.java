package edu.ntnu.idatt2001.paths.actions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;

import edu.ntnu.idatt2001.paths.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryActionTest {

  private Player player;
  private InventoryAction inventoryAction;

  @BeforeEach
  void setUp() {
    player = new Player("PlayerName", 100, 0, 0);
    inventoryAction = new InventoryAction("Sword");
  }

  @Test
  void testExecute() {
    inventoryAction.execute(player);
    assertThat(player.getInventory(), everyItem(is("Sword")));
  }
}
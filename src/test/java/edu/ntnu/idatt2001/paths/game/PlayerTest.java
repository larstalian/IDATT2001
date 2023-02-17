package edu.ntnu.idatt2001.paths.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerTest {
    private Player player;
    private List<String> inventory;

    @BeforeEach
    void setUp() {
        player = new Player("PlayerName", 100, 0, 0);
        player.addToInventory("Armor");
        player.addToInventory("Sword");
        inventory = new ArrayList<>();
        inventory.add("Armor");
        inventory.add("Sword");
    }

    @Test
    void testGetName() {
        assertThat("PlayerName", is(player.getName()));
    }

    @Test
    void testGetHealth() {
        assertThat(100, is(player.getHealth()));
    }

    @Test
    void testGetScore() {
        assertThat(0, is(player.getScore()));
    }

    @Test
    void testGetGold() {
        assertThat(0, is(player.getGold()));
    }

    @Test
    void testGetInventory() {
        assertThat(inventory, is(player.getInventory()));
    }

    @Test
    void testAddHealth() {
        player.addHealth(50);
        assertThat(150, is(player.getHealth()));
    }

    @Test
    void testAddScore() {
        player.addScore(50);
        assertThat(50, is(player.getScore()));
    }

    @Test
    void testAddGold() {
        player.addGold(50);
        assertThat(50, is(player.getGold()));
    }

    @Test
    void testAddToInventory() {
        inventory.add("Shield");
        player.addToInventory("Shield");
        assertThat(inventory, is(player.getInventory()));
    }

    @Test
    void testToString() {
        String expectedOutput = "PlayerName      100 HP     0 PTS     0 GOLD  INV: Armor, Sword";
        String actualOutput = player.toString();
        assertThat(expectedOutput, is(actualOutput));
    }

    @Test
    void testAddHealth_ShouldThrowExceptionWhenHealthIsUnder0() {
        assertThrows(IllegalArgumentException.class, () -> player.addHealth(-101));
    }
}
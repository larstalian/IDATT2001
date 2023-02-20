package edu.ntnu.idatt2001.paths.game;

import java.util.ArrayList;
import java.util.List;

import static edu.ntnu.idatt2001.paths.game.Player.PlayerConstants.*;

/**
 * The Player class represents a player in a game. A player has a name, health, score, gold, and an inventory
 * of items they have collected. The Player class provides methods for accessing and modifying these attributes.
 *
 * <p>The player attributes must fall within certain ranges or an IllegalArgumentException will be thrown.
 * The player's inventory is empty by default and can be populated using the {@link #addToInventory} method.
 * The players health, score and gold can be modified using their corresponding add methods
 * and retrieved using get methods.</p>
 *
 * <p>The attributes {@link Player#name} and {@link Player#inventory} are immutable and cannot be modified.
 *
 * @see Game
 */
public class Player {

    private final String name;
    private final List<String> inventory;
    private int health;
    private int score;
    private int gold;

    /**
     * Creates a new Player with the given name, health, score, and gold.
     *
     * @param name   the name of the player
     * @param health the starting health of the player, must be between 0 and 1000 (inclusive)
     * @param score  the starting score of the player, must be between 0 and 1000 (inclusive)
     * @param gold   the starting amount of gold the player has, must be between 0 and 100000 (inclusive)
     * @throws IllegalArgumentException if any of the parameter values are invalid
     */
    public Player(String name, int health, int score, int gold) {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Name cannot be less than " + MIN_NAME_LENGTH + " " +
                    "or greater than " + MAX_NAME_LENGTH + " characters");
        }
        this.name = name;
        addHealth(health);
        addScore(score);
        addGold(gold);
        inventory = new ArrayList<>();
    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current health of the player.
     *
     * @return the current health of the player
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the current score of the player.
     *
     * @return the current score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the current amount of gold the player has.
     *
     * @return the current amount of gold the player has
     */
    public int getGold() {
        return gold;
    }

    /**
     * Returns the list of items in the player's inventory.
     *
     * @return the list of items in the player's inventory
     */
    public List<String> getInventory() {
        return inventory;
    }

    /**
     * Increases the health of the player by the given amount.
     * The health cannot be less than 0 or greater than 1000.
     *
     * @param health the amount to increase the health by
     * @throws IllegalArgumentException if the resulting health is less than 0 or greater than 1000
     */
    public void addHealth(int health) {
        int totalHealth = this.health + health;
        if (totalHealth < 0 || totalHealth > MAX_HEALTH) {
            throw new IllegalArgumentException("Health cannot be less than 0 or greater than " + MAX_HEALTH);
        }
        this.health += health;
    }

    /**
     * Increases the current score of the player by the given amount.
     *
     * @param score the amount to increase the score by
     * @throws IllegalArgumentException if the resulting score is less than 0 or greater than 1000
     */
    public void addScore(int score) {
        int totalScore = this.score + score;
        if (totalScore < 0 || totalScore >= MAX_SCORE) {
            throw new IllegalArgumentException("The total score cannot be less than 0 or greater than" + MAX_SCORE);
        }
        this.score += score;
    }

    /**
     * Increases the current amount of gold the player has by the given amount.
     *
     * @param gold the amount to increase the gold by
     * @throws IllegalArgumentException if the resulting gold is less than 0 or greater than 100000
     */
    public void addGold(int gold) {
        int totalGold = this.gold + gold;
        if (totalGold < 0 || totalGold > MAX_GOLD) {
            throw new IllegalArgumentException("Gold cannot be less than 0 or greater than" + MAX_GOLD);
        }
        this.gold += gold;
    }

    /**
     * Adds a new item to the inventory.
     *
     * @param item the name of the item to be added to the inventory
     * @throws IllegalArgumentException if the length of the item is less than 2 or greater than 15 characters
     */
    public void addToInventory(final String item) {
        if (item.length() < 2 || item.length() > 15) {
            throw new IllegalArgumentException("Item cannot be less than 2 or greater than 15 characters");
        }
        inventory.add(item);
    }

    /**
     * Returns a string representation of the player, including their name, health, score, gold, and inventory.
     *
     * @return a string representation of the player
     */
    @Override
    public String toString() {
        return String.format("%-15s %3d HP  %4d PTS  %4d GOLD  INV: %s",
                name, health, score, gold, String.join(", ", inventory));
    }


    public static class PlayerConstants {
        static final int MAX_NAME_LENGTH = 15;
        static final int MIN_ITEM_LENGTH = 2;
        static final int MAX_ITEM_LENGTH = 15;
        static final int MAX_GOLD = 100000;
        static final int MAX_HEALTH = 1000;
        static final int MAX_SCORE = 1000;
        static final int MIN_NAME_LENGTH = 2;
    }
}

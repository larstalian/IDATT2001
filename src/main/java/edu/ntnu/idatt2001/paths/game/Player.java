package edu.ntnu.idatt2001.paths.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static edu.ntnu.idatt2001.paths.game.Player.PlayerConstants.*;

/**
 * The Player class represents a player in a game. A player has a name, health, score, gold, and an
 * inventory of items they have collected. The Player class provides methods for accessing and
 * modifying these attributes.
 *
 * <p>The player attributes must fall within certain ranges or an IllegalArgumentException will be
 * thrown. The player's inventory is empty by default and can be populated using the {@link
 * #addToInventory} method. The players' health, score and gold can be modified using their
 * corresponding add-methods and retrieved using get methods.
 *
 * <p>The attributes {@link Player#name} and {@link Player#inventory} are immutable and cannot be
 * modified.
 *
 * <p>The field variables constraints are defined in the {@link PlayerConstants} inner class.
 *
 * <p>To create a Player object, use the {@link Builder} class.
 *
 * @see Game
 * @see PlayerConstants
 * @see Builder
 */
public class Player {
  private final String name;
  private final List<String> inventory;
  private int health;
  private int score;
  private int gold;

  private Player(Builder builder) {
    this.name = builder.name;
    this.inventory = builder.inventory;
    this.health = builder.health;
    this.score = builder.score;
    this.gold = builder.gold;
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
   * Increases the health of the player by the given amount. The health cannot be less than 0 or
   * greater than 1000.
   *
   * @param health the amount to increase the health by
   * @throws IllegalArgumentException if the resulting health is less than 0 or greater than 1000
   */
  public void addHealth(int health) {
    int totalHealth = this.health + health;
    checkHealthRange(totalHealth);
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
    checkScoreRange(totalScore);
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
    checkGoldRange(totalGold);
    this.gold += gold;
  }

  /**
   * Adds a new item to the inventory.
   *
   * @param item the name of the item to be added to the inventory
   * @throws IllegalArgumentException if the length of the item is less than 2 or greater than 15
   *     characters
   */
  public void addToInventory(final String item) {
    checkItemLength(item);
    checkInventorySize(getInventory());
    inventory.add(item);
  }

  /**
   * Checks if the provided health value is within the valid range.
   *
   * @param health the health value to check
   * @throws IllegalArgumentException if the health value is less than 0 or greater than MAX_HEALTH
   */
  private void checkHealthRange(int health) {
    if (health < 0 || health > MAX_HEALTH) {
      throw new IllegalArgumentException(
          "Health cannot be less than 0 or greater than " + MAX_HEALTH);
    }
  }

  /**
   * Checks if the provided score value is within the valid range.
   *
   * @param score the score value to check
   * @throws IllegalArgumentException if the score value is less than 0 or greater than or equal to
   *     MAX_SCORE
   */
  private void checkScoreRange(int score) {
    if (score < 0 || score >= MAX_SCORE) {
      throw new IllegalArgumentException(
          "The total score cannot be less than 0 or greater than " + MAX_SCORE);
    }
  }

  /**
   * Checks if the provided gold value is within the valid range.
   *
   * @param gold the gold value to check
   * @throws IllegalArgumentException if the gold value is less than 0 or greater than MAX_GOLD
   */
  private void checkGoldRange(int gold) {
    if (gold < 0 || gold > MAX_GOLD) {
      throw new IllegalArgumentException("Gold cannot be less than 0 or greater than " + MAX_GOLD);
    }
  }

  /**
   * Checks if the provided item's length is within the valid range.
   *
   * @param item the item to check
   * @throws IllegalArgumentException if the length of the item is less than MIN_ITEM_LENGTH or
   *     greater than MAX_ITEM_LENGTH
   */
  private void checkItemLength(String item) {
    if (item.length() < MIN_ITEM_LENGTH || item.length() > MAX_ITEM_LENGTH) {
      throw new IllegalArgumentException(
          "Item cannot be less than 2 or greater than 15 characters");
    }
  }

  /**
   * Checks if the provided inventory's size is within the valid range.
   *
   * @param inventory the inventory to check
   * @throws IllegalArgumentException if the size of the inventory is greater than
   *     MAX_INVENTORY_SIZE
   */
  private void checkInventorySize(List<String> inventory) {
    if (inventory.size() >= MAX_INVENTORY_SIZE) {
      throw new IllegalArgumentException(
          "Inventory cannot be greater than " + MAX_INVENTORY_SIZE + " items");
    }
  }

  /**
   * Returns a string representation of the player, including their name, health, score, gold, and
   * inventory.
   *
   * @return a string representation of the player
   */
  @Override
  public String toString() {
    return String.format(
        "%-15s %3d HP  %4d PTS  %4d GOLD  INV: %s",
        name, health, score, gold, String.join(", ", inventory));
  }

  /**
   * The Builder class is a utility class used to create a Player object following the Builder
   * design pattern. It allows the creation of Player objects with optional fields while ensuring
   * readability and maintainability of the code.
   *
   * <p>The Builder class offers methods to set the health, score, gold, and inventory of a Player
   * object. Each method returns the current Builder instance, allowing method chaining for a more
   * readable code. Example usage:
   *
   * <pre>{@code
   * Player player = new Player.Builder("PlayerName")
   *                  .health(100)
   *                  .score(50)
   *                  .gold(250)
   *                  .inventory("Sword", "Shield")
   *                  .build();
   * }</pre>
   *
   * @see Player
   */
  public static class Builder {
    private final String name;
    private final List<String> inventory;
    private int health;
    private int score;
    private int gold;

    public Builder(String name) {
      this.name = name;
      inventory = new ArrayList<>();
    }

    /**
     * Sets the health of the Player to be built.
     *
     * @param health the health value for the Player
     * @return the current Builder instance for method chaining
     */
    public Builder health(int health) {
      this.health = health;
      return this;
    }

    /**
     * Sets the score of the Player to be built.
     *
     * @param score the score value for the Player
     * @return the current Builder instance for method chaining
     */
    public Builder score(int score) {
      this.score = score;
      return this;
    }

    /**
     * Sets the gold of the Player to be built.
     *
     * @param gold the gold value for the Player
     * @return the current Builder instance for method chaining
     */
    public Builder gold(int gold) {
      this.gold = gold;
      return this;
    }

    /**
     * Sets the inventory of the Player to be built.
     *
     * @param items an array of Strings representing the items to be added to the Player's inventory
     * @return the current Builder instance for method chaining
     */
    public Builder inventory(String... items) {
      inventory.addAll(Arrays.asList(items));
      return this;
    }

    /**
     * Builds and returns a new Player object with the specified attributes.
     *
     * @return a new Player object with the attributes set using the Builder methods
     */
    public Player build() {
      Player player = new Player(this);
      validateObject(player);
      return player;
    }

    /**
     * Validates the Player object to be built by checking the validity of its fields.
     *
     * @param player the Player object to be validated
     * @throws IllegalArgumentException if any of the fields are outside the valid range or have
     *     invalid values
     */
    private void validateObject(Player player) {
      checkNameLength(player.name);
      player.checkGoldRange(player.gold);
      player.checkHealthRange(player.health);
      player.checkScoreRange(player.score);
      player.checkInventorySize(player.inventory);
    }
    /**
     * Checks if the provided name's length is within the valid range.
     *
     * @param name the name to check
     * @throws IllegalArgumentException if the length of the name is less than MIN_NAME_LENGTH or
     *     greater than MAX_NAME_LENGTH
     */
    private void checkNameLength(String name) {
      if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
        throw new IllegalArgumentException(
            "Name cannot be less than "
                + MIN_NAME_LENGTH
                + " or greater than "
                + MAX_NAME_LENGTH
                + " characters");
      }
    }
  }

  /**
   * The PlayerConstants class contains constants used by the Player class to set the valid range of
   * its fields. The constants are declared as static and final, and can therefore not be modified.
   *
   * <p>Use the constants to check that parameter values are within the valid range when creating or
   * modifying Player objects.
   *
   * @see Player
   */
  static class PlayerConstants {
    public static final int MAX_INVENTORY_SIZE = 10;
    static final int MAX_NAME_LENGTH = 15;
    static final int MIN_ITEM_LENGTH = 2;
    static final int MAX_ITEM_LENGTH = 15;
    static final int MAX_GOLD = 100000;
    static final int MAX_HEALTH = 1000;
    static final int MAX_SCORE = 1000;
    static final int MIN_NAME_LENGTH = 2;
  }
}

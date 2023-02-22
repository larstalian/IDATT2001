package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.game.Player;

/**
 * <p>The <em>GoldGoal</em> class implements the <em>Goal</em> interface and represents a {@link Goal} of the game
 * based on the minimum amount of gold a {@link Player} must collect.</p>
 *
 * <p>The <em>GoldGoal</em> class is a part of the <em>Paths</em> game.
 * It specifies the minimum amount of gold a player needs to collect to fulfill the goal
 * by calling the {@link #isFulfilled} method and passing in a Player object.</p>
 *
 * @see Goal
 * @see Player
 */
public class GoldGoal implements Goal {
    private final int minimumGold;

    /**
     * Creates a new <em>GoldGoal</em> with the given minimum gold amount.
     *
     * @param minimumGold the minimum amount of gold a player needs to collect to fulfill the goal
     */
    public GoldGoal(int minimumGold) {
        this.minimumGold = minimumGold;
    }

    /**
     * Checks if the player has collected at least the minimum amount of gold to fulfill the goal.
     *
     * @param player the player to check for the goal fulfillment
     * @return {@code true} if the player has collected at least the minimum amount of gold, {@code false} otherwise
     */
    @Override
    public boolean isFulfilled(Player player) {
        return player.getGold() >= minimumGold;
    }
}

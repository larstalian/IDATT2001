package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.game.Player;


/**
 * The ScoreGoal class represents a goal in the game where the player needs to achieve a certain score.
 * The goal is considered fulfilled if the player's score is greater than or equal to a minimum number of points.
 *
 * <p>The minimum number of points required to fulfill the goal is specified in the constructor.
 * The goal can be checked for fulfillment by calling the {@link #isFulfilled}
 * method and passing in a Player object.</p>
 *
 * @see Goal
 * @see Player
 */
public class ScoreGoal implements Goal {
    private final int minimumScore;

    /**
     * Creates a new ScoreGoal with the given minimum number of points.
     *
     * @param minimumScore the minimum number of points required to fulfill the goal
     */
    public ScoreGoal(int minimumScore) {
        this.minimumScore = minimumScore;
    }

    /**
     * Checks if the given player has fulfilled the goal.
     *
     * @param player the player to check for goal fulfillment
     * @return {@code true} if the player's score is greater than or equal to the minimum score, {@code false} otherwise
     */
    @Override
    public boolean isFulfilled(Player player) {
        return player.getScore() >= minimumScore;
    }
}

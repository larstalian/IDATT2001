package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.Player;

public class ScoreGoal implements Goal {
    private final int minimumPoints;

    public ScoreGoal(int minimumScore) {
        this.minimumPoints = minimumScore;
    }

    @Override
    public boolean isFulfilled(Player player) {
        return player.getScore() >= minimumPoints;
    }
}

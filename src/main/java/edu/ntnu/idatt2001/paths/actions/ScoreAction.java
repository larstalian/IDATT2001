package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;

public class ScoreAction implements Action {
    private final int scoreChange;

    public ScoreAction(int points) {
        this.scoreChange = points;
    }

    @Override
    public void execute(Player player) {
        player.addScore(player.getScore() + scoreChange);
    }
}

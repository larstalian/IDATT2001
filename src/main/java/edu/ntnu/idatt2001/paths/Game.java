package edu.ntnu.idatt2001.paths;

import edu.ntnu.idatt2001.paths.goals.Goal;

import java.util.List;
import java.util.Objects;

public class Game {
    Player player;
    Story story;
    List<Goal> goals;

    public Game(Player player, Story story, List<Goal> goals) {
        this.player = player;
        this.story = story;
        this.goals = goals;
    }

    public Player getPlayer() {
        return player;
    }

    public Story getStory() {
        return story;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public Passage begin() {
        return story.getOpeningPassage();
    }

    // OBS NULLABLE RETURN
    public Passage go(Link link) {
        Passage passage = story.getPassage(link);
        Objects.requireNonNull(passage, "No passage with link " + link);
        return passage;
    }
}

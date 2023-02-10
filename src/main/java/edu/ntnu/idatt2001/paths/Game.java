package edu.ntnu.idatt2001.paths;

import java.util.List;

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
        return story.getPassages().stream()
                .flatMap(p -> story.getPassages().stream())
                .findFirst()
                .orElse(null);
    }
}

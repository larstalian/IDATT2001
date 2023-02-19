package edu.ntnu.idatt2001.paths.game;

import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;

import java.util.List;
import java.util.Objects;

public class Game {
    private final Player player;
    private final Story story;
    private final List<Goal> goals;

    public Game(Player player, Story story, List<Goal> goals) {
        this.player = Objects.requireNonNull(player, "Player cannot be null");
        this.story = Objects.requireNonNull(story, "Story cannot be null");
        this.goals = Objects.requireNonNull(goals, "Goals cannot be null");
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

    public Passage go(Link link) {
        Passage passage = story.getPassage(link);
        Objects.requireNonNull(passage, "No passage with link " + link);
        return passage;
    }
}

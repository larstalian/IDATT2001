package edu.ntnu.idatt2001.paths.game;

import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;

import java.util.List;
import java.util.Objects;

/**
 * The Game class represents a game. It contains a player, a story, and a list of goals.
 * The Game class provides methods for accessing these attributes.
 *
 * <p>The game's player, story, and goals cannot be null or an IllegalArgumentException will be thrown.
 * The game's story can be progressed through by calling the {@link #begin} method and then the {@link #go} method
 * to navigate to different passages using the associated link</p>
 *
 * <p>The field variables is immutable and cannot be modified.</p>
 *
 * @see Player
 * @see Story
 * @see Goal
 */
public class Game {
    private final Player player;
    private final Story story;
    private final List<Goal> goals;


    /**
     * Creates a new game with the given player, story, and goals.
     *
     * @param player the player of the game, cannot be null
     * @param story  the story of the game, cannot be null
     * @param goals  the goals of the game, cannot be null
     * @throws IllegalArgumentException if any of the parameter values are invalid
     */
    public Game(Player player, Story story, List<Goal> goals) {
        this.player = Objects.requireNonNull(player, "Player cannot be null");
        this.story = Objects.requireNonNull(story, "Story cannot be null");
        this.goals = Objects.requireNonNull(goals, "Goals cannot be null");
    }

    /**
     * Returns the player of the game.
     *
     * @return the player of the game
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the story of the game.
     *
     * @return the story of the game
     */
    public Story getStory() {
        return story;
    }

    /**
     * Returns the goals of the game.
     *
     * @return the goals of the game
     */
    public List<Goal> getGoals() {
        return goals;
    }

    /**
     * Returns the opening passage of the story.
     *
     * @return the opening passage of the story
     */
    public Passage begin() {
        return story.getOpeningPassage();
    }

    /**
     * Navigates to the passage corresponding to the given link and returns it.
     *
     * @param link the link of the passage to navigate to
     * @return the passage corresponding to the given link
     * @throws NullPointerException if the passage with the given link does not exist
     */
    public Passage go(Link link) {
        Passage passage = story.getPassage(link);
        Objects.requireNonNull(passage, "No passage with link " + link);
        return passage;
    }
}

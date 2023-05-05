package edu.ntnu.idatt2001.paths.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ntnu.idatt2001.paths.model.goals.Goal;
import edu.ntnu.idatt2001.paths.model.story.Link;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import edu.ntnu.idatt2001.paths.model.story.Story;
import java.util.List;
import java.util.Objects;
import lombok.EqualsAndHashCode;

/**
 * The Game class represents a game. It contains a player, a story, and a list of goals. The Game
 * class provides methods for accessing these attributes.
 *
 * <p>The game's player, story, and goals cannot be null or an IllegalArgumentException will be
 * thrown. The game's story can be progressed through by calling the {@link #begin} method and then
 * the {@link #go} method to navigate to different passages using the associated link
 *
 * <p>The field variables is immutable and cannot be modified.
 *
 * @see Player
 * @see Story
 * @see Goal
 */
@EqualsAndHashCode(of = {"player", "story"})
public class Game {

  @JsonProperty private final Player player;
  @JsonProperty private final Story story;
  @JsonProperty private final List<Goal> goals;

  /**
   * Creates a new game with the given player, story, and goals.
   *
   * @param player the player of the game, cannot be null
   * @param story the story of the game, cannot be null
   * @param goals the goals of the game, cannot be null
   * @throws IllegalArgumentException if any of the parameter values are invalid
   */
  @JsonCreator
  public Game(
      @JsonProperty("player") Player player,
      @JsonProperty("story") Story story,
      @JsonProperty("goals") List<Goal> goals) {
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

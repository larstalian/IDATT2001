package edu.ntnu.idatt2001.paths.game;

import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.goals.HealthGoal;
import edu.ntnu.idatt2001.paths.goals.InventoryGoal;
import edu.ntnu.idatt2001.paths.goals.ScoreGoal;
import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameTest {

  private List<Goal> goals;
  private Game game;
  private Story story;
  private Passage openingPassage;
  private Player player;

  @BeforeEach
  void setUp() {
    player = new Player("PlayerName", 100, 0, 0);
    openingPassage = new Passage("The opening passage", "A test troll");
    story = new Story("Test Story", openingPassage);

    Goal scoreGoal = new ScoreGoal(100);
    Goal healthGoal = new HealthGoal(50);
    Goal inventoryGoal = new InventoryGoal(List.of("Armor", "Sword"));

    goals = List.of(scoreGoal, healthGoal, inventoryGoal);
    game = new Game(player, story, goals);
  }

  @Test
  void testGetPlayer() {
    assertThat(game.getPlayer(), is(player));
  }

  @Test
  void testGetStory() {
    assertThat(game.getStory(), is(story));
  }

  @Test
  void testGetGoals() {
    assertThat(game.getGoals(), containsInAnyOrder(goals.toArray(new Goal[0])));
  }

  @Test
  void testBegin() {
    assertThat(game.begin(), is(openingPassage));
  }

  @Test
  void testGo_GoToNewPassage() {
    Passage nextPassage = new Passage("Killed by Troll", "Game finished");
    story.addPassage(nextPassage);

    assertThat(game.go(new Link(nextPassage.getTitle(), nextPassage.getTitle())), is(nextPassage));
  }

  @Test
  void testConstructor_NullPlayer_ThrowsNullPointerException() {
    assertThrows(NullPointerException.class, () -> new Game(null, story, goals));
  }

  @Test
  void testConstructor_NullStory_ThrowsNullPointerException() {
    assertThrows(NullPointerException.class, () -> new Game(player, null, goals));
  }

  @Test
  void testConstructor_NullGoals_ThrowsNullPointerException() {
    assertThrows(NullPointerException.class, () -> new Game(player, story, null));
  }

  @Test
  void testGo_LinkToNonexistentPassage_ThrowsNullPointerException() {
    assertThrows(NullPointerException.class, () -> game.go(new Link("Nonexistent Passage", "Nonexistent Passage")));
  }
}

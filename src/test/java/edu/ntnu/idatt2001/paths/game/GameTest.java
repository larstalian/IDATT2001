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
import static org.hamcrest.Matchers.is;

class GameTest {
    List<Goal> goals;
    private Game game;
    private Story story;
    private Passage openingPassage;

    @BeforeEach
    void setUp() {
        Player player = new Player("PlayerName", 100, 0, 0);
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
        assertThat("PlayerName", is(game.getPlayer().getName()));
    }

    @Test
    void testGetStory() {
        assertThat(story, is(game.getStory()));
    }

    @Test
    void testGetGoals() {
        assertThat(goals, is(game.getGoals()));
    }

    @Test
    void testBegin() {
        assertThat(openingPassage, is(game.begin()));
    }

    @Test
    void testGo_GoToNewPassage() {
        Passage nextPassage = new Passage("Killed by Troll", "Game finished");
        story.addPassage(nextPassage);
        assertThat(game.go(new Link(nextPassage.getTitle(), nextPassage.getTitle())), is(nextPassage));
    }
}
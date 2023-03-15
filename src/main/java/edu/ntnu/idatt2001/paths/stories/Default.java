package edu.ntnu.idatt2001.paths.stories;

import edu.ntnu.idatt2001.paths.actions.HealthAction;
import edu.ntnu.idatt2001.paths.game.Player;
import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Passage;
import edu.ntnu.idatt2001.paths.story.Story;

public class Default {
  public static void main(String[] args) {
      Player player = new Player.Builder("Player 1").build();
      Story story = new Story("The Story",new Passage("Beginning","You wake up in a forest"));
      story.getOpeningPassage().addLink(new Link("Go to the middle","Middle"));
      story.getOpeningPassage().getLinks().get(0).addAction(new HealthAction(-10));
      story.addPassage(new Passage("Middle","You are in the middle of the forest"));
  }
}

package edu.ntnu.idatt2001.paths.view;

import edu.ntnu.idatt2001.paths.model.story.Passage;
import java.awt.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Builder;

public class Game implements Builder<Region> {

  private Passage currentPassage;
  private edu.ntnu.idatt2001.paths.model.game.Game currentGame;
  private StringProperty contentBar;

  @Override
  public Region build() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("main-menu");
    results.setCenter(createCenter());
    results.setBottom(createBottom());
    return results;
  }

  private Node createBottom() {
    StackPane results = new StackPane();
    Text contentBar = createContentBar();
    results.getChildren().add(contentBar);
    return results;
  }

  private Text createContentBar() {
    // loop through current passage content
    return null;
  }

  private Node createCenter() {
    StackPane results = new StackPane();
    results.getChildren().add(createContent());
    return results;
  }

  private Node createContent() {
    Text results = new Text();
    results.setText(contentBar.getValue());
    return results;
  }

  private void createContentString() {
    contentBar = new SimpleStringProperty();
    char[] charArray = currentPassage.getContent().toCharArray();

    for (char c : charArray) {
      contentBar.setValue(contentBar.getValue() + c);

      try {
        Thread.sleep(50);

      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

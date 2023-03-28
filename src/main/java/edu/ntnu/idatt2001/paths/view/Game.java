package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class Game implements Builder<Region> {

  @Override
  public Region build() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("main-menu");
    results.setCenter(createCenter());
    return results;
  }

  private Node createCenter() {
    return null;
  }
}

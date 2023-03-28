package edu.ntnu.idatt2001.paths.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class NewGame implements Builder<Region> {

  @Override
  public Region build() {
    BorderPane results = new BorderPane();
    return results;
  }
}

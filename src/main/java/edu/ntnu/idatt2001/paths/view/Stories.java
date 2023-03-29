package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Builder;

public class Stories implements Builder<Region> {

  private final Button goBackButton = new Button("Back");

  @Override
  public Region build() {
    BorderPane results = new BorderPane();
    results.setCenter(createCenter());
    configureBackButton();
    return results;
  }private void configureBackButton() {
    goBackButton.setOnAction(
            event -> {
              Region mainMenuRoot = new MainMenu().build();
              goBackButton.getScene().setRoot(mainMenuRoot);
            });
    }

  private Node createCenter() {
    StackPane results = new StackPane();
    results.getChildren().add(goBackButton);
    return results;
  }
}

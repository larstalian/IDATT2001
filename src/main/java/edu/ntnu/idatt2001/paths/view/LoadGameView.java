package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoadGameView {

  @lombok.Getter private final ComboBox<String> saveSelect;
  @lombok.Getter private final Button loadButton;
  @lombok.Getter private final Button goBackButton;
  @lombok.Getter private final BorderPane root;

  public LoadGameView() {
    saveSelect = new ComboBox<>();
    loadButton = new Button("Load Game");
    goBackButton = new Button("Go Back");
    root = build();
  }

  private BorderPane build() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("main-menu");
    results.setCenter(createCenter());
    return results;
  }

  private Node createCenter() {
    StackPane results = new StackPane();
    results.getChildren().add(createButtonVBox());
    return results;
  }

  private Node createButtonVBox() {
    VBox results = new VBox();
    results.getChildren().add(saveSelect);
    results.getChildren().add(loadButton);
    results.getChildren().add(goBackButton);
    results.getStyleClass().add("button-vbox");
    return results;
  }
}

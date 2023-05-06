package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoadGameView {

  private final ComboBox<String> saveSelect;
  private final Button loadButton;
  private final Button goBackButton;
  private final BorderPane root;

  public LoadGameView() {
    saveSelect = new ComboBox<>();
    loadButton = new Button("Load Game");
    goBackButton = new Button("Go Back");
    root = build();
  }

  public Region getRoot() {
    return root;
  }

  public ComboBox<String> getSaveSelect() {
    return saveSelect;
  }

  public Button getLoadButton() {
    return loadButton;
  }

  public Button getGoBackButton() {
    return goBackButton;
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

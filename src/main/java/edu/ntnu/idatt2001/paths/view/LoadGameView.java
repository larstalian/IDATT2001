package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class LoadGameView {

  @Getter private final ComboBox<String> saveSelect;
  @Getter private final Button loadButton;
  @Getter private final Button goBackButton;
  @Getter private final BorderPane root;

  public LoadGameView() {
    saveSelect = new ComboBox<>();
    saveSelect.getStyleClass().add("default-choice-box");

    loadButton = new Button("Load Game");
    loadButton.getStyleClass().add("default-button");

    goBackButton = new Button("Go Back");
    goBackButton.getStyleClass().add("default-button");

    root = createRoot();
  }

  private BorderPane createRoot() {
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

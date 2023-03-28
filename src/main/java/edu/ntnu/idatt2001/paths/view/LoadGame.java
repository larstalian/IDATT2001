package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class LoadGame implements Builder<Region> {

  private final ComboBox<String> saveSelect = new ComboBox<>();
  private final Button loadButton = new Button("Load game");
  private final Button goBackButton = new Button("Go Back");

  @Override
  public Region build() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("main-menu");
    results.setCenter(createCenter());
    configureLoadButton();
    configureGoBackButton();
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

  private void configureLoadButton() {
    loadButton.setOnAction(
        event -> {
          if (saveSelect.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid save file", ButtonType.OK)
                .showAndWait();
          } else {

            Region gameRoot = new Game().build();
            loadButton.getScene().setRoot(gameRoot);
          }
        });
  }

  private void configureGoBackButton() {
    goBackButton.setOnAction(
        event -> {
          Region newGameRoot = new MainMenu().build();
          goBackButton.getScene().setRoot(newGameRoot);
        });
  }
}

package edu.ntnu.idatt2001.paths.view;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Builder;

public class MainMenu implements Builder<Region> {

  private final Button newGameButton = new Button("New Game");
  private final Button loadGameButton = new Button("Load Game");
  private final Button storiesButton = new Button("Stories");
  private final Button exitButton = new Button("Exit");

  @Override
  public Region build() {
    BorderPane results = new BorderPane();
    results.setTop(createTop());
    results.setCenter(createCenter());
    results.getStyleClass().add("main-menu");
    configureNewGameButton();
    configureLoadGameButton();
    configureStoriesButton();
    configureExitButton();
    return results;
  }

  private Node createTop() {
    VBox results = new VBox();
    Text title = new Text("Paths");
    results.getChildren().add(title);
    results.getStyleClass().add("title");
    return results;
  }

  private Node createCenter() {
    StackPane results = new StackPane();
    results.getChildren().add(createButtonVBox());
    return results;
  }

  private Node createButtonVBox() {
    VBox results = new VBox();
    results.getChildren().add(newGameButton);
    results.getChildren().add(loadGameButton);
    results.getChildren().add(storiesButton);
    results.getChildren().add(exitButton);
    results.getStyleClass().add("button-vbox");
    return results;
  }

  private void configureNewGameButton() {
    newGameButton.setOnAction(
        event -> {
          Region newGameRoot = new NewGame().build();
          newGameButton.getScene().setRoot(newGameRoot);
        });
  }

  private void configureLoadGameButton() {
    loadGameButton.setOnAction(
        event -> {
          Region newGameRoot = new LoadGame().build();
          loadGameButton.getScene().setRoot(newGameRoot);
        });
  }

  private void configureStoriesButton() {
    storiesButton.setOnAction(
        event -> {
          Region newGameRoot = new Stories().build();
          storiesButton.getScene().setRoot(newGameRoot);
        });
  }

    private void configureExitButton() {
        exitButton.setOnAction(
            event -> {
           Platform.exit();
            });
    }
}

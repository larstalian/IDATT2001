package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Getter;

public class MainMenuView {
  @Getter private final Button newGameButton;
  @Getter private final Button loadGameButton;
  @Getter private final Button storiesButton;
  @Getter private final Button exitButton;
  @Getter private final BorderPane root;
  @Getter private final Button createStoryButton;

  public MainMenuView() {
    newGameButton = new Button("New Game");
    newGameButton.getStyleClass().add("default-button");

    loadGameButton = new Button("Load Game");
    loadGameButton.getStyleClass().add("default-button");

    storiesButton = new Button("Stories");
    storiesButton.getStyleClass().add("default-button");

    exitButton = new Button("Exit");
    exitButton.getStyleClass().add("default-button");

    createStoryButton = new Button("Create Your Own Story");
    createStoryButton.getStyleClass().add("default-button");

    root = createRoot();
  }

  private BorderPane createRoot() {
    BorderPane root = new BorderPane();
    root.setTop(createTop());
    root.setCenter(createCenter());
    root.getStyleClass().add("main-menu");
    return root;
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
    results.getChildren().add(createButtonVbox());
    return results;
  }

  private Node createButtonVbox() {
    VBox results = new VBox();
    results.getChildren().add(newGameButton);
    results.getChildren().add(loadGameButton);
    results.getChildren().add(storiesButton);
    results.getChildren().add(createStoryButton);
    results.getChildren().add(exitButton);
    results.getStyleClass().add("button-vbox");
    return results;
  }
}

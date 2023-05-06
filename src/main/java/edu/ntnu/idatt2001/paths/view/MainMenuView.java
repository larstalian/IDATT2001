package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainMenuView {
  @lombok.Getter private final Button newGameButton = new Button("New Game");
  @lombok.Getter private final Button loadGameButton = new Button("Load Game");
  @lombok.Getter private final Button storiesButton = new Button("Stories");
  @lombok.Getter private final Button exitButton = new Button("Exit");
  @lombok.Getter private final BorderPane root;

  public MainMenuView() {
    root = new BorderPane();
    root.setTop(createTop());
    root.setCenter(createCenter());
    root.getStyleClass().add("main-menu");
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
}

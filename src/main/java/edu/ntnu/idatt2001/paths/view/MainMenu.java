package edu.ntnu.idatt2001.paths.view;

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
    results.setMinSize(500, 500);
    results.getStyleClass().add("main-menu");
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
}

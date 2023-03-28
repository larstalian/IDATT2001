package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Builder;

public class MainMenu implements Builder<Region> {

  @Override
  public Region build() {
    BorderPane results = new BorderPane();
    results.setTop(createTop());
    results.setCenter(createCenter());
    results.setMinSize(500,500);
    results.getStyleClass().add("main-menu");
    return results;
  }

  private Node createTop() {
    VBox results = new VBox();
    Text title =  new Text("Paths");
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
    results.getChildren().add(new Button("New Game"));
    results.getChildren().add(new Button("Load Game"));
    results.getChildren().add(new Button("Stories"));
    Text text = new Text("This is a test");
    results.getChildren().add(new Button(text.getText()));
    results.getStyleClass().add("button-vbox");
    return results;
  }
}

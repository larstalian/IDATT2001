package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class NewGameView {

  @lombok.Getter private final BorderPane mainLayout;
  @lombok.Getter private final Button startNewGameButton;
  @lombok.Getter private final ComboBox<String> storySelect;
  @lombok.Getter private final Button goBackButton;
  @lombok.Getter private final TextField playerName;
  @lombok.Getter private final Button customizeGameOptionsButton;

  public NewGameView() {
    startNewGameButton = new Button("Start New Game");
    storySelect = new ComboBox<>();
    goBackButton = new Button("Go Back");
    playerName = new TextField();
    customizeGameOptionsButton = new Button("Customize Game Options");

    mainLayout = new BorderPane();
    mainLayout.getStyleClass().add("main-menu");
    mainLayout.setCenter(createCenter());
  }

  private Node createCenter() {
    StackPane centerLayout = new StackPane();
    centerLayout.getChildren().add(createButtonVBox());
    return centerLayout;
  }

  private Node createButtonVBox() {
    VBox buttonLayout = new VBox();
    buttonLayout.getChildren().add(playerName);
    buttonLayout.getChildren().add(startNewGameButton);
    buttonLayout.getChildren().add(createHBox());
    buttonLayout.getChildren().add(customizeGameOptionsButton);
    buttonLayout.getChildren().add(goBackButton);
    buttonLayout.getStyleClass().add("button-vbox");
    return buttonLayout;
  }

  private Node createHBox() {
    HBox hboxLayout = new HBox();
    hboxLayout.getChildren().add(new Text("Select Story: "));
    hboxLayout.getChildren().add(storySelect);
    hboxLayout.getStyleClass().add("button-hbox");
    return hboxLayout;
  }
}

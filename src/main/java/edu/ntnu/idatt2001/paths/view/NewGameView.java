package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class NewGameView {

  private final BorderPane mainLayout;
  private final Button startNewGameButton;
  private final ComboBox<String> storySelect;
  private final Button goBackButton;
  private final TextField playerName;
  private final Button customizeGameOptionsButton;

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

  public Region getRoot() {
    return mainLayout;
  }

  public Button getStartNewGameButton() {
    return startNewGameButton;
  }

  public ComboBox<String> getStorySelect() {
    return storySelect;
  }

  public Button getGoBackButton() {
    return goBackButton;
  }

  public TextField getPlayerName() {
    return playerName;
  }

  public Button getCustomizeGameOptionsButton() {
    return customizeGameOptionsButton;
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

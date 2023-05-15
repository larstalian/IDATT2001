package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.Getter;

public class NewGameView {

  @Getter private final BorderPane root;
  @Getter private final Button startNewGameButton;
  @Getter private final ComboBox<String> storySelect;
  @Getter private final Button goBackButton;
  @Getter private final TextField playerName;
  @Getter private final Button customizeGameOptionsButton;

  public NewGameView() {
    startNewGameButton = new Button("Start New Game");
    startNewGameButton.getStyleClass().add("default-button");

    storySelect = new ComboBox<>();
    storySelect.getStyleClass().add("default-choice-box");

    goBackButton = new Button("Go Back");
    goBackButton.getStyleClass().add("default-button");

    playerName = new TextField();
    playerName.getStyleClass().add("default-text-field");

    customizeGameOptionsButton = new Button("Customize Game Options");
    customizeGameOptionsButton.getStyleClass().add("default-button");

    root = createRoot();
  }

  private BorderPane createRoot() {
    BorderPane root = new BorderPane();
    root.getStyleClass().add("main-menu");
    root.setCenter(createCenter());
    return root;
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
    hboxLayout.getChildren().add(new Label("Select Story: "));
    hboxLayout.getChildren().add(storySelect);
    hboxLayout.getStyleClass().add("button-hbox");
    return hboxLayout;
  }
}

package edu.ntnu.idatt2001.paths.view;

import static edu.ntnu.idatt2001.paths.model.game.Player.PlayerConstants.*;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Builder;

public class NewGame implements Builder<Region> {

  private final Button startNewGameButton = new Button("Start Game");
  private final ComboBox<String> storySelect = new ComboBox<>();
  private final Button goBackButton = new Button("Go Back");

  private TextField playerName = new TextField();

  @Override
  public Region build() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("main-menu");
    results.setCenter(createCenter());
    configureGoBackButton();
    configureNewGameButton();
    configureStorySelect();
    configurePlayerName();
    return results;
  }

  private Node createCenter() {
    StackPane results = new StackPane();
    results.getChildren().add(createButtonVBox());
    return results;
  }

  private Node createButtonVBox() {
    VBox results = new VBox();
    results.getChildren().add(playerName);
    results.getChildren().add(startNewGameButton);
    results.getChildren().add(createHBox());
    results.getChildren().add(goBackButton);
    results.getStyleClass().add("button-vbox");
    return results;
  }

  private Node createHBox() {
    HBox results = new HBox();
    results.getChildren().add(new Text("Select Story: "));
    results.getChildren().add(storySelect);
    results.getStyleClass().add("button-hbox");
    return results;
  }

  private void configureNewGameButton() {
    startNewGameButton.setOnAction(
        event -> {
          String name = playerName.getText();
          String story = storySelect.getValue();
          if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH || story == null) {
            new Alert(
                    Alert.AlertType.WARNING,
                    "Please enter a valid name and select a story",
                    ButtonType.OK)
                .showAndWait();
          } else {
            Region gameRoot = new Game().build();
            startNewGameButton.getScene().setRoot(gameRoot);
          }
        });
  }

  private void configureGoBackButton() {
    goBackButton.setOnAction(
        event -> {
          Region mainMenuRoot = new MainMenu().build();
          goBackButton.getScene().setRoot(mainMenuRoot);
        });
  }

  private void configureStorySelect() {
    storySelect.getItems().addAll("Story 1", "Story 2", "Story 3");
    storySelect.setValue("Story 1");
  }

  private void configurePlayerName() {
    playerName.getStyleClass().add("player-name-field");
    playerName.setPromptText("Enter your name");
    playerName.setFocusTraversable(false);
  }
}

package edu.ntnu.idatt2001.paths.controller;

import edu.ntnu.idatt2001.paths.model.filehandlers.json.GameData;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.StoryFileHandler;
import edu.ntnu.idatt2001.paths.model.filehandlers.paths.StoryFileReader;
import edu.ntnu.idatt2001.paths.model.game.Game;
import edu.ntnu.idatt2001.paths.model.game.Player;
import edu.ntnu.idatt2001.paths.model.goals.Goal;
import edu.ntnu.idatt2001.paths.model.goals.ScoreGoal;
import edu.ntnu.idatt2001.paths.model.story.Story;
import edu.ntnu.idatt2001.paths.view.NewGameView;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

public class NewGameController {

  private final NewGameView newGameView;
  private final CustomizeGameOptionsController customizeGameOptionsController;

  public NewGameController() {
    newGameView = new NewGameView();
    customizeGameOptionsController = new CustomizeGameOptionsController();
    configureGoBackButton();
    configureNewGameButton();
    configureStorySelect();
    configurePlayerName();
    configureCustomizeGameOptionsButton();
  }

  public Region getRoot() {
    return newGameView.getRoot();
  }

  private void configureNewGameButton() {
    newGameView
        .getStartNewGameButton()
        .setOnAction(
            event -> {
              String name = newGameView.getPlayerName().getText();
              String story = newGameView.getStorySelect().getValue();
              if (isNameValid(name) && story != null) {
                startNewGame(name, story);
              } else {
                showWarningDialog();
              }
            });
  }

  private void configureCustomizeGameOptionsButton() {
    newGameView
        .getCustomizeGameOptionsButton()
        .setOnAction(event -> customizeGameOptionsController.show());
  }

  private void configureGoBackButton() {
    newGameView
        .getGoBackButton()
        .setOnAction(
            event -> {
              Region mainMenuRoot = new MainMenuController().getRoot();
              newGameView.getGoBackButton().getScene().setRoot(mainMenuRoot);
            });
  }

  private void configureStorySelect() {
    newGameView.getStorySelect().getItems().addAll(StoryFileReader.getSavedStories());
    newGameView.getStorySelect().getItems().addAll(StoryFileHandler.getSavedStories());
    newGameView.getStorySelect().selectionModelProperty().get().selectFirst();
  }

  private void configurePlayerName() {
    newGameView.getPlayerName().getStyleClass().add("player-name-field");
    newGameView.getPlayerName().setPromptText("Enter your name");
    newGameView.getPlayerName().setFocusTraversable(false);
  }

  private boolean isNameValid(String name) {
    int minNameLength = Player.PlayerConstants.MIN_NAME_LENGTH;
    int maxNameLength = Player.PlayerConstants.MAX_NAME_LENGTH;
    return name != null && name.length() >= minNameLength && name.length() <= maxNameLength;
  }

  private void startNewGame(String name, String story) {
    Story loadedStory = loadStoryFromFile(story);
    Player player;
    List<Goal> goals;

    if (loadedStory != null) {
      if (customizeGameOptionsController.isModified()) {
        player =
            new Player.Builder(name)
                .health(customizeGameOptionsController.getStartingHealth())
                .gold(customizeGameOptionsController.getStartingGold())
                .score(customizeGameOptionsController.getStartingScore())
                .inventory(customizeGameOptionsController.getStartingInventory())
                .build();

        goals = customizeGameOptionsController.getGoals();
      } else {
        player = new Player.Builder(name).health(100).build();
        goals = new ArrayList<>(List.of(new ScoreGoal(0)));
      }

      Game currentGame = new Game(player, loadedStory, goals);
      GameData gameData = new GameData(currentGame, currentGame.getStory().getOpeningPassage());

      Region gameRoot = new GameController(gameData).getRoot();
      newGameView.getStartNewGameButton().getScene().setRoot(gameRoot);
    }
  }

  private Story loadStoryFromFile(String story) {
    Story loadedStory = null;
    try {
      if (story.endsWith(StoryFileReader.getFileEnding())) {
        story = story.replace(StoryFileReader.getFileEnding(), "");
        loadedStory = StoryFileReader.readStoryFromFile(story);

      } else if (story.endsWith(".json")) {
        story = story.replace(".json", "");
        loadedStory = new StoryFileHandler().loadStoryFromFile(story);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return loadedStory;
  }

  private void showWarningDialog() {
    Alert alert =
        new Alert(
            Alert.AlertType.WARNING, "Please enter a valid name and select a story", ButtonType.OK);
    alert.showAndWait();
  }
}

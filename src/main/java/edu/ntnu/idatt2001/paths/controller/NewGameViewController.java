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

/**
 * Controller for the NewGameView class. Manages the actions associated with each button and field
 * in the new game screen, including validation of player name input, story selection, and game
 * configuration.
 */
public class NewGameViewController {

  private final NewGameView newGameView;
  private final CustomizeGameOptionsViewController customizeGameOptionsViewController;

  /** Creates a new instance of the NewGameViewController class. */
  public NewGameViewController() {
    newGameView = new NewGameView();
    customizeGameOptionsViewController = new CustomizeGameOptionsViewController();
    configureGoBackButton();
    configureNewGameButton();
    configureStorySelect();
    configurePlayerName();
    configureCustomizeGameOptionsButton();
  }

  /**
   * Returns the root node of the new game view.
   *
   * @return a Region representing the root node of the new game view.
   */
  public Region getRoot() {
    return newGameView.getRoot();
  }

  /**
   * Configures the action of the "Start New Game" button. It will check the validity of the player
   * name and the selected story before starting the new game. If either is invalid, a warning
   * dialog will be shown.
   */
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

  /**
   * Configures the action of the "Customize Game Options" button to show the game options
   * customization view.
   */
  private void configureCustomizeGameOptionsButton() {
    newGameView
        .getCustomizeGameOptionsButton()
        .setOnAction(event -> customizeGameOptionsViewController.show());
  }

  /**
   * Configures the action of the "Go Back" button. It changes the current scene's root to the main
   * menu view.
   */
  private void configureGoBackButton() {
    newGameView
        .getGoBackButton()
        .setOnAction(
            event -> {
              Region mainMenuRoot = new MainMenuViewController().getRoot();
              newGameView.getGoBackButton().getScene().setRoot(mainMenuRoot);
            });
  }

  /** Configures the story selection ComboBox with the available story files. */
  private void configureStorySelect() {
    newGameView.getStorySelect().getItems().addAll(StoryFileReader.getSavedStories());
    newGameView.getStorySelect().getItems().addAll(StoryFileHandler.getSavedStories());
    newGameView.getStorySelect().selectionModelProperty().get().selectFirst();
  }

  /** Configures the player name input field with additional styling and placeholder text. */
  private void configurePlayerName() {
    newGameView.getPlayerName().getStyleClass().add("player-name-field");
    newGameView.getPlayerName().setPromptText("Enter your name");
    newGameView.getPlayerName().setFocusTraversable(false);
  }

  /**
   * Checks if the provided name is valid according to the defined length constraints.
   *
   * @param name the name to validate
   * @return true if the name is valid, false otherwise
   */
  private boolean isNameValid(String name) {
    int minNameLength = Player.PlayerConstants.MIN_NAME_LENGTH;
    int maxNameLength = Player.PlayerConstants.MAX_NAME_LENGTH;
    return name != null && name.length() >= minNameLength && name.length() <= maxNameLength;
  }

  /**
   * Starts a new game with the given player name and story. Depending on the game options
   * customization, it creates a new player with specific attributes and goals.
   *
   * @param name the name of the player
   * @param story the selected story
   */
  private void startNewGame(String name, String story) {
    Story loadedStory = loadStoryFromFile(story);
    Player player;
    List<Goal> goals;

    if (loadedStory != null) {
      if (customizeGameOptionsViewController.isModified()) {
        player =
            new Player.Builder(name)
                .health(customizeGameOptionsViewController.getStartingHealth())
                .gold(customizeGameOptionsViewController.getStartingGold())
                .score(customizeGameOptionsViewController.getStartingScore())
                .inventory(customizeGameOptionsViewController.getStartingInventory())
                .build();

        goals = customizeGameOptionsViewController.getGoals();
      } else {
        player = new Player.Builder(name).health(100).build();
        goals = new ArrayList<>(List.of(new ScoreGoal(0)));
      }

      Game currentGame = new Game(player, loadedStory, goals);
      GameData gameData = new GameData(currentGame, currentGame.getStory().getOpeningPassage());

      Region gameRoot = new GameViewController(gameData).getRoot();
      newGameView.getStartNewGameButton().getScene().setRoot(gameRoot);
    }
  }

  /**
   * Loads a story from a file with the provided story name. The method supports both the custom
   * story file format and JSON.
   *
   * @param story the name of the story file
   * @return the loaded Story object, or null if an error occurred during loading
   */
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

  /** Shows a warning dialog when the player name or the selected story is invalid. */
  private void showWarningDialog() {
    Alert alert =
        new Alert(
            Alert.AlertType.WARNING, "Please enter a valid name and select a story", ButtonType.OK);
    alert.showAndWait();
  }
}

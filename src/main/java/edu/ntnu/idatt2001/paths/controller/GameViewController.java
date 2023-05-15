package edu.ntnu.idatt2001.paths.controller;

import static edu.ntnu.idatt2001.paths.model.media.IconHandler.*;
import static edu.ntnu.idatt2001.paths.view.util.Widgets.createAlert;

import edu.ntnu.idatt2001.paths.model.actions.Action;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.GameData;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.GameFileHandler;
import edu.ntnu.idatt2001.paths.model.game.Game;
import edu.ntnu.idatt2001.paths.model.game.Player;
import edu.ntnu.idatt2001.paths.model.goals.*;
import edu.ntnu.idatt2001.paths.model.media.BackgroundHandler;
import edu.ntnu.idatt2001.paths.model.media.SoundHandler;
import edu.ntnu.idatt2001.paths.model.story.Link;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import edu.ntnu.idatt2001.paths.view.GameView;
import edu.ntnu.idatt2001.paths.view.util.Widgets;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * The GameController class is responsible for managing the game logic, updating the game view,
 * handling user interactions, and maintaining the state of the game. It controls the flow of the
 * game, updating the player's inventory, health, gold, and score as the player navigates through
 * the passages. It also manages the sound and background settings and handles saving and exiting
 * the game.
 *
 * @see GameView
 * @see Game
 */
public class GameViewController {

  private static Passage currentPassage;
  private final SoundHandler soundHandler;
  private final BackgroundHandler backgroundHandler;
  private final List<Passage> visitedPassages;
  private final Game currentGame;
  private final GameView gameView;
  private final Player initialPlayer;
  private final AtomicBoolean isAnimationSkipped;

  /**
   * Creates a new GameController object, initializes the game view, player, sound, and background
   * handlers, and sets up the initial state of the game.
   *
   * @param gameData The game data containing the game model, current passage, and visited passages.
   */
  public GameViewController(GameData gameData) {
    currentGame = gameData.getGame();
    currentPassage = gameData.getPassage();
    gameView = new GameView();
    soundHandler = SoundHandler.getInstance();
    backgroundHandler = BackgroundHandler.getInstance();
    soundHandler.updateMusic(currentPassage, currentGame.getStory().getTitle());
    backgroundHandler.updateBackground(
        gameView.getRoot(), currentPassage, currentGame.getStory().getTitle());
    visitedPassages = new ArrayList<>();
    visitedPassages.addAll(gameData.getVisitedPassages());
    initialPlayer = new Player.Builder(currentGame.getPlayer()).build();

    updatePlayerHealth();
    animateContentBar();
    configureExitButton();
    configureContentBarScrollPane();
    updateLinkChoices();
    updateScoreLabel();
    updateGoldLabel();
    configureRestartGameButton();
    configureDeathExitButton();
    configureGoldIcon();
    configureSkipLabel();
    isAnimationSkipped = new AtomicBoolean(false);
  }

  /** Configures the behavior of the content bar scroll pane to skip the animation when clicked. */
  private void configureContentBarScrollPane() {
    gameView.getContentbarScrollPane().setOnMouseClicked(event -> isAnimationSkipped.set(true));
  }

  /** Updates the score label to the new value. */
  private void updateScoreLabel() {
    gameView.getScoreLabel().setText(String.valueOf(currentGame.getPlayer().getScore()));
  }

  /** Updates the gold label to the new value. */
  private void updateGoldLabel() {
    gameView.getGoldLabel().setText(String.valueOf(currentGame.getPlayer().getGold()));
  }

  /** Creates and animates the content string of the current passage. */
  private void animateContentBar() {
    gameView.getContentBar().set("");
    char[] charArray = currentPassage.getContent().toCharArray();
    AtomicInteger index = new AtomicInteger(0);

    gameView.getSkipLabel().setVisible(true);
    gameView.getLinks().setVisible(false);

    Timeline timeline = new Timeline();
    KeyFrame keyFrame =
        new KeyFrame(
            Duration.millis(30),
            event -> {
              if (index.get() < charArray.length) {
                gameView
                    .getContentBar()
                    .set(gameView.getContentBar().getValue() + charArray[index.get()]);
                index.incrementAndGet();
              }
              if (isAnimationSkipped.get()) {
                gameView.getContentBar().set(currentPassage.getContent());
                gameView.getLinks().setVisible(true);
                timeline.stop();
              }
            });

    timeline.getKeyFrames().add(keyFrame);
    timeline.setCycleCount(charArray.length);

    timeline
        .statusProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue == Animation.Status.STOPPED) {
                gameView.getSkipLabel().setVisible(false);
                gameView.getLinks().setVisible(true);
                isAnimationSkipped.set(false);
              }
            });

    timeline.play();
  }

  /** Updates the link choices based on the current passage. */
  private void updateLinkChoices() {
    gameView.getLinks().getChildren().clear();

    List<Link> availableLinks = getAvailableLinks();

    availableLinks.forEach(link -> gameView.getLinks().getChildren().add(createLinkButton(link)));
  }

  /**
   * Retrieves the list of available links for the current passage.
   *
   * @return the list of available links.
   */
  private List<Link> getAvailableLinks() {
    Set<Link> brokenLinks = new HashSet<>(currentGame.getStory().getBrokenLinks());

    return currentPassage.getLinks().stream()
        .filter(link -> isValidLink(link, brokenLinks))
        .toList();
  }

  /**
   * Configures the behavior of the link button, which navigates to the next passage when clicked.
   *
   * @param link The link object that represents the link in the game's story.
   */
  public void configureLinkButton(Button button, Link link) {
    button.setFocusTraversable(true);
    button.setOnAction(event -> handleLinkButtonClick(link));
  }

  /**
   * Creates a button for a given link.
   *
   * @param link the link for which the button is to be created.
   * @return the created button.
   */
  public Button createLinkButton(Link link) {
    Button button = new Button(link.getText());
    button.getStyleClass().add("link-button");
    configureLinkButton(button, link);
    return button;
  }

  /** Configures the skip label. It is set to visible when called */
  private void configureSkipLabel() {
    gameView.getSkipLabel().setVisible(false);
  }

  /**
   * Determines if a link is valid based on broken links and single-visit passages.
   *
   * @param link the link to be checked.
   * @param brokenLinks the set of broken links.
   * @return true if the link is valid, false otherwise.
   */
  private boolean isValidLink(Link link, Set<Link> brokenLinks) {
    if (brokenLinks.contains(link)) {
      return false;
    }

    Passage openingPassage = currentGame.getStory().getOpeningPassage();
    if (link.getRef().equals(openingPassage.getTitle())) {
      return true;
    }

    Passage linkedPassage = currentGame.getStory().getPassage(link);
    boolean isVisited =
        visitedPassages.stream().anyMatch(vp -> vp.getTitle().equals(link.getRef()));
    return !isVisited || !linkedPassage.isSingleVisitOnly();
  }

  /**
   * Handles a button click. All relevant variables in view are updated.
   *
   * @param link the link that was clicked.
   */
  private void handleLinkButtonClick(Link link) {
    if (link.getRef().equals(currentGame.getStory().getOpeningPassage().getTitle())) {
      currentPassage = currentGame.getStory().getOpeningPassage();

    } else {
      currentPassage = currentGame.go(link);
    }

    visitedPassages.add(currentPassage);
    executeActions(link);
    animateContentBar();
    updateInventory();
    updateGoldLabel();
    updateScoreLabel();
    updateLinkChoices();

    soundHandler.updateMusic(currentPassage, currentGame.getStory().getTitle());
    backgroundHandler.updateBackground(
        getRoot(), currentPassage, currentGame.getStory().getTitle());
    updatePlayerHealth();

    if (currentPassage.getLinks().size() == 0) {
      onGameFinish();
    }
  }

  private void configureExitButton() {
    gameView.getExitButton().setOnAction(e -> showSaveGameConfirmationDialog());
  }

  /**
   * Retrieves the root region of the game view.
   *
   * @return the root region of the game view.
   */
  public Region getRoot() {
    return gameView.getRoot();
  }

  /** Builds and shows the save game confirmation dialog. */
  private void showSaveGameConfirmationDialog() {
    String title = "Wait!";
    String header = "Are you sure you want to exit?";
    String content = "";

    Alert alert = Widgets.createAlert(title, header, content);

    ButtonType saveButton = new ButtonType("Save game and exit");
    ButtonType buttonNo = new ButtonType("Exit without saving");
    ButtonType buttonRestart = new ButtonType("Restart Game");
    ButtonType buttonCancel = new ButtonType("Cancel");

    alert.getButtonTypes().setAll(saveButton, buttonNo, buttonRestart, buttonCancel);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent()) {
      if (result.get() == saveButton) {
        GameFileHandler gameFileHandler = new GameFileHandler();

        try {
          gameFileHandler.saveGameToFile(currentGame, currentPassage, visitedPassages);
          switchToMainMenu();

        } catch (IOException e) {
          alert = createAlert("Error", "Error saving game", e.getMessage());
          alert.showAndWait();
        }
      } else if (result.get() == buttonNo) {
        switchToMainMenu();
      } else if (result.get() == buttonRestart) {
        restartGame();
      }
    }
  }

  /** Sets the scene to main menu. */
  private void switchToMainMenu() {
    MainMenuViewController mainMenuViewController = new MainMenuViewController();
    Region mainMenuRoot = mainMenuViewController.getRoot();
    SoundHandler.getInstance().playMenuMusic();
    gameView.getRoot().getScene().setRoot(mainMenuRoot);
  }

  /** Restarts the game by resetting the game state and the game view. */
  private void restartGame() {
    Game game = new Game(initialPlayer, currentGame.getStory(), currentGame.getGoals());
    GameData gameData = new GameData(game, game.getStory().getOpeningPassage(), visitedPassages);
    Region gameRoot = new GameViewController(gameData).getRoot();
    gameView.getRoot().getScene().setRoot(gameRoot);
  }

  /** Handles the event when the game is finished, displaying the game's end screen and goals. */
  private void onGameFinish() {
    VBox results = new VBox();
    results.getStyleClass().add("game-finish");

    results
        .getChildren()
        .addAll(new Label("The end"), new Label("One of them, at least..."), createGoalsScreen());

    Button restartButton = new Button("Restart Game");
    restartButton.setOnAction(e -> restartGame());
    results.getChildren().add(restartButton);

    gameView.getCenterInfo().getChildren().add(results);
    gameView.getCenterInfo().setAlignment(Pos.CENTER);
  }

  /** Configures the restart game button to restart the game when clicked. */
  private void configureRestartGameButton() {
    gameView.getDeathRestartButton().setOnAction(e -> restartGame());
  }

  /** Sets the scene to main menu. */
  private void configureDeathExitButton() {
    gameView.getDeathExitButton().setOnAction(e -> switchToMainMenu());
  }

  /**
   * Creates a GridPane containing the goals and their statuses in the game.
   *
   * @return a GridPane with the goal information.
   */
  private Node createGoalsScreen() {
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    gridPane.add(new Label("Goals"), 0, 0);
    gridPane.add(new Label("Status"), 1, 0);

    int rowIndex = 1;
    for (Goal goal : currentGame.getGoals()) {
      String goalLabel = null;
      if (goal instanceof GoldGoal) {
        goalLabel = "Gold";
      } else if (goal instanceof ScoreGoal) {
        goalLabel = "Score";
      } else if (goal instanceof HealthGoal) {
        goalLabel = "Health";
      } else if (goal instanceof InventoryGoal) {
        goalLabel = "Inventory";
      }

      if (goalLabel != null) {
        gridPane.add(new Label(goalLabel), 0, rowIndex);
        gridPane.add(createGoalStatusLabel(goal), 1, rowIndex);
        rowIndex++;
      }
    }

    gridPane.getStyleClass().add("goal-labels");
    return gridPane;
  }

  /**
   * Creates a label with the status of the given goal.
   *
   * @param goal the goal to create a label for.
   * @return a label with the status of the given goal.
   */
  private Label createGoalStatusLabel(Goal goal) {
    String statusText = goal.isFulfilled(currentGame.getPlayer()) ? "Completed" : "Not completed";
    return new Label(statusText);
  }

  /**
   * Updates the inventory section of the game view based on the player's inventory. If there is an
   * image located in the inventory-icons folder with the same name as the item, that image will be
   * used. Otherwise, the item name will be displayed as text.
   */
  private void updateInventory() {
    gameView.getInventory().getChildren().clear();
    Label inventoryTitle = new Label("Inventory:");
    gameView.getInventory().getChildren().add(inventoryTitle);
    inventoryTitle.getStyleClass().add("inventory-view-title");
    List<String> playerItems = currentGame.getPlayer().getInventory();

    playerItems.forEach(
        item -> {
          HBox itemContainer = new HBox();
          itemContainer.setAlignment(Pos.CENTER);

          try {
            Image icon = getInventoryIcon(item);

            if (icon != null) {
              ImageView imageView = new ImageView(icon);
              imageView.setFitHeight(40);
              imageView.setFitWidth(40);
              itemContainer.getChildren().add(imageView);

            } else {
              Label itemText = new Label(item);
              itemText.getStyleClass().add("inventory-view-text");
              itemContainer.getChildren().add(itemText);
            }
            gameView.getInventory().getChildren().add(itemContainer);

          } catch (IOException e) {
            Widgets.createAlert("Error", "Error loading inventory icon", e.getMessage())
                .showAndWait();
          }
        });
  }

  /**
   * Executes a list of actions based on the link clicked by the player.
   *
   * @param link The link object that represents the link in the game's story.
   */
  private void executeActions(Link link) {
    List<Action> actions = link.getActions();
    actions.forEach(action -> action.execute(currentGame.getPlayer()));
  }

  /** Updates the player's health and handles the event of the player's death. */
  private void updatePlayerHealth() {
    double health = currentGame.getPlayer().getHealth();
    gameView.getHealthBar().setProgress(health / 100);
    if (health <= 0) {
      onPlayerDeath();
    }
  }

  /**
   * Handles the event when the player dies, displaying the death screen and playing the death
   * sound.
   */
  private void onPlayerDeath() {
    gameView.getCenterInfo().getChildren().add(gameView.createDeathScreen());
    soundHandler.playSound("death");
    gameView.getLinks().getChildren().clear();
  }

  /** Configures the gold icon in the game view. */
  private void configureGoldIcon() {
    try {
      gameView.setGoldIcon(getIcon("gold"));
    } catch (IOException e) {
      Widgets.createAlert("Error", "Error loading inventory icon", e.getMessage()).showAndWait();
    }
  }
}

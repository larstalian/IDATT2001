package edu.ntnu.idatt2001.paths.controller;

import static edu.ntnu.idatt2001.paths.view.util.Widgets.createAlert;

import edu.ntnu.idatt2001.paths.model.actions.Action;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.GameData;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.GameFileHandler;
import edu.ntnu.idatt2001.paths.model.game.Player;
import edu.ntnu.idatt2001.paths.model.goals.*;
import edu.ntnu.idatt2001.paths.model.media.BackgroundHandler;
import edu.ntnu.idatt2001.paths.model.media.IconHandler;
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

public class GameController {

  private static Passage currentPassage;

  private final SoundHandler soundHandler;

  private final BackgroundHandler backgroundHandler;

  private final List<Passage> visitedPassages;

  private final edu.ntnu.idatt2001.paths.model.game.Game currentGame;
  private final GameView gameView;
  private final Player initialPlayer;
  private final AtomicBoolean isAnimationSkipped;

  public GameController(GameData gameData) {
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

    isAnimationSkipped = new AtomicBoolean(false);
  }

  private void configureContentBarScrollPane() {
    gameView
        .getContentbarScrollPane()
        .setOnMouseClicked(
            event -> isAnimationSkipped.set(true));
  }

  private void updateScoreLabel() {
    gameView.getScoreLabel().setText(String.valueOf(currentGame.getPlayer().getScore()));
  }

  private void updateGoldLabel() {
    gameView.getGoldLabel().setText(String.valueOf(currentGame.getPlayer().getGold()));
  }

  /** Creates and animates the content string of the current passage. */
  private void animateContentBar() {
    gameView.setContentBar("");
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
                gameView.setContentBar(
                    gameView.contentBarProperty().getValue() + charArray[index.get()]);
                index.incrementAndGet();
              }
              if (isAnimationSkipped.get()) {
                gameView.setContentBar(currentPassage.getContent());
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

  private void updateLinkChoices() {
    gameView.getLinks().getChildren().clear();

    List<Link> availableLinks = getAvailableLinks();

    availableLinks.forEach(
        link -> {
          gameView.getLinks().getChildren().add(gameView.createLinkButton(link));
          configureLinkButton(link);
        });
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

  private void configureLinkButton(Link link) {
    gameView
        .getLinks()
        .getChildren()
        .forEach(
            node -> {
              if (node instanceof Button button) {
                button.setOnAction(
                    event -> handleLinkButtonClick(link));
              }
            });
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
    MainMenuController mainMenuController = new MainMenuController();
    Region mainMenuRoot = mainMenuController.getRoot();
    SoundHandler.getInstance().playMenuMusic();
    gameView.getRoot().getScene().setRoot(mainMenuRoot);
  }

  private void restartGame() {
    edu.ntnu.idatt2001.paths.model.game.Game game =
        new edu.ntnu.idatt2001.paths.model.game.Game(
            initialPlayer, currentGame.getStory(), currentGame.getGoals());
    GameData gameData = new GameData(game, game.getStory().getOpeningPassage(), visitedPassages);
    Region gameRoot = new GameController(gameData).getRoot();
    gameView.getRoot().getScene().setRoot(gameRoot);
  }

  private void onGameFinish() {
    VBox vBox = new VBox();
    vBox.getStyleClass().add("game-finish");

    vBox.getChildren()
        .addAll(new Label("The end"), new Label("One of them, at least..."), createGoalsScreen());

    Button restartButton = new Button("Restart Game");
    restartButton.setOnAction(e -> restartGame());
    vBox.getChildren().add(restartButton);

    gameView.getCenterInfo().getChildren().add(vBox);
    gameView.getCenterInfo().setAlignment(Pos.CENTER);
  }

  private void configureRestartGameButton() {
    gameView.getDeathRestartButton().setOnAction(e -> restartGame());
  }

  private void configureDeathExitButton() {
    gameView.getDeathExitButton().setOnAction(e -> switchToMainMenu());
  }

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

  private Label createGoalStatusLabel(Goal goal) {
    String statusText = goal.isFulfilled(currentGame.getPlayer()) ? "Completed" : "Not completed";
    return new Label(statusText);
  }

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
            Image icon = IconHandler.getInventoryIcon(item);

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

  private void executeActions(Link link) {
    List<Action> actions = link.getActions();
    actions.forEach(action -> action.execute(currentGame.getPlayer()));
  }

  private void updatePlayerHealth() {
    double health = currentGame.getPlayer().getHealth();
    gameView.getHealthBar().setProgress(health / 100);
    if (health <= 0) {
      onPlayerDeath();
    }
  }

  private void onPlayerDeath() {
    gameView.getCenterInfo().getChildren().add(gameView.createDeathScreen());
    soundHandler.playSound("death");
    gameView.getLinks().getChildren().clear();
  }
}

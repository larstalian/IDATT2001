package edu.ntnu.idatt2001.paths.view;

import static edu.ntnu.idatt2001.paths.view.Widgets.*;

import edu.ntnu.idatt2001.paths.model.actions.Action;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.GameFileHandler;
import edu.ntnu.idatt2001.paths.model.media.BackgroundHandler;
import edu.ntnu.idatt2001.paths.model.media.InventoryIconHandler;
import edu.ntnu.idatt2001.paths.model.media.SoundHandler;
import edu.ntnu.idatt2001.paths.model.story.Link;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Builder;
import javafx.util.Duration;

/**
 * The {@code Game} class represents the game view and its UI components. It manages the game flow,
 * user input, and updating the UI based on the game state.
 *
 * <p>This class utilizes JavaFX components to build the user interface and the game logic. It
 * handles user input via arrow keys, enter, and space-bar for navigation and interaction. It also
 * animates the text content for each passage and allows users to skip the animation.
 *
 * <p>The {@code Game} class contains methods to create UI elements and manage the game flow. For
 * example, it creates the content bar, link choices, and handles scene changes.
 *
 * <p>Usage example:
 *
 * <pre>{@code
 * Game game = new Game();
 * game.setCurrentGame(chosenGame);
 * Region gameUI = game.build();
 * }</pre>
 */
public class Game implements Builder<Region> {

  private static Passage currentPassage;
  private static edu.ntnu.idatt2001.paths.model.game.Game currentGame;
  private final StringProperty contentBar;
  private final AtomicBoolean isAnimationSkipped;
  private final VBox links;
  private final SoundHandler soundHandler;
  private final BackgroundHandler backgroundHandler;
  private final VBox inventory;
  private ProgressBar healthBar;
  private Label skipLabel;
  private BorderPane root;

  /**
   * Constructs a new {@code Game} instance, initializing the required properties and handlers.
   *
   * <p>This constructor initializes the {@link BackgroundHandler}, {@link SoundHandler}, {@code
   * VBox} for links, {@code StringProperty} for the content bar, and an {@code AtomicBoolean} for
   * tracking the animation skipping state.
   */
  public Game() {
    backgroundHandler = BackgroundHandler.getInstance();
    soundHandler = SoundHandler.getInstance();
    links = new VBox();
    contentBar = new SimpleStringProperty();
    isAnimationSkipped = new AtomicBoolean(false);
    inventory = new VBox();
  }

  /**
   * Sets the current game.
   *
   * @param chosenGame the chosen game to be set as the current game.
   */
  public static void setCurrentGame(edu.ntnu.idatt2001.paths.model.game.Game chosenGame) {
    currentGame = chosenGame;
  }

  /**
   * Sets the current passage
   *
   * @param passage the passage to be set as the current passage
   */
  public static void setCurrentPassage(Passage passage) {
    currentPassage = passage;
  }

  /**
   * Returns the root node.
   *
   * @return the root node of the game.
   */
  public BorderPane getRoot() {
    return root;
  }

  /**
   * Builds the game UI and sets up the game.
   *
   * @return a Region containing the game UI.
   */
  @Override
  public Region build() {
    soundHandler.updateMusic(currentPassage, currentGame.getStory().getTitle());
    root = createRoot();
    addSceneListener(root);
    backgroundHandler.updateBackground(
            root, currentPassage, currentGame.getStory().getTitle());
    soundHandler.updateMusic(currentPassage, currentGame.getStory().getTitle());
    return root;
  }

  /**
   * Creates the root node.
   *
   * @return the BorderPane representing the root node.
   */
  private BorderPane createRoot() {
    BorderPane root = new BorderPane();
    root.getStyleClass().add("main-menu");
    root.setCenter(createCenter());
    root.setBottom(createBottom());
    root.setRight(createRight());
    root.setTop(createTop());
    return root;
  }

  /**
   * Creates the top UI element containing the exit button.
   *
   * @return a Node representing the top side of the game UI.
   */
  private Node createTop() {
    BorderPane top = new BorderPane();
    Button exitButton = createExitButton();
    top.setLeft(exitButton);
    top.setRight(createHealthBar());
    exitButton.setAlignment(Pos.TOP_LEFT);
    BorderPane.setMargin(exitButton, new Insets(10, 10, 0, 0));
    return top;
  }

  /**
   * Creates the right side UI element.
   *
   * @return a Node representing the right side of the game UI.
   */
  private Node createRight() {
    BorderPane right = new BorderPane();
    right.setRight(createInventory());
    right.setBottom(createLinkChoices());
    return right;
  }


  /**
   * Creates the bottom UI element.
   *
   * @return a Node representing the bottom side of the game UI.
   */
  private Node createBottom() {
    StackPane bottom = new StackPane();
    ScrollPane contentBarText = createContentBar();
    contentBarText.getStyleClass().add("content-bar");
    bottom.getChildren().add(contentBarText);
    StackPane.setAlignment(contentBarText, Pos.BOTTOM_CENTER);

    skipLabel = new Label("(click to skip)");
    skipLabel.getStyleClass().add("skip-label");
    skipLabel.setVisible(false);

    bottom.getChildren().add(skipLabel);
    StackPane.setAlignment(skipLabel, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(skipLabel, new Insets(0, 10, 10, 0));

    return bottom;
  }

  /**
   * Creates the center UI element.
   *
   * @return a Node representing the center of the game UI.
   */
  private Node createCenter() {
    return new AnchorPane();
  }

  /**
   * Creates the content bar.
   *
   * @return a ScrollPane containing the content bar.
   */
  private ScrollPane createContentBar() {
    Text contentBarText = new Text();
    contentBarText.textProperty().bind(contentBar);
    TextFlow textFlow = new TextFlow(contentBarText);
    ScrollPane scrollPane = new ScrollPane(textFlow);

    textFlow
        .prefWidthProperty()
        .bind(Bindings.selectDouble(scrollPane.viewportBoundsProperty(), "width"));

    scrollPane.getStyleClass().add("scroll-pane");
    textFlow.getStyleClass().add("super-text-flow");
    scrollPane.setOnMouseClicked(event -> isAnimationSkipped.set(true));
    return scrollPane;
  }

  /** Creates and animates the content string of the current passage. */
  private void createContentString() {
    contentBar.set("");
    char[] charArray = currentPassage.getContent().toCharArray();
    AtomicInteger index = new AtomicInteger(0);

    skipLabel.setVisible(true);
    links.setVisible(false);

    Timeline timeline = new Timeline();
    KeyFrame keyFrame =
        new KeyFrame(
            Duration.millis(30),
            event -> {
              if (index.get() < charArray.length) {
                contentBar.set(contentBar.getValue() + charArray[index.get()]);
                index.incrementAndGet();
              }
              if (isAnimationSkipped.get()) {
                contentBar.set(currentPassage.getContent());
                links.setVisible(true);
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
                skipLabel.setVisible(false);
                links.setVisible(true);
                isAnimationSkipped.set(false);
              }
            });

    timeline.play();
  }

  private ProgressBar createHealthBar() {
    healthBar = new ProgressBar();
    healthBar.setMaxWidth(Double.MAX_VALUE);
    healthBar.getStyleClass().add("health-bar");
    updatePlayerHealth();
    return healthBar;
  }

  private void updatePlayerHealth(){
    double health = currentGame.getPlayer().getHealth();
    healthBar.setProgress(health / 100);
  }

  private Node createInventory() {
    AnchorPane.setTopAnchor(inventory, 10.0);
    AnchorPane.setRightAnchor(inventory, 10.0);
    inventory.getStyleClass().add("inventory-vbox");
    updateInventory();
    return inventory;
  }

  private void updateInventory() {
    inventory.getChildren().clear();
    Label inventoryTitle = new Label("Inventory:");
    inventory.getChildren().add(inventoryTitle);
    inventoryTitle.getStyleClass().add("inventory-view-title");
    List<String> playerItems = currentGame.getPlayer().getInventory();

    playerItems.forEach(
        item -> {
          HBox itemContainer = new HBox();
          itemContainer.setAlignment(Pos.CENTER);
          
          try{
          Image icon = InventoryIconHandler.getIcon(item);
          
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
          inventory.getChildren().add(itemContainer);
          }catch (IOException e){
            Widgets.createAlert("Error", "Error loading inventory icon", e.getMessage()).showAndWait();
          }
        });
  }

  private void executeActions(Link link) {
    List<Action> actions = link.getActions();
    actions.forEach(action -> action.execute(currentGame.getPlayer()));
  }

  /**
   * Creates the link choices UI element.
   *
   * @return a Node containing the link choices.
   */
  private Node createLinkChoices() {
    links.setAlignment(Pos.BOTTOM_CENTER);
    links.setPrefHeight(VBox.USE_COMPUTED_SIZE);
    VBox.setMargin(links, new Insets(10, 10, 10, 10));
    links.getStyleClass().add("link-view");
    updateLinkChoices();
    final AnchorPane anchorPane = new AnchorPane();
    AnchorPane.setBottomAnchor(links, 0.0);
    AnchorPane.setLeftAnchor(links, 0.0);
    AnchorPane.setRightAnchor(links, 0.0);
    anchorPane.getChildren().add(links);

    return anchorPane;
  }

  /** Updates the link choices UI element. */
  private void updateLinkChoices() {
    links.getChildren().clear();
    currentPassage
        .getLinks()
        .forEach(
            link -> {
              Button button = new Button(link.getText());
              button.setFocusTraversable(true);
              button.getStyleClass().add("link-button");
              button.setOnAction(
                  e -> {
                    currentPassage = currentGame.go(link);
                    executeActions(link);
                    createContentString();
                    updateLinkChoices();
                    updatePlayerHealth();
                    updateInventory();
                    backgroundHandler.updateBackground(
                        root, currentPassage, currentGame.getStory().getTitle());
                    soundHandler.updateMusic(currentPassage, currentGame.getStory().getTitle());
                  });
              links.getChildren().add(button);
            });
  }

  /**
   * Adds a listener to the root node's scene property.
   *
   * @param root the BorderPane to add the listener to.
   */
  private void addSceneListener(BorderPane root) {
    root.sceneProperty()
        .addListener(
            new ChangeListener<>() {
              @Override
              public void changed(
                  ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                if (newValue != null) {
                  createContentString();
                  root.sceneProperty().removeListener(this);
                  setupArrowKeysNavigation(newValue);
                }
              }
            });
  }

  /**
   * Sets up arrow key navigation in the scene.
   *
   * @param scene the scene to set up arrow key navigation.
   */
  private void setupArrowKeysNavigation(Scene scene) {
    AtomicInteger selectedIndex = new AtomicInteger(0);
    scene.addEventFilter(
        KeyEvent.KEY_PRESSED,
        keyEvent -> {
          int previousIndex = selectedIndex.get();
          switch (keyEvent.getCode()) {
            case UP -> selectedIndex.updateAndGet(
                i -> (i - 1 + links.getChildren().size()) % links.getChildren().size());
            case DOWN -> selectedIndex.updateAndGet(i -> (i + 1) % links.getChildren().size());
            case ENTER -> {
              Button selectedButton = (Button) links.getChildren().get(selectedIndex.get());
              selectedButton.fire();
            }
            case SPACE -> isAnimationSkipped.set(true);
            default -> {}
          }
          if (previousIndex != selectedIndex.get()) {
            links.getChildren().get(previousIndex).getStyleClass().remove("link-button-selected");
            links
                .getChildren()
                .get(selectedIndex.get())
                .getStyleClass()
                .add("link-button-selected");
          }
          keyEvent.consume();
        });
  }

  /**
   * Creates the exit button.
   *
   * @return a Button representing the exit button.
   */
  private Button createExitButton() {
    Button exitButton = new Button("Exit");
    exitButton.getStyleClass().add("exit-button");
    exitButton.setOnAction(e -> showSaveGameConfirmationDialog());
    return exitButton;
  }

  /** Builds and shows the save game confirmation dialog. */
  private void showSaveGameConfirmationDialog() {
    String title = "Save Game";
    String header = "Do you wish to save the game?";
    String content = "";

    Alert alert = createAlert(title, header, content);

    ButtonType buttonYes = new ButtonType("Yes");
    ButtonType buttonNo = new ButtonType("No");
    ButtonType buttonCancel = new ButtonType("Cancel");

    alert.getButtonTypes().setAll(buttonYes, buttonNo, buttonCancel);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent()) {
      if (result.get() == buttonYes) {
        GameFileHandler gameFileHandler = new GameFileHandler();

        try {
          gameFileHandler.saveGameToFile(currentGame, currentPassage);
          switchToMainMenu();

        } catch (IOException e) {
          alert = createAlert("Error", "Error saving game", e.getMessage());
          alert.showAndWait();
        }
      } else if (result.get() == buttonNo) {
        switchToMainMenu();
      }
    }
  }

  /** Sets the scene to main menu. */
  private void switchToMainMenu() {
    MainMenu mainMenu = new MainMenu();
    Region mainMenuRoot = mainMenu.build();
    root.getScene().setRoot(mainMenuRoot);
  }
}

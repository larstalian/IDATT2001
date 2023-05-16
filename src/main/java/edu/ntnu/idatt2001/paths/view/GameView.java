package edu.ntnu.idatt2001.paths.view;

import edu.ntnu.idatt2001.paths.controller.GameViewController;
import edu.ntnu.idatt2001.paths.model.game.Game;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.Getter;
import lombok.Setter;

/**
 * The GameView class is responsible for creating and maintaining the graphical user interface (GUI)
 * elements of the game. It sets up the game layout and includes components for displaying the
 * player's health, gold, score, inventory, and available actions. The interactions and
 * configurations of these components are handled by the GameViewController class.
 *
 * @see GameViewController
 * @see Game
 */
public class GameView {

  @Getter private final VBox links;
  @Getter private final Label goldLabel;
  @Getter private final Label scoreLabel;
  @Getter private final VBox inventory;
  @Getter private final ProgressBar healthBar;
  @Getter private final BorderPane root;
  @Getter private final Button exitButton;
  @Getter private final Label skipLabel;
  @Getter private final VBox centerInfo;
  @Getter private StringProperty contentBar;
  @Getter private ScrollPane contentbarScrollPane;
  @Getter private Button deathExitButton;
  @Getter private Button deathRestartButton;
  @Setter private Image goldIcon;
  @Getter private Label playerName;

  /**
   * Creates a new GameView object.
   *
   * <p>The constructor initializes the instance variables and calls the createRoot() method to
   * create the root node.
   */
  public GameView() {
    centerInfo = new VBox();
    skipLabel = new Label("click to skip");
    links = new VBox();
    contentBar = new SimpleStringProperty();
    inventory = new VBox();
    goldLabel = new Label();
    scoreLabel = new Label();
    deathExitButton = new Button("Exit");
    deathRestartButton = new Button("Restart");
    healthBar = new ProgressBar();

    exitButton = new Button("Exit");
    exitButton.getStyleClass().add("default-button");

    playerName = new Label();
    playerName.getStyleClass().add("default-label");

    root = createRoot();
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
    top.setCenter(createTopCenter());
    top.setRight(createHealthBar());
    exitButton.setAlignment(Pos.TOP_LEFT);
    BorderPane.setMargin(exitButton, new Insets(10, 10, 0, 0));
    return top;
  }

  /**
   * Creates the top center UI element containing the gold information.
   *
   * @return a Node representing the top center side of the game UI.
   */
  private Node createTopCenter() {
    HBox results = new HBox();
    results.getChildren().add(playerName);
    results.getChildren().add(createGoldInfo());
    results.getChildren().add(createScoreInfo());
    results.getStyleClass().add("top-info");
    return results;
  }

  private Node createScoreInfo() {
    HBox results = new HBox();
    results.getChildren().add(new Label("Score: "));
    results.getStyleClass().add("score-info");
    results.getChildren().add(scoreLabel);
    return results;
  }

  /**
   * Creates the gold information UI element containing the gold icon and gold label.
   *
   * @return a Node representing the gold information UI element.
   */
  private Node createGoldInfo() {
    HBox results = new HBox();
    results.getChildren().add(createGoldIconContainer());
    goldLabel.getStyleClass().add("gold-label");
    results.getChildren().add(goldLabel);
    return results;
  }

  private Node createGoldIconContainer() {
    HBox imageContainer = new HBox();
    imageContainer.getChildren().add(new ImageView(goldIcon));
    return imageContainer;
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

    skipLabel.getStyleClass().add("skip-label");

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
    return centerInfo;
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
    contentbarScrollPane = new ScrollPane(textFlow);

    textFlow
        .prefWidthProperty()
        .bind(Bindings.selectDouble(contentbarScrollPane.viewportBoundsProperty(), "width"));

    contentbarScrollPane.getStyleClass().add("scroll-pane");
    textFlow.getStyleClass().add("super-text-flow");
    return contentbarScrollPane;
  }

  /**
   * Creates the health bar.
   *
   * @return a ProgressBar representing the health bar.
   */
  private ProgressBar createHealthBar() {
    healthBar.setMaxWidth(Double.MAX_VALUE);
    healthBar.getStyleClass().add("health-bar");
    return healthBar;
  }

  /**
   * Creates the death screen.
   *
   * @return a Node representing the death screen
   */
  public Node createDeathScreen() {
    centerInfo.getStyleClass().add("death-screen");
    centerInfo.getChildren().add(new Label("YOU ARE DEAD"));

    deathExitButton = new Button("Exit to Main Menu");
    deathRestartButton = new Button("Restart Game");

    HBox deathButtons = new HBox();
    deathButtons.getChildren().addAll(deathExitButton, deathRestartButton);
    deathButtons.getStyleClass().add("death-screen");
    return deathButtons;
  }

  private Node createInventory() {
    AnchorPane.setTopAnchor(inventory, 10.0);
    AnchorPane.setRightAnchor(inventory, 10.0);
    inventory.getStyleClass().add("inventory-vbox");
    return inventory;
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
    final AnchorPane anchorPane = new AnchorPane();
    AnchorPane.setBottomAnchor(links, 0.0);
    AnchorPane.setLeftAnchor(links, 0.0);
    AnchorPane.setRightAnchor(links, 0.0);
    anchorPane.getChildren().add(links);
    return anchorPane;
  }

  /**
   * Creates the exit button.
   *
   * @return a Button representing the exit button.
   */
  private Button createExitButton() {
    exitButton.getStyleClass().add("exit-button");
    return exitButton;
  }
}

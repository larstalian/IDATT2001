package edu.ntnu.idatt2001.paths.view;

import edu.ntnu.idatt2001.paths.model.media.IconHandler;
import edu.ntnu.idatt2001.paths.model.story.Link;
import edu.ntnu.idatt2001.paths.view.util.Widgets;
import java.io.IOException;
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

public class GameView {

  private final StringProperty contentBar;
  private final VBox links;
  private final Label goldLabel;
  private final Label scoreLabel;
  private final VBox inventory;
  private final ProgressBar healthBar;
  private final BorderPane root;
  private final Button exitButton;
  private Label skipLabel;
  private VBox centerInfo;
  private ScrollPane contentbarScrollPane;
  private Button deathExitButton;
  private Button deathRestartButton;

  public GameView() {
    links = new VBox();
    contentBar = new SimpleStringProperty();
    inventory = new VBox();
    goldLabel = new Label();
    scoreLabel = new Label();
    deathExitButton = new Button("Exit");
    deathRestartButton = new Button("Restart");

    healthBar = new ProgressBar();
    exitButton = new Button("Exit");

    root = createRoot();
  }

  public BorderPane getRoot() {
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
    top.setCenter(createTopCenter());
    top.setRight(createHealthBar());
    exitButton.setAlignment(Pos.TOP_LEFT);
    BorderPane.setMargin(exitButton, new Insets(10, 10, 0, 0));
    return top;
  }

  public void setContentBar(String contentBar) {
    this.contentBar.set(contentBar);
  }

  public StringProperty contentBarProperty() {
    return contentBar;
  }

  public VBox getLinks() {
    return links;
  }

  public Label getGoldLabel() {
    return goldLabel;
  }

  public Label getScoreLabel() {
    return scoreLabel;
  }

  public VBox getInventory() {
    return inventory;
  }

  public ProgressBar getHealthBar() {
    return healthBar;
  }

  public Label getSkipLabel() {
    return skipLabel;
  }

  public VBox getCenterInfo() {
    return centerInfo;
  }

  /**
   * Creates the top center UI element containing the gold information.
   *
   * @return a Node representing the top center side of the game UI.
   */
  private Node createTopCenter() {
    HBox results = new HBox();
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
    results.getChildren().add(createGoldIcon());
    goldLabel.getStyleClass().add("gold-label");
    results.getChildren().add(goldLabel);
    return results;
  }

  private Node createGoldIcon() {
    HBox imageContainer = new HBox();
    Image goldIcon = null;

    try {
      goldIcon = IconHandler.getIcon("gold-coin");
    } catch (IOException e) {
      Widgets.createAlert("Error", "Error loading gold icon", e.getMessage()).showAndWait();
    }

    if (goldIcon != null) {
      ImageView imageView = new ImageView(goldIcon);
      imageView.setFitHeight(20);
      imageView.setFitWidth(20);
      imageContainer.getChildren().add(imageView);
      return imageContainer;
    }

    imageContainer.getChildren().add(new Label("Gold: "));
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
    centerInfo = new VBox();
    return centerInfo;
  }

  public ScrollPane getContentbarScrollPane() {
    return contentbarScrollPane;
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

  private ProgressBar createHealthBar() {
    healthBar.setMaxWidth(Double.MAX_VALUE);
    healthBar.getStyleClass().add("health-bar");
    return healthBar;
  }

  public Button getDeathExitButton() {
    return deathExitButton;
  }

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

  public Button getDeathRestartButton() {
    return deathRestartButton;
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
   * Creates a button for a given link.
   *
   * @param link the link for which the button is to be created.
   * @return the created button.
   */
  public Button createLinkButton(Link link) {
    Button button = new Button(link.getText());
    button.setFocusTraversable(true);
    button.getStyleClass().add("link-button");
    return button;
  }

  public Button getExitButton() {
    return exitButton;
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

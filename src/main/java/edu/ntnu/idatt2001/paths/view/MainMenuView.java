package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Getter;

/**
 * Represents the main menu view in the Paths game. This view is responsible for creating and
 * managing all the elements related to the main menu.
 */
public class MainMenuView {
  @Getter private final Button newGameButton;
  @Getter private final Button loadGameButton;
  @Getter private final Button storiesButton;
  @Getter private final Button exitButton;
  @Getter private final BorderPane root;
  @Getter private final Button createStoryButton;

  /** Constructs a MainMenuView object. Initializes all the buttons and sets up the root node. */
  public MainMenuView() {
    newGameButton = new Button("New Game");
    newGameButton.getStyleClass().add("default-button");

    loadGameButton = new Button("Load Game");
    loadGameButton.getStyleClass().add("default-button");

    storiesButton = new Button("Stories");
    storiesButton.getStyleClass().add("default-button");

    exitButton = new Button("Exit");
    exitButton.getStyleClass().add("default-button");

    createStoryButton = new Button("Create Your Own Story");
    createStoryButton.getStyleClass().add("default-button");

    root = createRoot();
  }

  /**
   * Creates the root node for the main menu view.
   *
   * @return a BorderPane object that serves as the root of the main menu view.
   */
  private BorderPane createRoot() {
    BorderPane root = new BorderPane();
    root.setTop(createTop());
    root.setCenter(createCenter());
    root.getStyleClass().add("main-menu");
    return root;
  }

  /**
   * Creates the top section of the main menu view.
   *
   * @return a Node object representing the top section of the main menu view.
   */
  private Node createTop() {
    VBox results = new VBox();
    Text title = new Text("Paths");
    results.getChildren().add(title);
    results.getStyleClass().add("title");
    return results;
  }

  /**
   * Creates the center section of the main menu view.
   *
   * @return a Node object representing the center section of the main menu view.
   */
  private Node createCenter() {
    StackPane results = new StackPane();
    results.getChildren().add(createButtonVbox());
    return results;
  }

  /**
   * Creates a VBox containing all the buttons for the main menu view.
   *
   * @return a Node object representing a VBox with all the main menu buttons.
   */
  private Node createButtonVbox() {
    VBox results = new VBox();
    results.getChildren().add(newGameButton);
    results.getChildren().add(loadGameButton);
    results.getChildren().add(storiesButton);
    results.getChildren().add(createStoryButton);
    results.getChildren().add(exitButton);
    results.getStyleClass().add("button-vbox");
    return results;
  }
}

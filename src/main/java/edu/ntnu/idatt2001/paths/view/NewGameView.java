package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

/**
 * Represents the new game view in the user interface. This includes a text field for player name
 * input, a button to start a new game, a ComboBox for story selection, a button to return to the
 * main menu, and a button to customize game options.
 */
public class NewGameView {

  @Getter
  private final BorderPane root;
  @Getter
  private final Button startNewGameButton;
  @Getter
  private final ComboBox<String> storySelect;
  @Getter
  private final Button goBackButton;
  @Getter
  private final TextField playerName;
  @Getter
  private final Button customizeGameOptionsButton;

  /**
   * Constructs a new game view. It initializes all the UI components, sets up their style classes,
   * and configures the layout of the view.
   */
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

  /**
   * Creates the root BorderPane for the new game view, with the center layout added.
   *
   * @return The root BorderPane for the new game view.
   */
  private BorderPane createRoot() {
    BorderPane root = new BorderPane();
    root.getStyleClass().add("main-menu");
    root.setCenter(createCenter());
    return root;
  }

  /**
   * Creates the center layout for the new game view, which contains a VBox of buttons.
   *
   * @return A Node representing the center layout for the new game view.
   */
  private Node createCenter() {
    StackPane centerLayout = new StackPane();
    centerLayout.getChildren().add(createButtonVBox());
    return centerLayout;
  }

  /**
   * Creates a VBox layout for the new game view, which contains the player name input field, "Start
   * New Game" button, story select combo box, "Customize Game Options" button, and "Go Back"
   * button.
   *
   * @return A Node representing the VBox layout for the new game view.
   */
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

  /**
   * Creates an HBox layout for the new game view, which contains a label and a ComboBox for story
   * selection.
   *
   * @return A Node representing the HBox layout for the new game view.
   */
  private Node createHBox() {
    HBox hboxLayout = new HBox();
    hboxLayout.getChildren().add(new Label("Select Story: "));
    hboxLayout.getChildren().add(storySelect);
    hboxLayout.getStyleClass().add("button-hbox");
    return hboxLayout;
  }
}

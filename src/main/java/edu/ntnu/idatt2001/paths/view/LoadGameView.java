package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

/**
 * This class represents the view for the Load Game screen of the Paths application. This screen
 * allows users to select a saved game from a dropdown menu and load it for playing. It also
 * provides a button for returning back to the previous screen.
 */
public class LoadGameView {

  @Getter private final ComboBox<String> saveSelect;
  @Getter private final Button loadButton;
  @Getter private final Button goBackButton;
  @Getter private final BorderPane root;

  /**
   * Constructs a new LoadGameView. This involves creating and styling all the nodes, and assembling
   * them into a layout.
   */
  public LoadGameView() {
    saveSelect = new ComboBox<>();
    saveSelect.getStyleClass().add("default-choice-box");

    loadButton = new Button("Load Game");
    loadButton.getStyleClass().add("default-button");

    goBackButton = new Button("Go Back");
    goBackButton.getStyleClass().add("default-button");

    root = createRoot();
  }

  /**
   * Creates the root node for this view. The root node is a BorderPane, which holds all other nodes
   * in this view.
   *
   * @return the created root node
   */
  private BorderPane createRoot() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("main-menu");
    results.setCenter(createCenter());
    return results;
  }

  /**
   * Creates the center node for the root BorderPane. This node is a StackPane, which holds a VBox
   * containing all the buttons.
   *
   * @return the created center node
   */
  private Node createCenter() {
    StackPane results = new StackPane();
    results.getChildren().add(createButtonVBox());
    return results;
  }

  /**
   * Creates the VBox that contains all the buttons. The buttons are added vertically in this VBox.
   *
   * @return the created VBox
   */
  private Node createButtonVBox() {
    VBox results = new VBox();
    results.getChildren().add(saveSelect);
    results.getChildren().add(loadButton);
    results.getChildren().add(goBackButton);
    results.getStyleClass().add("button-vbox");
    return results;
  }
}

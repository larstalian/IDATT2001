package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

/**
 * Represents the new game view in the user interface. This includes a text field for player name
 * input, a button to start a new game, a ComboBox for story selection, a button to return to the
 * main menu, and a button to customize game options.
 */
public class NewStoryView {

  @Getter private final Button newStoryButton;
  @Getter private final Button goBackButton;
  @Getter private final TextField storyName;
  @Getter private final TextField openingPassageText;
  @Getter private final Label infoLabel;
  @Getter private final BorderPane root;

  /**
   * Creates a new instance of the new game view, initializes the field variables and sets their
   * style class.
   */
  public NewStoryView() {
    newStoryButton = new Button("Create a new Story");
    newStoryButton.getStyleClass().add("default-button");

    goBackButton = new Button("Go Back");
    goBackButton.getStyleClass().add("default-button");

    storyName = new TextField();
    storyName.getStyleClass().add("default-text-field");

    openingPassageText = new TextField();
    openingPassageText.getStyleClass().add("default-text-field");

    infoLabel = new Label();
    infoLabel.getStyleClass().add("default-label");
    root = createRoot();
  }

  /**
   * Returns the root node of the new game view.
   *
   * @return a BorderPane representing the root node of the new game view.
   */
  private BorderPane createRoot() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("main-menu");
    results.setCenter(createCenter());
    return results;
  }

  /**
   * Creates the root node of the new game view.
   *
   * @return a BorderPane representing the root node of the new game view.
   */
  private Node createCenter() {
    VBox results = new VBox();
    results.getChildren().add(infoLabel);
    results.getChildren().add(storyName);
    results.getChildren().add(openingPassageText);
    results.getChildren().add(newStoryButton);
    results.getChildren().add(goBackButton);
    return results;
  }
}

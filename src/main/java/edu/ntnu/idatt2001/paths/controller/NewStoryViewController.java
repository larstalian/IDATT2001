package edu.ntnu.idatt2001.paths.controller;

import edu.ntnu.idatt2001.paths.model.story.Passage;
import edu.ntnu.idatt2001.paths.model.story.Story;
import edu.ntnu.idatt2001.paths.view.NewStoryView;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;

/**
 * Controller for the NewStoryView class. Manages the actions associated with each button and field
 * in the new story screen, including the creation of a new story with a provided name and opening
 * passage.
 */
public class NewStoryViewController {

  private final NewStoryView newStoryView;

  /**
   * Constructs a new story view controller. It initializes the NewStoryView and configures the
   * actions for the "Go Back" button, the story name field, the opening passage field, and the "New
   * Story" button.
   */
  public NewStoryViewController() {
    newStoryView = new NewStoryView();
    configureNewStoryButton();
    configureStoryNameTextField();
    configureOpeningPassageTextField();
    configureGoBackButton();
  }

  /**
   * Configure the behavior of the "Go Back" button. When pressed, the scene root changes to the
   * main menu view.
   */
  private void configureGoBackButton() {
    newStoryView
        .getGoBackButton()
        .setOnAction(
            event ->
                newStoryView
                    .getGoBackButton()
                    .getScene()
                    .setRoot(new MainMenuViewController().getRoot()));
  }

  /**
   * Configure the text prompt for the opening passage text field in the new story view.
   */
  private void configureOpeningPassageTextField() {
    newStoryView.getOpeningPassageText().setPromptText("Enter the opening passage content");
  }

  /**
   * Configure the text prompt for the story name field in the new story view.
   */
  private void configureStoryNameTextField() {
    newStoryView.getStoryName().setPromptText("Enter the story title");
  }

  /**
   * Configure the behavior of the "New Story" button. When pressed, it validates the inputs and if
   * they are valid, it creates a new Story object and changes the scene root to the create story
   * view. If the inputs are not valid, it updates the info label with an error message.
   */
  private void configureNewStoryButton() {
    Button newStoryButton = newStoryView.getNewStoryButton();
    newStoryButton.setOnAction(
        event -> {
          String storyName = newStoryView.getStoryName().getText();
          String openingPassageText = newStoryView.getOpeningPassageText().getText();
          if (storyName.isEmpty() || openingPassageText.isEmpty()) {
            newStoryView.getInfoLabel().setText("Please fill in all fields");
          } else {
            Story story = new Story(storyName, new Passage("Opening Passage", openingPassageText));
            newStoryButton.getScene().setRoot(new CreateStoryViewController(story).getRoot());
          }
        });
  }

  /**
   * Returns the root node of the NewStoryView for this controller, allowing it to be added to a
   * scene.
   *
   * @return the root node of the NewStoryView.
   */
  public Region getRoot() {
    return newStoryView.getRoot();
  }
}

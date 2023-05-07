package edu.ntnu.idatt2001.paths.controller;

import edu.ntnu.idatt2001.paths.model.story.Passage;
import edu.ntnu.idatt2001.paths.model.story.Story;
import edu.ntnu.idatt2001.paths.view.NewStoryView;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;

public class NewStoryViewController {

  private final NewStoryView newStoryView;

  public NewStoryViewController() {
    newStoryView = new NewStoryView();
    configureNewStoryButton();
    configureStoryNameTextField();
    configureOpeningPassageTextField();
    configureGoBackButton();
  }

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

  private void configureOpeningPassageTextField() {
    newStoryView.getOpeningPassageText().setPromptText("Enter the opening passage content");
  }

  private void configureStoryNameTextField() {
    newStoryView.getStoryName().setPromptText("Enter Story Name (file name)");
  }

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

  public Region getRoot() {
    return newStoryView.getRoot();
  }
}

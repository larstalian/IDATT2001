package edu.ntnu.idatt2001.paths.view;

import edu.ntnu.idatt2001.paths.model.filehandlers.json.StoryFileHandler;
import edu.ntnu.idatt2001.paths.model.story.Story;
import java.io.IOException;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Builder;
import org.apache.commons.io.FilenameUtils;

public class Stories implements Builder<Region> {

  private final Button goBackButton;
  private final Button getBrokenLinksButton;
  private final ChoiceBox<String> storySelect;
  private Story loadedStory;
  private final BorderPane root;


  public Stories() {
    goBackButton  = new Button("Back");
    storySelect = new ChoiceBox<>();
    storySelect.getItems().addAll(StoryFileHandler.getSavedStories());
    storySelect.getSelectionModel().selectFirst();
    getBrokenLinksButton = new Button("See broken links");
    root = new BorderPane();
    loadInitialStory();
  }

  private void loadInitialStory() {
    try {
      loadedStory = new StoryFileHandler().loadStoryFromFile(storySelect.getValue());
    } catch (Exception e) {
      Widgets.createAlert("Error", "Error", "Error loading the story, check for errors in the story file\n" + e.getMessage()).showAndWait();
    }
  }

  @Override
  public Region build() {
    root.getStyleClass().add("main-menu");
    root.setTop(createTop());
    root.setCenter(createCenter());
    configureBackButton();
    return root;
  }

  private Node createTop() {
    VBox results = new VBox();
    results.getStyleClass().add("story-info-top");
    results.getChildren().add(new Label("Select a Story file"));
    results.getChildren().add(storySelect);
    configureStorySelect();
    return results;
  }

  private void configureStorySelect() {
    storySelect.setOnAction(
            event -> {
              try {
                loadedStory = new StoryFileHandler().loadStoryFromFile(storySelect.getValue());
                updateStoryInfo();
              } catch (IOException e) {
                Widgets.createAlert("Error", "Error loading the story, check for errors in the story file", "Detailed error message:\n\n" + e.getMessage()).showAndWait();
              }
            });
  }

  private void configureBackButton() {
    goBackButton.getStyleClass().add("story-info-button");
    goBackButton.setOnAction(
            event -> {
              Region mainMenuRoot = new MainMenu().build();
              goBackButton.getScene().setRoot(mainMenuRoot);
            });
  }

  private Node createCenter() {
    StackPane results = new StackPane();
    VBox storyInfoVBox = new VBox();
    storyInfoVBox.getStyleClass().add("story-info-vbox");
    storyInfoVBox.getChildren().add(goBackButton);
    storyInfoVBox.getChildren().add(createStoryInfo());
    results.getChildren().add(storyInfoVBox);
    return results;
  }


  private boolean isStoryValid() {
    return loadedStory != null;
  }

  private Node createStoryInfo() {
    VBox results = new VBox();
    results.getStyleClass().add("story-info-label");
    if (isStoryValid()) {
      results.getChildren().add(validStory());
      results.getChildren().add(storyFileType());
      results.getChildren().add(numberOfPassages());
      results.getChildren().add(brokenLinks());
      results.getChildren().add(customMediaFiles());
    }
    else {
      results.getChildren().add(new Label("The selected story is not valid"));
    }
    return results;
  }

  private Node customMediaFiles() {
    HBox results = new HBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(new Label("Custom Media Files: "));
    int numberOfCustomImages = StoryFileHandler.getCustomImageFiles(loadedStory.getTitle()).size();
    int numberOfCustomSound = StoryFileHandler.getCustomSoundFiles(loadedStory.getTitle()).size();
    int numberOfBrokenFiles = StoryFileHandler.getBrokenFiles(loadedStory).size();
    if (numberOfCustomImages > 0){
      results.getChildren().add(new Label("Images: "));
      results.getChildren().add(new Label(String.valueOf(numberOfCustomImages)));
    }
    if (numberOfCustomSound > 0){
      results.getChildren().add(new Label("Sound: "));
      results.getChildren().add(new Label(String.valueOf(numberOfCustomSound)));
    }
    if (numberOfBrokenFiles > 0){
      results.getChildren().add(new Label("Broken Files: "));
      results.getChildren().add(new Label(String.valueOf(numberOfBrokenFiles)));
    }
    if (numberOfCustomSound == 0 && numberOfCustomImages == 0){
      results.getChildren().add(new Label("None"));
    }

    return results;
  }

  private Node brokenLinks() {
    HBox results = new HBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(new Label("Broken Links: "));
    results.getChildren().add(new Label(String.valueOf(loadedStory.getBrokenLinks().size())));
    if (loadedStory.getBrokenLinks().size() > 0){
      results.getChildren().add(getBrokenLinksButton);
      getBrokenLinksButton.getStyleClass().add("story-info-button");
      getBrokenLinksButton.setOnAction(event -> createBrokenLinksInfo());
    }
    return results;
  }private void createBrokenLinksInfo() {
    String brokenLinks = loadedStory.getBrokenLinks().stream().map(link -> link + "\n").collect(Collectors.joining());
    Widgets.createAlert("Broken Links", "List of broken Links:", brokenLinks).showAndWait();
  }

  private Node numberOfPassages() {
    HBox results = new HBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(new Label("Number of passages: "));
    results.getChildren().add(new Label(String.valueOf(loadedStory.getPassages().size())));
    return results;
  }

  private Node storyFileType() {
    HBox results = new HBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(new Label("Story File Type: "));
    results.getChildren().add(new Label(FilenameUtils.getExtension(storySelect.getValue())));
    return results;
  }private Node validStory() {
    HBox results = new HBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(new Label("Valid Story: "));
    try {
      StoryFileHandler storyFileHandler = new StoryFileHandler();
      storyFileHandler.loadStoryFromFile(storySelect.getValue());
      results.getChildren().add(new Label("Yes"));
    } catch (Exception e) {
      results.getChildren().add(new Label("No"));
      e.printStackTrace();
    }
    return results;
  }

  private void updateStoryInfo() {
    root.setCenter(createCenter());
  }
}
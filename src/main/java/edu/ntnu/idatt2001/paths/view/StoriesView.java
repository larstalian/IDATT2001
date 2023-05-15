package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class StoriesView {

  @Getter private final Button goBackButton;
  @Getter private final Button getBrokenLinksButton;
  @Getter private final ComboBox<String> storySelect;
  @Getter private final BorderPane root;
  @Getter private final Button convertToJsonButton;
  @Getter private final Button convertToPathsButton;
  @Getter private final Label validStoryLabel;
  @Getter private final Label storyFileInfoLabel;
  @Getter private final Label numberOfPassagesLabel;
  @Getter private final Label brokenLinksLabel;
  @Getter private final Label customImagesLabel;
  @Getter private final Label customSoundsLabel;
  @Getter private final Label brokenFilesLabel;
  @Getter private final Label convertInfoLabel;
  @Getter private final Button editStoryButton;

  public StoriesView() {
    goBackButton = new Button("Back");
    goBackButton.getStyleClass().add("default-button");

    storySelect = new ComboBox<>();
    storySelect.getStyleClass().add("default-choice-box");

    convertToJsonButton = new Button("Convert to Json");
    convertToJsonButton.getStyleClass().add("default-button");

    convertToPathsButton = new Button("Convert to Paths");
    convertToPathsButton.getStyleClass().add("default-button");

    getBrokenLinksButton = new Button("See broken links");
    getBrokenLinksButton.getStyleClass().add("default-button");

    editStoryButton = new Button("Edit Story");
    editStoryButton.getStyleClass().add("default-button");

    brokenLinksLabel = new Label();
    storyFileInfoLabel = new Label();
    numberOfPassagesLabel = new Label();
    validStoryLabel = new Label();
    customImagesLabel = new Label();
    customSoundsLabel = new Label();
    brokenFilesLabel = new Label();
    convertInfoLabel = new Label();

    root = createRoot();
  }

  private BorderPane createRoot() {
    BorderPane root = new BorderPane();
    root.getStyleClass().add("main-menu");
    root.setTop(createTop());
    root.setCenter(createCenter());
    return root;
  }

  private Node createTop() {
    VBox results = new VBox();
    results.getStyleClass().add("story-info-top");
    results.getChildren().add(new Label("Select a Story file"));
    results.getChildren().add(storySelect);
    return results;
  }

  private Node createStoryInfo() {
    VBox results = new VBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(createValidStoryInfo());
    results.getChildren().add(createStoryFileInfo());
    results.getChildren().add(createNumberOfPassagesInfo());
    results.getChildren().add(createNumberOfBrokenLinksInfo());
    results.getChildren().add(createMediaFilesInfo());
    return results;
  }

  private Node createNumberOfPassagesInfo() {
    HBox results = new HBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(new Label("Number of passages: "));
    results.getChildren().add(numberOfPassagesLabel);
    return results;
  }

  private Node createCenter() {
    VBox centerInfo = new VBox();
    centerInfo.getStyleClass().add("story-info-vbox");
    centerInfo.getChildren().add(createStoryInfo());
    centerInfo.getChildren().add(createConvertStoryInfo());
    centerInfo.getChildren().add(createEditStoryButton());
    centerInfo.getChildren().add(goBackButton);
    return centerInfo;
  }

  private Node createEditStoryButton() {
    VBox results = new VBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(editStoryButton);
    return results;
  }

  private Node createMediaFilesInfo() {
    HBox results = new HBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(new Label("Custom Media Files: "));
    results.getChildren().add(customImagesLabel);
    results.getChildren().add(customSoundsLabel);
    results.getChildren().add(brokenFilesLabel);

    return results;
  }

  private Node createNumberOfBrokenLinksInfo() {
    HBox results = new HBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(new Label("Broken Links: "));
    results.getChildren().add(brokenLinksLabel);
    results.getChildren().add(getBrokenLinksButton);
    return results;
  }

  private Node createStoryFileInfo() {
    HBox results = new HBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(new Label("Story File Type: "));
    results.getChildren().add(storyFileInfoLabel);
    return results;
  }

  private Node createValidStoryInfo() {
    HBox results = new HBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(new Label("Valid Story: "));
    results.getChildren().add(validStoryLabel);
    return results;
  }

  private Node createConvertStoryInfo() {
    VBox convertStoryVBox = new VBox();
    convertStoryVBox
        .getChildren()
        .addAll(convertInfoLabel, convertToJsonButton, convertToPathsButton);
    convertStoryVBox.getStyleClass().add("story-info-label");
    return convertStoryVBox;
  }
}

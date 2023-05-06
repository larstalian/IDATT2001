package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StoriesView {

  @lombok.Getter private final Button goBackButton;
  @lombok.Getter private final Button getBrokenLinksButton;
  @lombok.Getter private final ChoiceBox<String> storySelect;
  @lombok.Getter private final BorderPane root;
  @lombok.Getter private final Button convertToJsonButton;
  @lombok.Getter private final Button convertToPathsButton;
  @lombok.Getter private final Label validStoryLabel;
  @lombok.Getter private final Label storyFileInfoLabel;
  @lombok.Getter private final Label numberOfPassagesLabel;
  @lombok.Getter private final Label brokenLinksLabel;
  @lombok.Getter private final Label customImagesLabel;
  @lombok.Getter private final Label customSoundsLabel;
  @lombok.Getter private final Label brokenFilesLabel;
  @lombok.Getter private final Label convertInfoLabel;

  public StoriesView() {
    goBackButton = new Button("Back");
    storySelect = new ChoiceBox<>();
    convertToJsonButton = new Button("Convert to Json");
    convertToPathsButton = new Button("Convert to Paths");
    getBrokenLinksButton = new Button("See broken links");
    getBrokenLinksButton.setDisable(true);
    brokenLinksLabel = new Label();
    storyFileInfoLabel = new Label();
    numberOfPassagesLabel = new Label();
    validStoryLabel = new Label();
    customImagesLabel = new Label();
    customSoundsLabel = new Label();
    brokenFilesLabel = new Label();

    convertInfoLabel = new Label();

    root = new BorderPane();
    root.getStyleClass().add("main-menu");
    root.setTop(createTop());
    root.setCenter(createCenter());
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
    centerInfo.getChildren().add(goBackButton);
    centerInfo.getChildren().add(createStoryInfo());
    centerInfo.getChildren().add(createConvertStoryInfo());
    return centerInfo;
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

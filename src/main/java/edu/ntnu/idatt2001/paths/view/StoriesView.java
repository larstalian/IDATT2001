package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StoriesView {
  private final Button goBackButton;
  private final Button getBrokenLinksButton;
  private final ChoiceBox<String> storySelect;
  private final BorderPane root;
  private final Button convertToJsonButton;
  private final Button convertToPathsButton;
  private final Label validStoryLabel;
  private final Label storyFileInfoLabel;
  private final Label numberOfPassagesLabel;
  private final Label brokenLinksLabel;
  private final Label customImagesLabel;
  private final Label customSoundsLabel;
  private final Label brokenFilesLabel;
  private final Label convertInfoLabel;

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

  public Label getConvertInfoLabel() {
    return convertInfoLabel;
  }

  public BorderPane getRoot() {
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

  public Button getGoBackButton() {
    return goBackButton;
  }

  public Button getGetBrokenLinksButton() {
    return getBrokenLinksButton;
  }

  public ChoiceBox<String> getStorySelect() {
    return storySelect;
  }

  public Button getConvertToJsonButton() {
    return convertToJsonButton;
  }

  public Button getConvertToPathsButton() {
    return convertToPathsButton;
  }

  public Label getValidStoryLabel() {
    return validStoryLabel;
  }

  public Label getStoryFileInfoLabel() {
    return storyFileInfoLabel;
  }

  public Label getNumberOfPassagesLabel() {
    return numberOfPassagesLabel;
  }

  public Label getBrokenLinksLabel() {
    return brokenLinksLabel;
  }

  public Label getCustomImagesLabel() {
    return customImagesLabel;
  }

  public Label getCustomSoundsLabel() {
    return customSoundsLabel;
  }

  public Label getBrokenFilesLabel() {
    return brokenFilesLabel;
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

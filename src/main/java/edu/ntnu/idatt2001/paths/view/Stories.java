package edu.ntnu.idatt2001.paths.view;

import edu.ntnu.idatt2001.paths.model.filehandlers.json.StoryFileHandler;
import edu.ntnu.idatt2001.paths.model.filehandlers.paths.StoryFileReader;
import edu.ntnu.idatt2001.paths.model.filehandlers.paths.StoryFileWriter;
import edu.ntnu.idatt2001.paths.model.story.Mood;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import edu.ntnu.idatt2001.paths.model.story.Story;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.*;
import javafx.util.Builder;
import org.apache.commons.io.FilenameUtils;

public class Stories implements Builder<Region> {

  private final Button goBackButton;
  private final Button getBrokenLinksButton;
  private final ChoiceBox<String> storySelect;
  private final BorderPane root;
  private final Button convertToJsonButton;
  private final Button convertToPathsButton;
  private Story loadedStory;

  public Stories() {
    goBackButton = new Button("Back");
    storySelect = new ChoiceBox<>();
    storySelect.getItems().addAll(StoryFileHandler.getSavedStories());
    storySelect.getItems().addAll(StoryFileReader.getSavedStories());
    storySelect.getSelectionModel().selectFirst();
    convertToJsonButton = new Button("Convert to Json");
    convertToPathsButton = new Button("Convert to Paths");
    getBrokenLinksButton = new Button("See broken links");
    root = new BorderPane();
    loadInitialStory();
  }

  private void loadInitialStory() {
    String fileName = storySelect.getValue();
    try {
      if (FilenameUtils.isExtension(fileName, "json")) {
        loadedStory = new StoryFileHandler().loadStoryFromFile(storySelect.getValue());
      } else if (FilenameUtils.isExtension(fileName, "paths")) {
        loadedStory = StoryFileReader.readStoryFromFile(fileName);
      }
    } catch (Exception e) {
      Widgets.createAlert(
              "Error",
              "Error",
              "Error loading the story, check for errors in the story file\n" + e.getMessage())
          .showAndWait();
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
          String fileName = storySelect.getValue();
          try {
            if (FilenameUtils.isExtension(fileName, "json")) {
              loadedStory = new StoryFileHandler().loadStoryFromFile(storySelect.getValue());
            } else if (FilenameUtils.isExtension(fileName, "paths")) {
              loadedStory = StoryFileReader.readStoryFromFile(fileName);
            }
            updateStoryInfo();
          } catch (IOException | ParseException e) {
            Widgets.createAlert(
                    "Error",
                    "Error loading the story, check for errors in the story file",
                    "Detailed error message:\n\n" + e.getMessage())
                .showAndWait();
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
    HBox results = new HBox();
    results.getStyleClass().add("story-info-vbox");
    VBox storyInfoVBox = new VBox();
    storyInfoVBox.getChildren().add(createStoryInfo());
    storyInfoVBox.getChildren().add(goBackButton);
    results.getChildren().add(storyInfoVBox);
    results.getChildren().add(createConvertStoryInfo());
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
    } else {
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
    if (numberOfCustomImages > 0) {
      results.getChildren().add(new Label("Images: "));
      results.getChildren().add(new Label(String.valueOf(numberOfCustomImages)));
    }
    if (numberOfCustomSound > 0) {
      results.getChildren().add(new Label("Sound: "));
      results.getChildren().add(new Label(String.valueOf(numberOfCustomSound)));
    }
    if (numberOfBrokenFiles > 0) {
      results.getChildren().add(new Label("Broken Files: "));
      results.getChildren().add(new Label(String.valueOf(numberOfBrokenFiles)));
    }
    if (numberOfCustomSound == 0 && numberOfCustomImages == 0) {
      results.getChildren().add(new Label("None"));
    }

    return results;
  }

  private Node brokenLinks() {
    HBox results = new HBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(new Label("Broken Links: "));
    results.getChildren().add(new Label(String.valueOf(loadedStory.getBrokenLinks().size())));
    if (loadedStory.getBrokenLinks().size() > 0) {
      results.getChildren().add(getBrokenLinksButton);
      getBrokenLinksButton.getStyleClass().add("story-info-button");
      getBrokenLinksButton.setOnAction(event -> createBrokenLinksInfo());
    }
    return results;
  }

  private void createBrokenLinksInfo() {
    String brokenLinks =
        loadedStory.getBrokenLinks().stream()
            .map(link -> link + "\n")
            .collect(Collectors.joining());
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
  }

  private Node validStory() {
    HBox results = new HBox();
    results.getStyleClass().add("story-info-label");
    results.getChildren().add(new Label("Valid Story: "));
    try {
      if (storySelect.getValue().endsWith(".json")) {
        StoryFileHandler storyFileHandler = new StoryFileHandler();
        storyFileHandler.loadStoryFromFile(storySelect.getValue());
      } else if (storySelect.getValue().endsWith(".paths")) {
        StoryFileReader.readStoryFromFile(storySelect.getValue());
      }
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

  private Node createConvertStoryInfo() {
    VBox results = new VBox();
    String selectedFile = storySelect.getValue();
    if (selectedFile.endsWith(".paths")) {
      results.getChildren().add(createPathsConvertInfo());

    }
    if (selectedFile.endsWith(".json")) {
      results.getChildren().add(createJsonConvertInfo());
    }
    results.getStyleClass().add("story-info-label");
    return results;
  }

  private Node createJsonConvertInfo() {
    VBox results = new VBox();
    String info =
        """
        The selected story is in .json format.
        This is the recommended file format
        and supports all current features.
        If you wish convert the story back to
        a paths format so editing the story
        will be easier.
       """;
    results.getChildren().add(new Label(info));
    results.getChildren().add(convertToPathsButton);
    configureConvertToPathsButton();
    results.getStyleClass().add("story-info-label");
    return results;
  }

  private Node createPathsConvertInfo() {
    VBox results = new VBox();
    String info =
        """
        The selected story is in a .paths format.
        This story-format is easy to edit by
        opening the file in a text editor.
        However, it does not support
        additional features other than custom
        sounds and images. You can convert the
        story format to a json format here to
        support additional features. Read the
        .README file to see all features.""";

    results.getChildren().add(new Label(info));
    results.getChildren().add(convertToJsonButton);
    configureConvertToJsonButton();
    results.getStyleClass().add("story-info-label");
    return results;
  }

  private void configureConvertToJsonButton() {
    convertToJsonButton.setOnAction(
        event -> {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setHeaderText("Do you wish to add mood enums to the story?");

          ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
          ButtonType noButton = new ButtonType("No", ButtonData.NO);
          alert.getButtonTypes().setAll(yesButton, noButton);

          Optional<ButtonType> result = alert.showAndWait();
          if (result.isPresent() && result.get() == yesButton) {
            addEnums();
          }
          try {
            StoryFileHandler storyFileHandler = new StoryFileHandler();
            storyFileHandler.saveStoryToFile(loadedStory);
            Widgets.createAlert(
                    "Success",
                    "Story converted",
                    "Story converted successfully and can be now found in the json directory")
                .showAndWait();
          } catch (IOException e) {
            Widgets.createAlert("Error", "Error converting story", e.getMessage()).showAndWait();
          }
        });
  }


  private void addEnums() {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Add Mood Enums");
    dialog.setHeaderText("Select a Mood Enum for each passage:");

    GridPane grid = new GridPane();
    grid.getStyleClass().add("dialog-grid");

    List<ChoiceBox<Mood>> moodComboBoxes = new ArrayList<>();
    List<Passage> passagesList = new ArrayList<>(loadedStory.getPassages());

    IntStream.range(0, passagesList.size()).forEach(i -> {
      grid.add(new Label(passagesList.get(i).getTitle()), 0, i);
      ChoiceBox<Mood> choiceBox = new ChoiceBox<>();
      choiceBox.getItems().addAll(Mood.values());
      choiceBox.setValue(Mood.NONE);
      moodComboBoxes.add(choiceBox);
      grid.add(choiceBox, 1, i);
    });

    dialog.getDialogPane().setContent(grid);
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    Optional<ButtonType> result = dialog.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      for (int i = 0; i < moodComboBoxes.size(); i++) {
        ChoiceBox<Mood> choiceBox = moodComboBoxes.get(i);
        Passage passage = passagesList.get(i);
        passage.setMood(choiceBox.getValue());
      }
    }
  }



  private void configureConvertToPathsButton() {
    convertToPathsButton.setOnAction(event -> {
      Alert alert = convertWarning();
      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) {
        try {
          StoryFileWriter.saveStoryToFile(loadedStory);
          Widgets.createAlert("Success", "Story converted", "Story converted successfully and can be now found in the paths directory").showAndWait();
        } catch (IOException e) {
          Widgets.createAlert("Error", "Error converting story", e.getMessage()).showAndWait();
        }
      }
    });
  }


  private Alert convertWarning() {
   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Warning");
    alert.setHeaderText("Wait!");
    alert.setContentText("Additional features, other than custom images and sound will be deleted and must be re-added when converting back to Json");
    return alert;
  }
}

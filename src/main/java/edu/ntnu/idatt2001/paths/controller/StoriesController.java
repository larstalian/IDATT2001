package edu.ntnu.idatt2001.paths.controller;

import edu.ntnu.idatt2001.paths.model.filehandlers.json.StoryFileHandler;
import edu.ntnu.idatt2001.paths.model.filehandlers.paths.StoryFileReader;
import edu.ntnu.idatt2001.paths.model.filehandlers.paths.StoryFileWriter;
import edu.ntnu.idatt2001.paths.model.story.Mood;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import edu.ntnu.idatt2001.paths.model.story.Story;
import edu.ntnu.idatt2001.paths.view.StoriesView;
import edu.ntnu.idatt2001.paths.view.util.Widgets;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import org.apache.commons.io.FilenameUtils;

public class StoriesController {

  private final StoriesView storiesView;
  private Story loadedStory;

  public StoriesController() {
    storiesView = new StoriesView();
    configureBackButton();
    configureStorySelect();
    configureConvertToJsonButton();
    configureConvertToPathsButton();
  }

  public Region getRoot() {
    return storiesView.getRoot();
  }

  private void configureStorySelect() {
    storiesView.getStorySelect().getItems().addAll(StoryFileHandler.getSavedStories());
    storiesView.getStorySelect().getItems().addAll(StoryFileReader.getSavedStories());
    storiesView
        .getStorySelect()
        .setOnAction(
            event -> {
              String fileName = storiesView.getStorySelect().getValue();
              if (fileName != null) {

                try {
                  if (FilenameUtils.isExtension(fileName, "json")) {
                    loadedStory =
                        new StoryFileHandler()
                            .loadStoryFromFile(storiesView.getStorySelect().getValue());

                    storiesView.getConvertToJsonButton().setVisible(false);
                    storiesView.getConvertToPathsButton().setVisible(true);

                  } else if (FilenameUtils.isExtension(fileName, "paths")) {
                    loadedStory = StoryFileReader.readStoryFromFile(fileName);
                    storiesView.getConvertToJsonButton().setVisible(true);
                    storiesView.getConvertToPathsButton().setVisible(false);
                  }
                  updateValidStoryInfo();
                  updateStoryFileInfo();
                  updateNumberOfPassages();
                  updateBrokenLinks();
                  updateMediaFilesLabel();
                  updateMediaFilesLabel();
                  updateConvertStoryInfo();
                } catch (IOException | ParseException e) {
                  Widgets.createAlert(
                          "Error",
                          "Error loading the story, check for errors in the story file",
                          "Detailed error message:\n\n" + e.getMessage())
                      .showAndWait();
                }
              }
            });
  }

  private Alert onBrokenLinksButtonClick() {
    String brokenLinks =
        loadedStory.getBrokenLinks().stream()
            .map(link -> link + "\n")
            .collect(Collectors.joining());
    return Widgets.createAlert("Broken links", "The following links are broken", brokenLinks);
  }

  private void updateBrokenLinks() {
    storiesView
        .getBrokenLinksLabel()
        .setText(String.valueOf(loadedStory.getBrokenLinks().size()));
    if (loadedStory.getBrokenLinks().size() > 0) {
      storiesView.getGetBrokenLinksButton().setDisable(false);
      storiesView
          .getGetBrokenLinksButton()
          .setOnAction(event -> onBrokenLinksButtonClick().showAndWait());
    } else {
      storiesView.getGetBrokenLinksButton().setDisable(true);
    }
  }

  private void updateStoryFileInfo() {
    storiesView
        .getStoryFileInfoLabel()
        .setText(FilenameUtils.getExtension(storiesView.getStorySelect().getValue()));
  }

  public void updateConvertStoryInfo() {
    String selectedFile = storiesView.getStorySelect().getValue();
    if (selectedFile == null) {
      Widgets.createAlert(
              "Error",
              "There are no stories",
              "Please check that the stories are in the correct file path, see the .README for more info.")
          .showAndWait();
      storiesView.getConvertInfoLabel().setText("");
      return;
    }
    if (selectedFile.endsWith(".paths")) {
      updatePathsConvertInfo();
    }
    if (selectedFile.endsWith(".json")) {
      updateJsonConvertInfo();
    }
  }

  private void updateValidStoryInfo() {
    try {
      if (storiesView.getStorySelect().getValue().endsWith(".json")) {
        StoryFileHandler storyFileHandler = new StoryFileHandler();
        storyFileHandler.loadStoryFromFile(storiesView.getStorySelect().getValue());
      } else if (storiesView.getStorySelect().getValue().endsWith(".paths")) {
        StoryFileReader.readStoryFromFile(storiesView.getStorySelect().getValue());
      }
      storiesView.getValidStoryLabel().setText("Yes");
    } catch (Exception e) {
      storiesView.getValidStoryLabel().setText("No");
      e.printStackTrace();
    }
  }

  private void updateJsonConvertInfo() {
    String info =
        """
                The selected story is in .json format.
                This is the recommended file format
                and supports all current features.
                If you wish convert the story back to
                a paths format so editing the story
                will be easier.
               """;
    storiesView.getConvertInfoLabel().setText(info);
  }

  private void updatePathsConvertInfo() {
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

    storiesView.getConvertInfoLabel().setText(info);
  }

  private void configureConvertToJsonButton() {
    storiesView.getConvertToJsonButton().setVisible(false);
    storiesView
        .getConvertToJsonButton()
        .setOnAction(
            event -> {
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
              alert.setHeaderText("Do you wish to add additional features to the story?");

              ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
              ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
              alert.getButtonTypes().setAll(yesButton, noButton);

              Optional<ButtonType> result = alert.showAndWait();
              if (result.isPresent() && result.get() == yesButton) {
                createValues();
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
                Widgets.createAlert("Error", "Error converting story", e.getMessage())
                    .showAndWait();
              }
            });
  }

  private void createValues() {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Edit passages");
    dialog.setHeaderText("Select moods and define if the \npassage is single visit only.");

    GridPane grid = new GridPane();
    grid.getStyleClass().add("dialog-grid");
    grid.setHgap(5);

    List<ChoiceBox<Mood>> moodComboBoxes = new ArrayList<>();
    List<ChoiceBox<String>> singleVisitComboBox = new ArrayList<>();
    List<Passage> passagesList = new ArrayList<>(loadedStory.getPassages());
    passagesList.add(0, loadedStory.getOpeningPassage());
    grid.add(new Label("Passage"), 0, 0);
    grid.add(new Label("Mood"), 1, 0);
    grid.add(new Label("Single Visit"), 2, 0);

    IntStream.range(0, passagesList.size())
        .forEach(
            i -> {
              int rowIndex = i + 1;
              grid.add(new Label(passagesList.get(i).getTitle()), 0, rowIndex);
              ChoiceBox<Mood> moodChoiceBox = new ChoiceBox<>();
              moodChoiceBox.getItems().addAll(Mood.values());
              moodChoiceBox.setValue(Mood.NONE);
              moodComboBoxes.add(moodChoiceBox);
              grid.add(moodChoiceBox, 1, rowIndex);

              ChoiceBox<String> singleVisitChoiceBox = new ChoiceBox<>();
              singleVisitChoiceBox.getItems().addAll("Yes", "No");
              singleVisitChoiceBox.setValue("No");
              singleVisitComboBox.add(singleVisitChoiceBox);
              grid.add(singleVisitChoiceBox, 2, rowIndex);
            });

    dialog.getDialogPane().setContent(grid);
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    Optional<ButtonType> result = dialog.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      for (int i = 0; i < moodComboBoxes.size(); i++) {
        ChoiceBox<Mood> moodChoiceBox = moodComboBoxes.get(i);
        Passage passage = passagesList.get(i);
        passage.setMood(moodChoiceBox.getValue());

        ChoiceBox<String> singleVisitChoiceBox = singleVisitComboBox.get(i);
        passage.setSingleVisitOnly(singleVisitChoiceBox.getValue().equals("Yes"));
      }
    }
  }

  private void configureConvertToPathsButton() {
    storiesView.getConvertToPathsButton().setVisible(false);
    storiesView
        .getConvertToPathsButton()
        .setOnAction(
            event -> {
              Alert alert = convertWarning();
              Optional<ButtonType> result = alert.showAndWait();
              if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                  StoryFileWriter.saveStoryToFile(loadedStory);
                  Widgets.createAlert(
                          "Success",
                          "Story converted",
                          "Story converted successfully and can be now found in the paths directory")
                      .showAndWait();
                } catch (IOException e) {
                  Widgets.createAlert("Error", "Error converting story", e.getMessage())
                      .showAndWait();
                }
              }
            });
  }

  private void updateNumberOfPassages() {
    storiesView
        .getNumberOfPassagesLabel()
        .setText(String.valueOf(loadedStory.getPassages().size()));
  }

  private Alert convertWarning() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Warning");
    alert.setHeaderText("Wait!");
    alert.setContentText(
        "Additional features, other than custom images and sound will be deleted and must be re-added when converting back to Json");
    return alert;
  }

  private void configureBackButton() {
    storiesView.getGoBackButton().getStyleClass().add("story-info-button");
    storiesView
        .getGoBackButton()
        .setOnAction(
            event -> {
              Region mainMenuRoot = new MainMenuController().getRoot();
              storiesView.getGoBackButton().getScene().setRoot(mainMenuRoot);
            });
  }

  public void updateMediaFilesLabel() {
    int numberOfCustomImages = StoryFileHandler.getCustomImageFiles(loadedStory.getTitle()).size();
    int numberOfCustomSound = StoryFileHandler.getCustomSoundFiles(loadedStory.getTitle()).size();
    int numberOfBrokenFiles = StoryFileHandler.getBrokenFiles(loadedStory).size();

    if (numberOfCustomImages > 0) {
      storiesView.getCustomImagesLabel().setText("Images: " + numberOfCustomImages);
    } else {
      storiesView.getCustomImagesLabel().setText("");
    }

    if (numberOfCustomSound > 0) {
      storiesView.getCustomSoundsLabel().setText("Sound: " + numberOfCustomSound);
    } else {
      storiesView.getCustomSoundsLabel().setText("");
    }

    if (numberOfBrokenFiles > 0) {
      storiesView.getBrokenFilesLabel().setText("Broken Files: " + numberOfBrokenFiles);
    } else {
      storiesView.getBrokenFilesLabel().setText("");
    }

    if (numberOfCustomSound == 0 && numberOfCustomImages == 0) {
      storiesView.getCustomImagesLabel().setText("None");
      storiesView.getCustomSoundsLabel().setText("");
    }
  }
}

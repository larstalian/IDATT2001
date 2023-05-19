package edu.ntnu.idatt2001.paths.controller;

import edu.ntnu.idatt2001.paths.model.actions.Action;
import edu.ntnu.idatt2001.paths.model.filehandlers.factories.ActionFactory;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.StoryFileHandler;
import edu.ntnu.idatt2001.paths.model.story.Link;
import edu.ntnu.idatt2001.paths.model.story.Mood;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import edu.ntnu.idatt2001.paths.model.story.Story;
import edu.ntnu.idatt2001.paths.view.CreateStoryView;
import edu.ntnu.idatt2001.paths.view.util.Widgets;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Controller for creating stories in the user interface. This class manages the interactions
 * between the UI and the Story model. It configures the buttons for adding and deleting passages,
 * adding and deleting actions, saving the story, exiting the story creation, editing a passage, and
 * deleting a link. It also manages drag and drop functionality and the updating of views.
 */
public class CreateStoryViewController {
  private final ObservableList<Passage> passages;
  private final CreateStoryView createStoryView;
  private final Story story;
  private Passage selectedPassage;
  private Link selectedLink;
  private Action selectedAction;

  /**
   * Constructs a CreateStoryViewController for a specified story. It initializes the
   * createStoryView, sets up the passages list, and configures the buttons and listeners for the
   * user interface.
   *
   * @param chosenStory The story to create a view controller for.
   */
  public CreateStoryViewController(Story chosenStory) {
    story = chosenStory;
    createStoryView = new CreateStoryView();
    passages = FXCollections.observableArrayList(story.getPassages());
    passages.add(0, story.getOpeningPassage());
    configureAddPassageButton();
    configureDeletePassageButton();
    addPassagesListener();
    configureDragAndDrop();
    configureAddActionButton();
    configureDeleteActionButton();
    configureSaveStoryButton();
    configureExitButton();
    configureEditPassageButton();
    configureDeleteLinkButton();
  }

  /**
   * Configures the Delete Link Button. On action, removes the selected link from the selected
   * passage. If no link is selected, an error alert is shown.
   */
  private void configureDeleteLinkButton() {
    Button deleteLinkButton = createStoryView.getDeleteLinkButton();
    deleteLinkButton.setOnAction(
        event -> {
          if (selectedLink != null) {
            selectedPassage.getLinks().remove(selectedLink);
            createStoryView
                .getLinksView()
                .getItems()
                .remove(new Passage(selectedLink.getRef(), "dummy text"));
          } else {
            Widgets.createAlert("Error", "No link selected", "Please select a link to delete");
          }
        });
  }

  /**
   * Configures the Edit Passage Button. On action, if a passage is selected, opens a dialog to edit
   * the passage. If no passage is selected, an error alert is shown.
   */
  private void configureEditPassageButton() {
    Button editPassageButton = createStoryView.getEditPassageButton();
    editPassageButton.setOnAction(
        event -> {
          if (selectedPassage != null) {
            showEditPassageDialog(selectedPassage);
          } else {
            Widgets.createAlert("Error", "No passage selected", "Please select a passage to edit");
          }
        });
  }

  /**
   * Opens a dialog to edit a selected passage. Allows for modification of passage content, mood,
   * and single visit property. If an invalid input is provided, an error alert is shown.
   *
   * @param passage The passage to be edited.
   */
  private void showEditPassageDialog(Passage passage) {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Edit Passage");
    dialog.setHeaderText("Edit Passage");
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    TextArea contentTextArea = new TextArea();
    contentTextArea.setPromptText("Enter passage content");
    contentTextArea.setText(passage.getContent());

    ComboBox<Mood> moodChoiceBox = new ComboBox<>();
    moodChoiceBox.getItems().addAll(Mood.values());
    moodChoiceBox.setPromptText("Select mood");
    moodChoiceBox.setValue(passage.getMood());

    Label singleVisitLabel = new Label("Support a single visit only?");
    CheckBox isSingleVisit = new CheckBox();
    isSingleVisit.setSelected(passage.isSingleVisitOnly());
    HBox singleVisitHbox = new HBox(singleVisitLabel, isSingleVisit);

    VBox vBox = new VBox();
    vBox.getChildren().addAll(contentTextArea, moodChoiceBox, singleVisitHbox);

    dialog.getDialogPane().setContent(vBox);

    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == ButtonType.OK) {
            try {
              passage.setContent(contentTextArea.getText());
              passage.setMood(moodChoiceBox.getValue());
              passage.setSingleVisitOnly(isSingleVisit.isSelected());
              updateLinksViewAndPassageInfo(passage);
            } catch (IllegalArgumentException e) {
              Widgets.createAlert("Error", "Invalid input", e.getMessage()).showAndWait();
            }
          }
          return null;
        });

    dialog.showAndWait();
  }

  /** Configures the Exit Button. On action, opens a dialog to confirm the exit without saving. */
  private void configureExitButton() {
    createStoryView.getExitButton().setOnAction(event -> showExitDialog());
  }

  /**
   * Opens a dialog to confirm the exit without saving. If confirmed, switches the view to the main
   * menu.
   */
  private void showExitDialog() {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Exit");
    dialog.setHeaderText("Exit without saving?");
    ButtonType exitButtonType = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(exitButtonType, ButtonType.CANCEL);

    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == exitButtonType) {
            createStoryView
                .getExitButton()
                .getScene()
                .setRoot(new MainMenuViewController().getRoot());
          }
          return dialogButton;
        });
    dialog.showAndWait();
  }

  /**
   * Configures the Save Story Button. On action, opens a dialog to confirm the save operation,
   * which overwrites the old file. If an error occurs during the save operation, an error alert is
   * shown.
   */
  private void configureSaveStoryButton() {
    createStoryView
        .getSaveButton()
        .setOnAction(
            event -> {
              Dialog<ButtonType> dialog = new Dialog<>();
              dialog.setTitle("Save Story");
              dialog.setHeaderText("Save Story (this will overwrite the old file)");
              ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
              dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

              dialog.setResultConverter(
                  dialogButton -> {
                    if (dialogButton == saveButtonType) {
                      StoryFileHandler storyFileHandler = new StoryFileHandler();
                      try {
                        storyFileHandler.saveStoryToFile(story);
                        createStoryView
                            .getSaveButton()
                            .getScene()
                            .setRoot(new MainMenuViewController().getRoot());
                      } catch (IOException e) {
                        Widgets.createAlert("Error", "Could not save story", e.getMessage())
                            .showAndWait();
                      }
                    }
                    return dialogButton;
                  });
              dialog.showAndWait();
            });
  }

  /**
   * Configures the Delete Passage Button. On action, if a passage is selected, opens a dialog to
   * confirm the deletion of the passage.
   */
  private void configureDeletePassageButton() {
    createStoryView
        .getDeletePassageButton()
        .setOnAction(
            event -> {
              if (selectedPassage != null) {
                showDeletePassageDialog();
                updatePassageContainerText();
              }
            });
  }

  /**
   * Opens a dialog to confirm the deletion of a selected passage. If confirmed, removes all links
   * to and from the passage, and updates the view.
   */
  private void showDeletePassageDialog() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete Passage");
    alert.setHeaderText(
        "Are you sure you want to delete the " + selectedPassage.getTitle() + " passage?");
    alert.setContentText("This will also delete all links to and from this passage!");
    alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
    alert
        .showAndWait()
        .ifPresent(
            buttonType -> {
              if (buttonType == ButtonType.YES) {
                story.removeAllLinksToPassage(selectedPassage.getTitle());

                if (selectedPassage == story.getOpeningPassage()) {
                  Widgets.createAlert("Error", "Cannot delete opening passage", "").showAndWait();
                  return;
                }
                story.removePassage(new Link("empty", selectedPassage.getTitle()));
                passages.remove(selectedPassage);
                selectedLink = null;
                selectedPassage = null;
                updateLinksViewAndPassageInfo(
                    new Passage("DRAG PASSAGE HERE", "No passage selected"));
              }
            });
  }

  /**
   * Configures the Delete Action Button. On action, if a passage, link, and action are all
   * selected, removes the action from the link.
   */
  private void configureDeleteActionButton() {
    createStoryView
        .getDeleteActionButton()
        .setOnAction(
            event -> {
              if (selectedPassage != null && selectedLink != null && selectedAction != null) {
                selectedLink.getActions().remove(selectedAction);
                updateActionsListView();
              }
            });
  }

  /**
   * Configures the Add Action Button. On action, if a passage and link are selected, opens a dialog
   * to add an action to the link.
   */
  private void configureAddActionButton() {
    createStoryView
        .getAddActionButton()
        .setOnAction(
            event -> {
              if (selectedPassage != null && selectedLink != null) {
                showAddActionDialog();
                updateActionsListView();
              }
            });
  }

  /**
   * Opens a dialog to add an action to a selected link. Allows for the choice of action type and
   * value. If an invalid input is provided, an error alert is shown.
   */
  private void showAddActionDialog() {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Add Action");
    dialog.setHeaderText(
        "Add Action to the link from\nthe "
            + selectedPassage.getTitle()
            + " passage to the "
            + selectedLink.getRef()
            + " passage");
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    ChoiceBox<String> actionChoiceBox = new ChoiceBox<>();
    actionChoiceBox.getItems().add("healthChange");
    actionChoiceBox.getItems().add("item");
    actionChoiceBox.getItems().add("scoreChange");
    actionChoiceBox.getItems().add("goldChange");

    TextField valueField = new TextField();
    valueField.setDisable(true);

    actionChoiceBox
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldValue, newValue) -> {
              valueField.setDisable(false);
              switch (newValue) {
                case "healthChange" -> {
                  valueField.setDisable(false);
                  valueField.setPromptText("Enter health value (eg. -10 or 10)");
                }
                case "item" -> {
                  valueField.setDisable(false);
                  valueField.setPromptText("Enter item name (eg. Sword)");
                }
                case "scoreChange" -> {
                  valueField.setDisable(false);
                  valueField.setPromptText("Enter score value (eg. -10 or 10)");
                }
                case "goldChange" -> {
                  valueField.setDisable(false);
                  valueField.setPromptText("Enter gold value (eg. -10 or 10)");
                }
                default -> {
                  valueField.setDisable(true);
                  valueField.setPromptText("");
                }
              }
            });
    VBox vBox = new VBox();
    vBox.getChildren().addAll(actionChoiceBox, valueField);

    dialog.getDialogPane().setContent(vBox);

    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == ButtonType.OK) {
            try {
              Action action =
                  ActionFactory.createAction(actionChoiceBox.getValue(), valueField.getText());
              selectedLink.addAction(action);
            } catch (IllegalArgumentException e) {
              Widgets.createAlert("Error", "Invalid input", e.getMessage()).showAndWait();
            }
          }
          return null;
        });

    dialog.showAndWait();
  }

  /**
   * Returns the root region of the CreateStoryView.
   *
   * @return the root region
   */
  public Region getRoot() {
    return createStoryView.getRoot();
  }

  /** Configures the Add Passage Button. On action, opens a dialog to add a new passage. */
  private void configureAddPassageButton() {
    createStoryView.getAddPassageButton().setOnAction(event -> showAddPassageDialog());
  }

  /**
   * Opens a dialog to add a new passage. Allows for input of title, text, mood, and single visit
   * property. If an invalid input is provided or a passage with the same title already exists, an
   * error alert is shown.
   */
  private void showAddPassageDialog() {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Add Passage");
    dialog.setHeaderText("Add Passage");
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    TextField titleField = new TextField();
    titleField.setPromptText("Enter title");
    TextArea textArea = new TextArea();
    textArea.setPromptText("Enter text");

    ComboBox<Mood> moodChoiceBox = new ComboBox<>();
    moodChoiceBox.getItems().addAll(Mood.values());
    moodChoiceBox.setPromptText("Select mood");

    Label singleVisitLabel = new Label("Support a single visit only?");
    CheckBox isSingleVisit = new CheckBox();
    HBox singleVisitHbox = new HBox(singleVisitLabel, isSingleVisit);

    VBox vBox = new VBox();
    vBox.getChildren().addAll(titleField, textArea, moodChoiceBox, singleVisitHbox);

    dialog.getDialogPane().setContent(vBox);

    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == ButtonType.OK) {
            try {
              Passage passage =
                  new Passage(
                      titleField.getText(),
                      textArea.getText(),
                      moodChoiceBox.getValue(),
                      isSingleVisit.isSelected());

              if (passages.contains(passage)) {
                Widgets.createAlert(
                        "Error", "Invalid input", "Passage with this title already exists")
                    .showAndWait();
                return null;
              }

              passages.add(passage);
              story.addPassage(passage);
            } catch (IllegalArgumentException e) {
              Widgets.createAlert("Error", "Invalid input", e.getMessage()).showAndWait();
            }
          }
          return null;
        });

    dialog.showAndWait();
  }

  /**
   * Adds a listener to the passages list. Updates the passage container text when a passage is
   * selected.
   */
  private void addPassagesListener() {
    createStoryView.getPassages().setItems(passages);

    createStoryView
        .getPassages()
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue == null) {
                createStoryView.getPassageContainer().setText("DRAG PASSAGE HERE");
              }
            });
  }

  /** Configures drag and drop functionality for passages, links, and actions. */
  private void configureDragAndDrop() {
    configurePassagesCellFactory();
    configureLinksViewCellFactory();
    configurePassageContainerDragAndDrop();
    configureLinksViewDragAndDrop();
    configureLinksViewSelectionListener();
    configureActionsViewSelectionListener();
  }

  /**
   * Configures the cell factory for the passages list view. Allows for drag and drop functionality
   * of passages.
   */
  private void configurePassagesCellFactory() {
    createStoryView
        .getPassages()
        .setCellFactory(
            lv -> {
              ListCell<Passage> cell =
                  new ListCell<>() {
                    @Override
                    protected void updateItem(Passage item, boolean empty) {
                      super.updateItem(item, empty);
                      if (empty) {
                        setText(null);
                      } else {
                        setText(item.getTitle());
                        this.getStyleClass().add("default-list-view");
                      }
                    }
                  };

              cell.setOnDragDetected(
                  event -> {
                    if (!cell.isEmpty()) {
                      Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                      ClipboardContent cc = new ClipboardContent();
                      cc.putString(cell.getItem().getTitle());
                      db.setContent(cc);
                      event.consume();
                    }
                  });

              return cell;
            });
  }

  /**
   * Configures drag and drop functionality for the passage container. On drop, updates the links
   * view and passage information to reflect the dropped passage.
   */
  private void configurePassageContainerDragAndDrop() {
    Label passageContainer = createStoryView.getPassageContainer();
    passageContainer.setOnDragOver(
        event -> {
          if (event.getGestureSource() != passageContainer && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
          }
          event.consume();
        });

    passageContainer.setOnDragDropped(
        event -> {
          Dragboard db = event.getDragboard();
          boolean success = false;
          if (db.hasString()) {
            Passage passage =
                passages.stream()
                    .filter(p -> p.getTitle().equals(db.getString()))
                    .findFirst()
                    .orElse(null);
            if (passage != null) {
              selectedLink = null;
              selectedAction = null;
              updateLinksViewAndPassageInfo(passage);
              updateLinkText("");
              updateActionsListView();
              updatePassageContainerText();
              success = true;
            }
          }
          event.setDropCompleted(success);
          event.consume();
        });
  }

  /** Updates the text of the passage container to display the title of the selected passage. */
  private void updatePassageContainerText() {
    createStoryView.getPassageContainer().setText(selectedPassage.getTitle());

    createStoryView.getPassageContainer().getStyleClass().add("passage-container");
  }

  /**
   * Updates the links view and passage information to reflect the given passage.
   *
   * @param passage The passage to be reflected.
   */
  private void updateLinksViewAndPassageInfo(Passage passage) {
    createStoryView.getLinksView().getItems().clear();
    updateActionsListView();
    selectedPassage = passage;

    for (Link link : passage.getLinks()) {
      if (link.getRef().equals(story.getOpeningPassage().getTitle())) {
        createStoryView.getLinksView().getItems().add(story.getOpeningPassage());
      } else {
        createStoryView.getLinksView().getItems().addAll(story.getPassage(link));
      }
    }

    createStoryView.getPassageContent().setText(passage.getContent());
    createStoryView.getMoodText().setText(passage.getMood().toString());
    createStoryView.getSingleVisitOnly().setText(passage.isSingleVisitOnly() ? "Yes" : "No");
  }

  /**
   * Configures drag and drop functionality for the links view. On drop, adds a new link to the
   * selected passage that leads to the dropped passage.
   */
  private void configureLinksViewDragAndDrop() {
    ListView<Passage> linksView = createStoryView.getLinksView();
    linksView.setOnDragOver(
        event -> {
          if (event.getGestureSource() != linksView && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
          }
          event.consume();
        });

    linksView.setOnDragDropped(
        event -> {
          Dragboard db = event.getDragboard();

          if (!db.hasString()) {
            return;
          }
          Passage passageToAdd =
              passages.stream()
                  .filter(p -> p.getTitle().equals(db.getString()))
                  .findFirst()
                  .orElse(null);

          if (passageToAdd == null) {
            return;
          }
          if (selectedPassage == null) {
            return;
          }
          if (selectedPassage.getTitle().equals(passageToAdd.getTitle())) {
            Widgets.createAlert("Error", "Link to the specified passage already exists", "")
                .showAndWait();
            return;
          }

          TextField linkText = newLinkPopup(passageToAdd.getTitle());
          if (linkText == null) {
            return;
          }
          try {
            selectedPassage.addLink(new Link(linkText.getText(), passageToAdd.getTitle()));
          } catch (IllegalArgumentException e) {
            Widgets.createAlert(
                    "Error", "Link to the specified passage already exists", e.getMessage())
                .showAndWait();
          }
          updateLinksViewAndPassageInfo(selectedPassage);
          updateActionsListView();

          event.setDropCompleted(true);
          event.consume();
        });
  }

  /**
   * Opens a dialog to enter the text for a new link to the given passage title.
   *
   * @param passageTitle The title of the passage to be linked to.
   * @return A TextField containing the text for the new link, or null if the dialog was cancelled.
   */
  private TextField newLinkPopup(String passageTitle) {
    Dialog<Object> dialog = new Dialog<>();
    dialog.setTitle("New Link");
    dialog.setHeaderText("Add a new link to the " + passageTitle + " passage \nEnter Link text:");

    ButtonType addLinkButton = new ButtonType("Add Link", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    TextField linkText = new TextField();
    linkText.setPromptText("Link Text");
    dialog.getDialogPane().setContent(linkText);

    dialog.getDialogPane().getButtonTypes().addAll(addLinkButton, cancelButton);

    dialog.showAndWait();
    return dialog.getResult() == addLinkButton ? linkText : null;
  }

  /**
   * Configures the cell factory for the links view. Displays the title of the passage each link
   * leads to.
   */
  private void configureLinksViewCellFactory() {
    createStoryView
        .getLinksView()
        .setCellFactory(
            listView ->
                new ListCell<>() {
                  @Override
                  protected void updateItem(Passage item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                      setText(null);
                    } else {
                      setText(item.getTitle());
                      setPadding(new Insets(10, 0, 10, 0));
                    }
                  }
                });
  }

  /**
   * Adds a listener to the links view selection model. Updates the displayed link text and actions
   * list to reflect the selected link.
   */
  private void configureLinksViewSelectionListener() {
    createStoryView
        .getLinksView()
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue == null) {
                createStoryView.getLinkText().setText("Select a link to see its text here!");
                return;
              }
              List<Link> links = selectedPassage.getLinks();
              for (Link link : links) {
                if (link.getRef().equals(newValue.getTitle())) {
                  selectedLink = link;
                  selectedAction = null;
                  updateLinkText(link.getText());
                  updateActionsListView();
                }
              }
            });
  }

  /**
   * Adds a listener to the actions list view selection model. Updates the selected action to
   * reflect the selected item in the list view.
   */
  private void configureActionsViewSelectionListener() {
    createStoryView
        .getActionsListView()
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue != null) {
                selectedAction = newValue;
              }
            });
  }

  /** Updates the actions list view to display the actions of the selected link. */
  private void updateActionsListView() {
    createStoryView.getActionsListView().getItems().clear();
    selectedAction = null;

    if (selectedPassage == null) {
      return;
    }

    if (selectedLink == null) {
      return;
    }
    createStoryView.getActionsListView().getItems().addAll(selectedLink.getActions());
  }

  /**
   * Updates the displayed link text to the given text.
   *
   * @param text The text to be displayed.
   */
  private void updateLinkText(String text) {
    createStoryView.getLinkText().clear();
    if (text.isBlank()) {
      createStoryView.getLinkText().setText("Select a link to see its text here!");
      return;
    }
    createStoryView.getLinkText().setText(text);
  }
}

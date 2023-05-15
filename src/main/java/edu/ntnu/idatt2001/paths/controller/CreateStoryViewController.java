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

public class CreateStoryViewController {
  private final ObservableList<Passage> passages;
  private final CreateStoryView createStoryView;
  private final Story story;
  private Passage selectedPassage;
  private Link selectedLink;
  private Action selectedAction;

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
  }

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

  private void configureExitButton() {
    createStoryView.getExitButton().setOnAction(event -> showExitDialog());
  }

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

  public Region getRoot() {
    return createStoryView.getRoot();
  }

  private void configureAddPassageButton() {
    createStoryView.getAddPassageButton().setOnAction(event -> showAddPassageDialog());
  }

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

  private void configureDragAndDrop() {
    configurePassagesCellFactory();
    configureLinksViewCellFactory();
    configurePassageContainerDragAndDrop();
    configureLinksViewDragAndDrop();
    configureLinksViewSelectionListener();
    configureActionsViewSelectionListener();
  }

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

  private void updatePassageContainerText() {
    createStoryView.getPassageContainer().setText(selectedPassage.getTitle());

    createStoryView.getPassageContainer().getStyleClass().add("passage-container");
  }

  private void updateLinksViewAndPassageInfo(Passage passage) {
    createStoryView.getLinksView().getItems().clear();
    updateActionsListView();
    selectedPassage = passage;

    for (Link link : passage.getLinks()) {
      if (link.getRef().equals(story.getOpeningPassage().getTitle())) {
        createStoryView.getLinksView().getItems().add(story.getOpeningPassage());
      }
      createStoryView.getLinksView().getItems().addAll(story.getPassage(link));
    }
    createStoryView.getPassageContent().setText(passage.getContent());
  }

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
          boolean success = false;
          if (db.hasString()) {
            Passage passageToAdd =
                passages.stream()
                    .filter(p -> p.getTitle().equals(db.getString()))
                    .findFirst()
                    .orElse(null);
            if (selectedPassage != null && passageToAdd != null) {
              TextField linkText = newLinkPopup(passageToAdd.getTitle());
              if (linkText != null) {
                selectedPassage.addLink(new Link(linkText.getText(), passageToAdd.getTitle()));
                updateLinksViewAndPassageInfo(selectedPassage);
                updateActionsListView();
              }
              success = true;
            }
          }
          event.setDropCompleted(success);
          event.consume();
        });
  }

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

  private void configureLinksViewSelectionListener() {
    createStoryView
        .getLinksView()
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue != null) {
                List<Link> links = selectedPassage.getLinks();
                for (Link link : links) {
                  if (link.getRef().equals(newValue.getTitle())) {
                    selectedLink = link;
                    selectedAction = null;
                    updateLinkText(link.getText());
                    updateActionsListView();
                  }
                }
              }
            });
  }

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

  private void updateActionsListView() {
    createStoryView.getActionsListView().getItems().clear();
    selectedAction = null;
    if (selectedLink != null) {
      createStoryView.getActionsListView().getItems().addAll(selectedLink.getActions());
    }
  }

  private void updateLinkText(String text) {
    createStoryView.getLinkText().clear();
    createStoryView.getLinkText().setText(text);
  }
}

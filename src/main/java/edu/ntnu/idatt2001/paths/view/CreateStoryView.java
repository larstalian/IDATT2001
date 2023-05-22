package edu.ntnu.idatt2001.paths.view;

import static javafx.geometry.Pos.BOTTOM_LEFT;
import static javafx.geometry.Pos.CENTER;

import edu.ntnu.idatt2001.paths.model.actions.Action;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

/**
 * This class represents the view for creating a story. It contains various UI elements such as
 * buttons, labels, and text areas for managing the story creation process.
 */
public class CreateStoryView {

  @Getter
  private final BorderPane root;
  @Getter
  private final ListView<Passage> passages;
  @Getter
  private final ListView<Passage> linksView;
  @Getter
  private final TextArea linkText;
  @Getter
  private final ListView<Action> actionsListView;
  @Getter
  private final Button exitButton;
  @Getter
  private final Button addActionButton;
  @Getter
  private final Button saveButton;
  @Getter
  private final TextArea passageContent;
  @Getter
  private final Button editPassageButton;
  @Getter
  private final TextArea moodText;
  @Getter
  private final TextArea singleVisitOnly;
  @Getter
  private final Button addPassageButton;
  @Getter
  private final Button deletePassageButton;
  @Getter
  private Label passageContainer;
  @Getter
  private final Button deleteActionButton;
  @Getter
  private final Button deleteLinkButton;

  /**
   * Constructs a new CreateStoryView. This involves initializing the UI elements and setting up
   * their properties.
   */
  public CreateStoryView() {
    passages = new ListView<>();

    deletePassageButton = new Button("Delete Passage");
    deletePassageButton.getStyleClass().add("default-button");

    addActionButton = new Button("Add Action");
    addActionButton.getStyleClass().add("default-button");

    deleteActionButton = new Button("Delete Action");
    deleteActionButton.getStyleClass().add("default-button");

    addPassageButton = new Button("Add Passage");
    addPassageButton.getStyleClass().add("default-button");

    saveButton = new Button("Save Story");
    saveButton.getStyleClass().add("default-button");

    exitButton = new Button("Exit to main menu");
    exitButton.getStyleClass().add("default-button");

    editPassageButton = new Button("Edit Passage");
    editPassageButton.getStyleClass().add("default-button");

    deleteLinkButton = new Button("Delete Link");
    deleteLinkButton.getStyleClass().add("default-button");

    passageContainer = new Label();

    passageContent = new TextArea();
    singleVisitOnly = new TextArea();

    linksView = new ListView<>();
    linkText = new TextArea();
    moodText = new TextArea();

    actionsListView = new ListView<>();
    actionsListView.getStyleClass().add("default-list-view");

    root = createRoot();
  }

  /**
   * Creates the root BorderPane for the story creation view.
   *
   * @return the created BorderPane
   */
  private BorderPane createRoot() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("main-menu");
    results.setCenter(createCenter());
    results.setRight(createRight());
    results.setTop(createTop());
    return results;
  }

  /**
   * Creates the top Node for the story creation view. This includes the exit and save buttons.
   *
   * @return the created Node
   */
  private Node createTop() {
    HBox results = new HBox();
    results.getStyleClass().add("top-buttons");
    results.setSpacing(10);
    results.setPadding(new Insets(10));
    results.getChildren().add(exitButton);
    results.getChildren().add(saveButton);
    results.setMinHeight(HBox.USE_PREF_SIZE);
    results.setMaxHeight(30);
    return results;
  }

  /**
   * Creates the right Node for the story creation view. This includes the passages ListView and
   * passage choices.
   *
   * @return the created Node
   */
  private Node createRight() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("right-panel");
    results.setCenter(passages);
    results.setBottom(createPassageChoices());
    return results;
  }

  /**
   * Creates the Node for the passage choices in the story creation view. This includes the add and
   * delete passage buttons.
   *
   * @return the created Node
   */
  private Node createPassageChoices() {
    HBox results = new HBox();
    results.getStyleClass().add("passage-choices");
    results.setSpacing(10);
    results.setPadding(new Insets(10));
    results.getChildren().addAll(addPassageButton, deletePassageButton);
    return results;
  }

  /**
   * Creates the center Node for the story creation view. This includes the passage container and
   * info, and the passage info HBox.
   *
   * @return the created Node
   */
  private Node createCenter() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("center-panel");
    results.setCenter(createPassageContainerAndInfo());
    results.setBottom(createPassageInfoHBox());
    return results;
  }

  /**
   * Creates the Node for the passage container and info in the story creation view. This includes
   * the passage container, passage info, and passage additional features info.
   *
   * @return the created Node
   */
  private Node createPassageContainerAndInfo() {
    BorderPane results = new BorderPane();
    results.setCenter(createPassageContainer());
    results.setBottom(createPassageInfo());
    results.setRight(createPassageAdditionalFeaturesInfo());
    return results;
  }

  /**
   * Creates the Node for the passage additional features info in the story creation view. This
   * includes the mood text and single visit only text area.
   *
   * @return the created Node
   */
  private Node createPassageAdditionalFeaturesInfo() {
    VBox results = new VBox();
    Label MoodLabel = new Label("Mood:");
    MoodLabel.getStyleClass().add("default-label");
    results.getChildren().add(MoodLabel);
    moodText.setEditable(false);
    results.getChildren().add(moodText);
    Label isSingleVisitOnly = new Label("Single Visit Only:");
    isSingleVisitOnly.getStyleClass().add("default-label");
    results.getChildren().add(isSingleVisitOnly);
    results.getChildren().add(singleVisitOnly);
    results.setMaxWidth(160);
    results.setSpacing(10);
    results.setMinWidth(160);
    results.setPadding(new Insets(20));
    return results;
  }

  /**
   * Creates the Node for the passage info in the story creation view. This includes the passage
   * content and the edit passage button.
   *
   * @return the created Node
   */
  private Node createPassageInfo() {
    HBox results = new HBox();
    results.getChildren().addAll(createPassageContent(), createEditPassageButton());
    results.setMaxWidth(400);
    return results;
  }

  /**
   * Creates the Node for the passage content in the story creation view.
   *
   * @return the created Node
   */
  private Node createPassageContent() {
    passageContent.setEditable(false);
    passageContent.setPrefHeight(40);
    return passageContent;
  }

  /**
   * Creates the Node for the edit passage button in the story creation view.
   *
   * @return the created Node
   */
  private Node createEditPassageButton() {
    editPassageButton.setMinWidth(100);
    return editPassageButton;
  }

  /**
   * Creates the Node for the passage info HBox in the story creation view. This includes the links
   * view and links info view.
   *
   * @return the created Node
   */
  private Node createPassageInfoHBox() {
    HBox results = new HBox();
    results.getStyleClass().add("passage-info-hbox");
    results.setSpacing(10);
    results.setPadding(new Insets(10));
    results.getChildren().addAll(createLinksView(), createLinksInfoView());
    return results;
  }

  /**
   * Creates the Node for the links info view in the story creation view. This includes the link
   * text and actions view.
   *
   * @return the created Node
   */
  private Node createLinksInfoView() {
    BorderPane results = new BorderPane();
    Label linksLabel = new Label("Link Text:");
    linksLabel.getStyleClass().add("default-label");
    results.setTop(linksLabel);
    results.setCenter(linkText);
    linkText.setEditable(false);
    linkText.setMaxWidth(200);
    linkText.setText("Select a link to see its text here!");
    results.setBottom(createActionsView());
    results.getStyleClass().add("create-story-link-view");
    return results;
  }

  /**
   * Creates the Node for the actions view in the story creation view. This includes the actions
   * ListView and action buttons.
   *
   * @return the created Node
   */
  private Node createActionsView() {
    BorderPane results = new BorderPane();
    results.setMinHeight(200);
    Label actionsLabel = new Label("Actions");
    actionsLabel.getStyleClass().add("default-label");
    results.setTop(actionsLabel);
    results.setCenter(actionsListView);
    actionsListView.setPlaceholder(new Label("No actions!\nAdd one to see it here!"));
    results.setPrefHeight(130);
    results.setMaxWidth(200);
    results.setBottom(createActionsButtons());
    return results;
  }

  /**
   * Creates the Node for the actions buttons in the story creation view. This includes the add and
   * delete action buttons.
   *
   * @return the created Node
   */
  private Node createActionsButtons() {
    HBox results = new HBox();
    results.setSpacing(10);
    results.getChildren().add(addActionButton);
    results.getChildren().add(deleteActionButton);
    results.setAlignment(BOTTOM_LEFT);
    return results;
  }

  /**
   * Creates the Node for the links view in the story creation view. This includes the links label,
   * links ListView, and the delete link button.
   *
   * @return the created Node
   */
  private Node createLinksView() {
    BorderPane results = new BorderPane();
    Label linksLabel = new Label("Links");
    linksLabel.getStyleClass().add("default-label");
    results.setTop(linksLabel);
    results.setCenter(linksView);
    linksView.setPlaceholder(new Label("No links!\nDrag a passage here\nto create one!"));
    linksView.setMinSize(200, 100);
    linksView.setMaxSize(200, Double.MAX_VALUE);
    results.setBottom(deleteLinkButton);
    return results;
  }

  /**
   * Creates the Node for the passage container in the story creation view. The passage container is
   * where a passage can be dragged in the UI.
   *
   * @return the created Node
   */
  private Node createPassageContainer() {
    passageContainer = new Label("DRAG PASSAGE HERE");
    passageContainer.setAlignment(CENTER);
    passageContainer.getStyleClass().add("passage-container");
    passageContainer.setMinSize(200, 100);
    passageContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    return passageContainer;
  }
}

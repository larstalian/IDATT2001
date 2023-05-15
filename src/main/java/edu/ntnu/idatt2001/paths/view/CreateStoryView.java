package edu.ntnu.idatt2001.paths.view;

import static javafx.geometry.Pos.*;

import edu.ntnu.idatt2001.paths.model.actions.Action;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.Getter;

public class CreateStoryView {

  @Getter private final BorderPane root;
  @Getter private final ListView<Passage> passages;
  @Getter private final ListView<Passage> linksView;
  @Getter private final TextArea linkText;
  @Getter private final ListView<Action> actionsListView;
  @Getter private final Button exitButton;
  @Getter private final Button addActionButton;
  @Getter private final Button saveButton;
  @Getter private final TextArea passageContent;
  @Getter private final Button editPassageButton;
  @Getter private final TextArea moodText;
  @Getter private final TextArea singleVisitOnly;
  @Getter private Button addPassageButton;
  @Getter private Button deletePassageButton;
  @Getter private Label passageContainer;
  @Getter private Button deleteActionButton;
  @Getter private Button deleteLinkButton;

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

  private BorderPane createRoot() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("main-menu");
    results.setCenter(createCenter());
    results.setRight(createRight());
    results.setTop(createTop());
    return results;
  }

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

  private Node createRight() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("right-panel");
    results.setCenter(passages);
    results.setBottom(createPassageChoices());
    return results;
  }

  private Node createPassageChoices() {
    HBox results = new HBox();
    results.getStyleClass().add("passage-choices");
    results.setSpacing(10);
    results.setPadding(new Insets(10));
    results.getChildren().addAll(addPassageButton, deletePassageButton);
    return results;
  }

  private Node createCenter() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("center-panel");
    results.setCenter(createPassageContainerAndInfo());
    results.setBottom(createPassageInfoHBox());
    return results;
  }

  private Node createPassageContainerAndInfo() {
    BorderPane results = new BorderPane();
    results.setCenter(createPassageContainer());
    results.setBottom(createPassageInfo());
    results.setRight(createPassageAdditionalFeaturesInfo());
    return results;
  }

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

  private Node createPassageInfo() {
    HBox results = new HBox();
    results.getChildren().addAll(createPassageContent(), createEditPassageButton());
    results.setMaxWidth(400);
    return results;
  }

  private Node createPassageContent() {
    passageContent.setEditable(false);
    passageContent.setPrefHeight(40);
    return passageContent;
  }

  private Node createEditPassageButton() {
    editPassageButton.setMinWidth(100);
    return editPassageButton;
  }

  private Node createPassageInfoHBox() {
    HBox results = new HBox();
    results.getStyleClass().add("passage-info-hbox");
    results.setSpacing(10);
    results.setPadding(new Insets(10));
    results.getChildren().addAll(createLinksView(), createLinksInfoView());
    return results;
  }

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

  private Node createActionsButtons() {
    HBox results = new HBox();
    results.setSpacing(10);
    results.getChildren().add(addActionButton);
    results.getChildren().add(deleteActionButton);
    results.setAlignment(BOTTOM_LEFT);
    return results;
  }

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

  private Node createPassageContainer() {
    passageContainer = new Label("DRAG PASSAGE HERE");
    passageContainer.setAlignment(CENTER);
    passageContainer.getStyleClass().add("passage-container");
    passageContainer.setMinSize(200, 100);
    passageContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    return passageContainer;
  }
}

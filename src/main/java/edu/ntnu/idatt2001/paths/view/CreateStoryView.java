package edu.ntnu.idatt2001.paths.view;

import static javafx.geometry.Pos.CENTER;

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
  @Getter private Button addPassageButton;
  @Getter private Button deletePassageButton;
  @Getter private Label passageContainer;
  @Getter private final TextArea passageContent;
  @Getter private Button deleteActionButton;
  @Getter private final Button editPassageButton;

  public CreateStoryView() {
    passages = new ListView<>();
    deletePassageButton = new Button("Delete Passage");
    addActionButton = new Button("Add Action");
    deleteActionButton = new Button("Delete Action");
    addPassageButton = new Button("Add Passage");
    saveButton = new Button("Save Story");
    exitButton = new Button("Exit to main menu");
    editPassageButton = new Button("Edit Passage");
    passageContainer = new Label();
    passageContent = new TextArea();
    linksView = new ListView<>();
    linkText = new TextArea();
    actionsListView = new ListView<>();
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
    return results;
  }

  private Node createPassageInfo() {
    HBox results = new HBox();
    results.getChildren().addAll(createPassageContent(), createEditPassageButton());
    return results;
  }

  private Node createPassageContent() {
    passageContent.setEditable(false);
    passageContent.setPrefHeight(40);
    passageContent.setMaxWidth(320);
    return passageContent;
  }

  private Node createEditPassageButton() {
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
    VBox results = new VBox();
    results.setSpacing(10);
    results.getChildren().add(linkText);
    linkText.setEditable(false);
    linkText.setMaxWidth(200);
    results.getChildren().add(createActionsView());
    results.getStyleClass().add("create-story-link-view");
    return results;
  }

  private Node createActionsView() {
    VBox results = new VBox();
    results.setSpacing(10);
    results.getChildren().add(actionsListView);
    results.setPrefHeight(130);
    results.setMaxWidth(200);
    results.getChildren().add(createActionsButtons());
    return results;
  }

  private Node createActionsButtons() {
    HBox results = new HBox();
    results.setSpacing(10);
    results.getChildren().add(addActionButton);
    results.getChildren().add(deleteActionButton);
    return results;
  }

  private Node createLinksView() {
    linksView.setPrefHeight(200);
    linksView.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
    return linksView;
  }

  private Node createPassageContainer() {
    passageContainer = new Label("DRAG PASSAGE HERE");
    passageContainer.setAlignment(CENTER);
    passageContainer.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
    passageContainer.getStyleClass().add("passage-container");
    passageContainer.setMinSize(200, 100);
    passageContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    return passageContainer;
  }
}
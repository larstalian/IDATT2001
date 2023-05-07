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

  @Getter private final VBox root;
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
  @Getter private Label passageInfo;
  @Getter private Button deleteActionButton;

  public CreateStoryView() {
    passages = new ListView<>();
    deletePassageButton = new Button("Delete Passage");
    addActionButton = new Button("Add Action");
    deleteActionButton = new Button("Delete Action");
    addPassageButton = new Button("Add Passage");
    saveButton = new Button("Save Story");
    exitButton = new Button("Exit to main menu");
    passageContainer = new Label();
    passageInfo = new Label();
    linksView = new ListView<>();
    linkText = new TextArea();
    actionsListView = new ListView<>();
    root = createRoot();
  }

  private Node createPassageView() {
    VBox results = new VBox(passages);
    results.setMaxWidth(100);
    results.autosize();
    return results;
  }

  private VBox createRoot() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("main-menu");
    results.setCenter(createCenter());
    results.setRight(createRight());

    VBox wrapper = new VBox();
    wrapper.getChildren().addAll(createTop(), results);
    return wrapper;
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
    results.setCenter(createPassageView());
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
    VBox results = new VBox();
    results.setSpacing(10);
    results.getChildren().add(createPassageContainer());
    VBox.setVgrow(passageContainer, Priority.ALWAYS);
    results.getChildren().add(passageInfo);
    return results;
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
    results.getChildren().add(createActionsView());
    return results;
  }

  private Node createActionsView() {
    VBox results = new VBox();
    results.setSpacing(10);
    results.getChildren().add(actionsListView);
    results.setPrefHeight(200);
    results.setPrefWidth(200);
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
    return linksView;
  }

  private Node createPassageContainer() {
    passageContainer = new Label("DRAG PASSAGE HERE");
    passageContainer.setAlignment(CENTER);
    passageContainer.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
    passageContainer.getStyleClass().add("passage-container");
    passageContainer.setMinSize(200, 100);
    passageContainer.setMaxSize(Double.MAX_VALUE, 100);
    return passageContainer;
  }
}
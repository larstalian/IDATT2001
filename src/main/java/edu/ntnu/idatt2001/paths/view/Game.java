package edu.ntnu.idatt2001.paths.view;

import edu.ntnu.idatt2001.paths.model.story.Link;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Builder;
import javafx.util.Duration;

public class Game implements Builder<Region> {

  private static final StringProperty contentBar = new SimpleStringProperty();
  private static final AtomicBoolean isAnimationSkipped = new AtomicBoolean(false);
  private static Passage currentPassage;
  private static edu.ntnu.idatt2001.paths.model.game.Game currentGame;
  private Label skipLabel;
  private VBox links;

  public static void setCurrentGame(edu.ntnu.idatt2001.paths.model.game.Game chosenGame) {
    currentGame = chosenGame;
  }

  @Override
  public Region build() {
    currentPassage = currentGame.begin();
    BorderPane root = createRoot();
    addSceneListener(root);
    return root;
  }

  private BorderPane createRoot() {
    BorderPane root = new BorderPane();
    root.getStyleClass().add("main-menu");
    root.setCenter(createCenter());
    root.setBottom(createBottom());
    root.setRight(createRight());
    return root;
  }

  private Node createRight() {
    StackPane results = new StackPane();
    results.getChildren().add(createLinkChoices());
    return results;
  }

  private Node createBottom() {
    StackPane bottom = new StackPane();
    ScrollPane contentBarText = createContentBar();
    contentBarText.getStyleClass().add("content-bar");
    bottom.getChildren().add(contentBarText);
    StackPane.setAlignment(contentBarText, Pos.BOTTOM_CENTER);

    skipLabel = new Label("(click to skip)");
    skipLabel.getStyleClass().add("skip-label");
    skipLabel.setVisible(false);

    bottom.getChildren().add(skipLabel);
    StackPane.setAlignment(skipLabel, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(skipLabel, new Insets(0, 10, 10, 0));

    return bottom;
  }

  private Node createCenter() {
    AnchorPane center = new AnchorPane();
    return center;
  }

  private ScrollPane createContentBar() {
    Text contentBarText = new Text();
    contentBarText.textProperty().bind(contentBar);
    TextFlow textFlow = new TextFlow(contentBarText);
    ScrollPane scrollPane = new ScrollPane(textFlow);

    textFlow
        .prefWidthProperty()
        .bind(Bindings.selectDouble(scrollPane.viewportBoundsProperty(), "width"));

    scrollPane.getStyleClass().add("scroll-pane");
    textFlow.getStyleClass().add("super-text-flow");
    scrollPane.setOnMouseClicked(event -> isAnimationSkipped.set(true));
    return scrollPane;
  }

  private void createContentString() {
    contentBar.set("");
    char[] charArray = currentPassage.getContent().toCharArray();
    AtomicInteger index = new AtomicInteger(0);

    skipLabel.setVisible(true);
    links.setVisible(false);

    Timeline timeline = new Timeline();
    KeyFrame keyFrame =
        new KeyFrame(
            Duration.millis(30),
            event -> {
              if (index.get() < charArray.length) {
                contentBar.set(contentBar.getValue() + charArray[index.get()]);
                index.incrementAndGet();
              }
              if (isAnimationSkipped.get()) {
                contentBar.set(currentPassage.getContent());
                timeline.stop();
              }
            });

    timeline.getKeyFrames().add(keyFrame);
    timeline.setCycleCount(charArray.length);

    timeline
        .statusProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue == Animation.Status.STOPPED) {
                skipLabel.setVisible(false);
                links.setVisible(true);
                isAnimationSkipped.set(false);
              }
            });
  
    timeline.play();
  }

  private Node createLinkChoices() {
    links = new VBox();
    links.setAlignment(Pos.BOTTOM_CENTER);
    links.setPrefHeight(VBox.USE_COMPUTED_SIZE);
    VBox.setMargin(links, new Insets(10, 10, 10, 10));
    links.getStyleClass().add("link-view");
    for (Link link : currentPassage.getLinks()) {
      Button button = new Button(link.getText());
      button.setFocusTraversable(true);
      button.getStyleClass().add("link-button");
      button.setOnAction(
          e -> {
            currentPassage = currentGame.go(link);
            createContentString();
          });
      links.getChildren().add(button);
    }
    AnchorPane anchorPane = new AnchorPane();
    AnchorPane.setBottomAnchor(links, 0.0);
    AnchorPane.setLeftAnchor(links, 0.0);
    AnchorPane.setRightAnchor(links, 0.0);
    anchorPane.getChildren().add(links);

    return anchorPane;
  }

  private void addSceneListener(BorderPane root) {
    root.sceneProperty()
        .addListener(
            new ChangeListener<>() {
              @Override
              public void changed(
                  ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                if (newValue != null) {
                  createContentString();
                  root.sceneProperty().removeListener(this);
                }
              }
            });
  }
}

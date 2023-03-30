package edu.ntnu.idatt2001.paths.view;

import edu.ntnu.idatt2001.paths.model.story.Link;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Builder;
import javafx.util.Duration;

public class Game implements Builder<Region> {

  private static final StringProperty contentBar = new SimpleStringProperty();
  private static Passage currentPassage;
  private static edu.ntnu.idatt2001.paths.model.game.Game currentGame;

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
    return root;
  }

  private Node createBottom() {
    VBox bottom = new VBox();
    ScrollPane contentBarText = createContentBar();
    contentBarText.getStyleClass().add("content-bar");
    bottom.getChildren().add(contentBarText);
    VBox.setVgrow(contentBarText, Priority.ALWAYS);
    bottom.setAlignment(Pos.BOTTOM_CENTER);
    return bottom;
  }

  private Node createCenter() {
    StackPane center = new StackPane();
    center.getChildren().add(createLinkChoices());
    return center;
  }

  private ScrollPane createContentBar() {
    Text contentBarText = new Text();
    contentBarText.textProperty().bind(contentBar);

    TextFlow textFlow = new TextFlow(contentBarText);
    textFlow.getStyleClass().add("text-flow");

    ScrollPane scrollPane = new ScrollPane(textFlow);
    scrollPane.getStyleClass().add("scroll-pane");
    textFlow
        .prefWidthProperty()
        .bind(Bindings.selectDouble(scrollPane.viewportBoundsProperty(), "width"));
    return scrollPane;
  }

  private void createContentString() {
    contentBar.set("");
    char[] charArray = currentPassage.getContent().toCharArray();
    AtomicInteger index = new AtomicInteger(0);

    Timeline timeline = new Timeline();
    KeyFrame keyFrame =
        new KeyFrame(
            Duration.millis(50),
            event -> {
              if (index.get() < charArray.length) {
                contentBar.set(contentBar.getValue() + charArray[index.get()]);
                index.incrementAndGet();
              }
            });

    timeline.getKeyFrames().add(keyFrame);
    timeline.setCycleCount(charArray.length);
    timeline.play();
  }

  private Node createLinkChoices() {
    VBox choiceBox = new VBox();
    for (Link link : currentPassage.getLinks()) {
      Button button = new Button(link.getText());
      button.setOnAction(
          e -> {
            currentPassage = currentGame.go(link);
            createContentString();
          });
      choiceBox.getChildren().add(button);
    }
    return choiceBox;
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

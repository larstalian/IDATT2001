package edu.ntnu.idatt2001.paths.view;

import edu.ntnu.idatt2001.paths.model.story.Link;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import java.util.Objects;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Builder;
import javafx.util.Duration;

public class Game implements Builder<Region> {

  private static final StringProperty contentBar = new SimpleStringProperty();
  private static final AtomicBoolean isAnimationSkipped = new AtomicBoolean(false);
  private static final String IMAGE_PATH = "/images/";
  private static final String SOUND_PATH = "/sound/";
  private static final String STORIES_PATH = "/stories/";
  private static final String IMAGE_EXTENSION = ".png";
  private static final String SOUND_EXTENSION = ".mp3";
  private static Passage currentPassage;
  private static edu.ntnu.idatt2001.paths.model.game.Game currentGame;
  private final VBox links = new VBox();
  private Label skipLabel;
  private BorderPane root;
  private MediaPlayer mediaPlayer;

  public static void setCurrentGame(edu.ntnu.idatt2001.paths.model.game.Game chosenGame) {
    currentGame = chosenGame;
  }

  public BorderPane getRoot() {
    return root;
  }

  @Override
  public Region build() {
    currentPassage = currentGame.begin();
    root = createRoot();
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
    return new AnchorPane();
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
                links.setVisible(true);
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
    links.setAlignment(Pos.BOTTOM_CENTER);
    links.setPrefHeight(VBox.USE_COMPUTED_SIZE);
    VBox.setMargin(links, new Insets(10, 10, 10, 10));
    links.getStyleClass().add("link-view");
    updateLinkChoices();
    AnchorPane anchorPane = new AnchorPane();
    AnchorPane.setBottomAnchor(links, 0.0);
    AnchorPane.setLeftAnchor(links, 0.0);
    AnchorPane.setRightAnchor(links, 0.0);
    anchorPane.getChildren().add(links);

    return anchorPane;
  }

  private void updateLinkChoices() {
    links.getChildren().clear();
    for (Link link : currentPassage.getLinks()) {
      Button button = new Button(link.getText());
      button.setFocusTraversable(true);
      button.getStyleClass().add("link-button");
      button.setOnAction(
          e -> {
            currentPassage = currentGame.go(link);
            createContentString();
            updateLinkChoices();
            updateBackground();
            updateMusic();
          });
      links.getChildren().add(button);
    }
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
                  updateLinkChoices();
                  root.sceneProperty().removeListener(this);
                  setupArrowKeysNavigation(newValue);
                }
              }
            });
  }

  private void setupArrowKeysNavigation(Scene scene) {
    AtomicInteger selectedIndex = new AtomicInteger(0);
    scene.addEventFilter(
        KeyEvent.KEY_PRESSED,
        keyEvent -> {
          int previousIndex = selectedIndex.get();
          switch (keyEvent.getCode()) {
            case UP -> selectedIndex.updateAndGet(
                i -> (i - 1 + links.getChildren().size()) % links.getChildren().size());
            case DOWN -> selectedIndex.updateAndGet(i -> (i + 1) % links.getChildren().size());
            case ENTER -> {
              Button selectedButton = (Button) links.getChildren().get(selectedIndex.get());
              selectedButton.fire();
            }
            case SPACE -> isAnimationSkipped.set(true);
            default -> {}
          }
          if (previousIndex != selectedIndex.get()) {
            links.getChildren().get(previousIndex).getStyleClass().remove("link-button-selected");
            links
                .getChildren()
                .get(selectedIndex.get())
                .getStyleClass()
                .add("link-button-selected");
          }
          keyEvent.consume();
        });
  }

  private void updateBackground() {
    String backgroundImageUrl;
    if (hasBackground(currentPassage)) {
      String path = "/stories/" + currentGame.getStory().getTitle() + "/images/";
      String fileName = currentPassage.getTitle().toLowerCase() + IMAGE_EXTENSION;
      backgroundImageUrl =
          Objects.requireNonNull(getClass().getResource(path + fileName)).toExternalForm();
    } else {
      String defaultBackground =
          currentPassage.getMood().toString().toLowerCase() + IMAGE_EXTENSION;
      backgroundImageUrl =
          Objects.requireNonNull(getClass().getResource(IMAGE_PATH + defaultBackground))
              .toExternalForm();
    }
    setBackgroundImageUrl(backgroundImageUrl);
  }

  private void setBackgroundImageUrl(String backgroundImageUrl) {
    getRoot().setStyle("-fx-background-image: url('" + backgroundImageUrl + "');");
  }

  private boolean hasBackground(Passage passage) {
    String path = STORIES_PATH + currentGame.getStory().getTitle() + IMAGE_PATH;
    String fileName = passage.getTitle() + IMAGE_EXTENSION;
    return getClass().getResource(path + fileName) != null;
  }

  private boolean hasMusic(Passage passage) {
    String path = SOUND_PATH + currentGame.getStory().getTitle() + SOUND_PATH;
    String fileName = passage.getTitle() + SOUND_EXTENSION;
    return getClass().getResource(path + fileName) != null;
  }

  private void updateMusic() {
    String musicUrl;
    if (hasMusic(currentPassage)) {
      String path = SOUND_PATH + currentGame.getStory().getTitle() + "/sound/";
      String fileName = currentPassage.getTitle().toLowerCase() + SOUND_EXTENSION;
      musicUrl = Objects.requireNonNull(getClass().getResource(path + fileName)).toExternalForm();
    } else {
      musicUrl = SOUND_PATH + currentPassage.getMood().toString().toLowerCase() + SOUND_EXTENSION;
    }
    playBackgroundMusic(musicUrl);
  }

  private void playBackgroundMusic(String musicFileUrl) {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
      mediaPlayer.dispose();
    }

    String musicUrl = Objects.requireNonNull(getClass().getResource(musicFileUrl)).toExternalForm();
    Media media = new Media(musicUrl);
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.play();
  }
}

package edu.ntnu.idatt2001.paths.view;

import static edu.ntnu.idatt2001.paths.model.filehandlers.json.GameFileHandler.getGameFiles;
import static edu.ntnu.idatt2001.paths.view.Game.setCurrentGame;
import static edu.ntnu.idatt2001.paths.view.Widgets.createAlert;

import edu.ntnu.idatt2001.paths.model.filehandlers.json.GameFileHandler;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import org.apache.commons.io.FilenameUtils;

public class LoadGame implements Builder<Region> {

  private final ComboBox<String> saveSelect;
  private final Button loadButton;
  private final Button goBackButton;

  public LoadGame() {
    saveSelect = new ComboBox<>();
    loadButton = new Button("Load Game");
    goBackButton = new Button("Go Back");
  }

  @Override
  public Region build() {
    BorderPane results = new BorderPane();
    results.getStyleClass().add("main-menu");
    results.setCenter(createCenter());
    configureSaveSelect();
    configureLoadButton();
    configureGoBackButton();
    return results;
  }

  private Node createCenter() {
    StackPane results = new StackPane();
    results.getChildren().add(createButtonVBox());
    return results;
  }

  private Node createButtonVBox() {
    VBox results = new VBox();
    results.getChildren().add(saveSelect);
    results.getChildren().add(loadButton);
    results.getChildren().add(goBackButton);
    results.getStyleClass().add("button-vbox");
    return results;
  }

  private void configureLoadButton() {
    loadButton.setOnAction(
        event -> {
          Alert alert =
              new Alert(Alert.AlertType.NONE, "Please enter a valid save file", ButtonType.OK);

          if (saveSelect.getValue() == null) {
            alert.showAndWait();

          } else {
            GameFileHandler gameFileHandler = new GameFileHandler();

            try {
              setCurrentGame(
                  gameFileHandler.loadGameFromFile(
                      FilenameUtils.removeExtension(saveSelect.getValue())).getGame());

              Region gameRoot = new Game().build();
              loadButton.getScene().setRoot(gameRoot);

            } catch (IOException e) {
              alert = createAlert("Error", "Unexpected error:", e.getMessage());
              alert.show();
            }
          }
        });
  }

  private void configureGoBackButton() {
    goBackButton.setOnAction(
        event -> {
          Region newGameRoot = new MainMenu().build();
          goBackButton.getScene().setRoot(newGameRoot);
        });
  }

  private void configureSaveSelect() {
    try {
      saveSelect.getItems().addAll(getGameFiles());
    } catch (IOException e) {
      Alert alert = createAlert("Error", "Unexpected error:", e.getMessage());
      alert.show();
    }
  }
}

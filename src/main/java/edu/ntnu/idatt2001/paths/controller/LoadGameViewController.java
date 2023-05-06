package edu.ntnu.idatt2001.paths.controller;

import static edu.ntnu.idatt2001.paths.view.util.Widgets.createAlert;

import edu.ntnu.idatt2001.paths.model.filehandlers.json.GameData;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.GameFileHandler;
import edu.ntnu.idatt2001.paths.view.LoadGameView;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import org.apache.commons.io.FilenameUtils;

public class LoadGameViewController {

  private final LoadGameView loadGameView;

  public LoadGameViewController() {
    loadGameView = new LoadGameView();
    configureLoadButton();
    configureGoBackButton();
    configureSaveSelect();
  }

  public Region getRoot() {
    return loadGameView.getRoot();
  }

  private void configureLoadButton() {
    loadGameView
        .getLoadButton()
        .setOnAction(
            event -> {
              Alert alert =
                  new Alert(Alert.AlertType.NONE, "Please enter a valid save file", ButtonType.OK);

              if (loadGameView.getSaveSelect().getValue() == null) {
                alert.showAndWait();

              } else {
                GameFileHandler gameFileHandler = new GameFileHandler();

                try {
                  GameData gameData =
                      gameFileHandler.loadGameFromFile(
                          FilenameUtils.removeExtension(loadGameView.getSaveSelect().getValue()));

                  Region gameRoot = new GameViewController(gameData).getRoot();
                  loadGameView.getLoadButton().getScene().setRoot(gameRoot);

                } catch (IOException e) {
                  alert = createAlert("Error", "Unexpected error:", e.getMessage());
                  alert.show();
                }
              }
            });
  }

  private void configureGoBackButton() {
    loadGameView
        .getGoBackButton()
        .setOnAction(
            event -> {
              Region newGameRoot = new MainMenuViewController().getRoot();
              loadGameView.getLoadButton().getScene().setRoot(newGameRoot);
            });
  }

  private void configureSaveSelect() {
    try {
      loadGameView.getSaveSelect().getItems().addAll(GameFileHandler.getGameFiles());
    } catch (IOException e) {
      Alert alert = createAlert("Error", "Unexpected error:", e.getMessage());
      alert.show();
    }
  }
}

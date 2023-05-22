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

/**
 * This class is the controller for the Load Game screen of the Paths application. This screen
 * allows users to select a saved game from a dropdown menu and load it for playing. The
 * LoadGameViewController class is responsible for handling actions performed on this screen.
 */
public class LoadGameViewController {

  private final LoadGameView loadGameView;

  /**
   * Constructs a new LoadGameViewController. This involves creating the view and configuring all
   * the widgets in the view.
   */
  public LoadGameViewController() {
    loadGameView = new LoadGameView();
    configureLoadButton();
    configureGoBackButton();
    configureSaveSelect();
  }

  /**
   * Returns the root node of the view that this controller is controlling.
   *
   * @return the root node of the view
   */
  public Region getRoot() {
    return loadGameView.getRoot();
  }

  /**
   * Configures the Load button in the view. This method sets an action handler for the Load button,
   * which is executed when the button is clicked. The action handler tries to load the game
   * selected in the dropdown menu. If no game is selected or an error occurs while loading the
   * game, an alert dialog is shown.
   */
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

  /**
   * Configures the Go Back button in the view. This method sets an action handler for the Go Back
   * button, which is executed when the button is clicked. The action handler changes the current
   * scene to the Main Menu.
   */
  private void configureGoBackButton() {
    loadGameView
        .getGoBackButton()
        .setOnAction(
            event -> {
              Region newGameRoot = new MainMenuViewController().getRoot();
              loadGameView.getLoadButton().getScene().setRoot(newGameRoot);
            });
  }

  /**
   * Configures the dropdown menu for selecting a saved game. This method populates the dropdown
   * menu with the names of all saved games. If an error occurs while retrieving the names of saved
   * games, an alert dialog is shown.
   */
  private void configureSaveSelect() {
    try {
      loadGameView.getSaveSelect().getItems().addAll(GameFileHandler.getGameFiles());
    } catch (IOException e) {
      Alert alert = createAlert("Error", "Unexpected error:", e.getMessage());
      alert.show();
    }
  }
}

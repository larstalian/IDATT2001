package edu.ntnu.idatt2001.paths.controller;

import edu.ntnu.idatt2001.paths.model.media.SoundHandler;
import edu.ntnu.idatt2001.paths.view.MainMenuView;
import edu.ntnu.idatt2001.paths.view.util.Widgets;
import javafx.application.Platform;
import javafx.scene.layout.Region;

public class MainMenuController {
  private final MainMenuView mainMenuView;

  public MainMenuController() {
    mainMenuView = new MainMenuView();
    configureNewGameButton();
    configureLoadGameButton();
    configureStoriesButton();
    configureExitButton();
    SoundHandler.getInstance().playMenuMusic();
  }

  public Region getRoot() {
    return mainMenuView.getRoot();
  }

  private void configureNewGameButton() {
    mainMenuView
        .getNewGameButton()
        .setOnAction(
            event -> {
              NewGameController newGameController = new NewGameController();
              Region newGameRoot = newGameController.getRoot();
              mainMenuView.getNewGameButton().getScene().setRoot(newGameRoot);
            });
  }

  private void configureLoadGameButton() {
    mainMenuView
        .getLoadGameButton()
        .setOnAction(
            event -> {
              Region newGameRoot = new LoadGameController().getRoot();
              mainMenuView.getLoadGameButton().getScene().setRoot(newGameRoot);
            });
  }

  private void configureStoriesButton() {
    mainMenuView
        .getStoriesButton()
        .setOnAction(
            event -> {
              try {
                Region newGameRoot = new StoriesController().getRoot();
                mainMenuView.getStoriesButton().getScene().setRoot(newGameRoot);
              } catch (NullPointerException e) {
                e.printStackTrace();
                Widgets.createAlert(
                        "Error",
                        "There are no stories",
                        "Are you sure the stories are saved in the correct folder?")
                    .showAndWait();
              }
            });
  }

  private void configureExitButton() {
    mainMenuView.getExitButton().setOnAction(event -> Platform.exit());
  }
}

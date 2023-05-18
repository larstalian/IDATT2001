package edu.ntnu.idatt2001.paths.controller;

import edu.ntnu.idatt2001.paths.model.media.SoundHandler;
import edu.ntnu.idatt2001.paths.view.MainMenuView;
import edu.ntnu.idatt2001.paths.view.util.Widgets;
import javafx.application.Platform;
import javafx.scene.layout.Region;

/**
 * Represents the controller for the main menu view in the Paths game. This controller is
 * responsible for managing the behavior of the main menu view.
 */
public class MainMenuViewController {
  private final MainMenuView mainMenuView;

  /**
   * Constructs a MainMenuViewController object. Initializes the main menu view and configures all
   * the buttons.
   */
  public MainMenuViewController() {
    mainMenuView = new MainMenuView();
    configureNewGameButton();
    configureLoadGameButton();
    configureStoriesButton();
    configureCreateStoryButton();
    configureExitButton();
    SoundHandler.getInstance().playMenuMusic();
  }

  /**
   * Returns the root node of the main menu view.
   *
   * @return a Region object representing the root of the main menu view.
   */
  public Region getRoot() {
    return mainMenuView.getRoot();
  }

  /** Configures the action of the new game button. */
  private void configureNewGameButton() {
    mainMenuView
        .getNewGameButton()
        .setOnAction(
            event -> {
              NewGameViewController newGameViewController = new NewGameViewController();
              Region newGameRoot = newGameViewController.getRoot();
              mainMenuView.getNewGameButton().getScene().setRoot(newGameRoot);
            });
  }

  /** Configures the action of the load game button. */
  private void configureLoadGameButton() {
    mainMenuView
        .getLoadGameButton()
        .setOnAction(
            event -> {
              Region newGameRoot = new LoadGameViewController().getRoot();
              mainMenuView.getLoadGameButton().getScene().setRoot(newGameRoot);
            });
  }

  /** Configures the action of the stories button. */
  private void configureStoriesButton() {
    mainMenuView
        .getStoriesButton()
        .setOnAction(
            event -> {
              try {
                Region newGameRoot = new StoriesViewController().getRoot();
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

  /** Configures the action of the create story button. */
  private void configureCreateStoryButton() {
    mainMenuView
        .getCreateStoryButton()
        .setOnAction(
            event ->
                mainMenuView
                    .getCreateStoryButton()
                    .getScene()
                    .setRoot(new NewStoryViewController().getRoot()));
  }

  /** Configures the action of the exit button. */
  private void configureExitButton() {
    mainMenuView.getExitButton().setOnAction(event -> Platform.exit());
  }
}

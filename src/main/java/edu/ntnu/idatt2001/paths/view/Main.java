package edu.ntnu.idatt2001.paths.view;

import edu.ntnu.idatt2001.paths.controller.MainMenuViewController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * This is the main class for the Paths application. This class is responsible for launching the
 * application and setting up the primary stage.
 */
public class Main extends Application {

  /**
   * The main entry point for the application. This method is called by the JVM to start the
   * application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * The main entry point for JavaFX applications. This method is called by the launch method in the
   * main function. It sets up the primary stage for the application, including the scene, size, and
   * stylesheet.
   *
   * @param primaryStage the primary stage for this application, onto which the application scene
   *                     can be set.
   */
  @Override
  public void start(Stage primaryStage) {
    Region sceneRoot = new MainMenuViewController().getRoot();
    Scene scene = new Scene(sceneRoot);
    primaryStage.setScene(scene);
    scene.getStylesheets().add("/css/default.css");
    primaryStage.setMinHeight(800);
    primaryStage.setMinWidth(800);
    primaryStage.show();
  }
}

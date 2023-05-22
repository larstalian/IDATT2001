package edu.ntnu.idatt2001.paths.view;

import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import lombok.Getter;

/**
 * This class represents the view for customizing game options. It contains text fields for the user
 * to input their desired options for the game.
 */
public class CustomizeGameOptionsView {

  @Getter
  private final TextField healthGoal;
  @Getter
  private final TextField scoreGoal;
  @Getter
  private final TextField goldGoal;
  @Getter
  private final TextField inventoryGoal;
  @Getter
  private final TextField startingHealth;
  @Getter
  private final TextField startingScore;
  @Getter
  private final TextField startingGold;
  @Getter
  private final TextField startingInventory;

  /**
   * Constructs a new CustomizeGameOptionsView. This involves initializing the text fields for game
   * options.
   */
  public CustomizeGameOptionsView() {
    healthGoal = new TextField();
    healthGoal.getStyleClass().add("default-text-field");

    scoreGoal = new TextField();
    scoreGoal.getStyleClass().add("default-text-field");

    goldGoal = new TextField();
    goldGoal.getStyleClass().add("default-text-field");

    inventoryGoal = new TextField();
    inventoryGoal.getStyleClass().add("default-text-field");

    startingHealth = new TextField();
    startingHealth.getStyleClass().add("default-text-field");

    startingScore = new TextField();
    startingScore.getStyleClass().add("default-text-field");

    startingGold = new TextField();
    startingGold.getStyleClass().add("default-text-field");

    startingInventory = new TextField();
    startingInventory.getStyleClass().add("default-text-field");
  }

  /**
   * Creates a dialog for the game options customization view.
   *
   * @return the created dialog
   */
  public Dialog<Void> createDialog() {
    Dialog<Void> popup = new Dialog<>();
    popup.setTitle("Customize Game Options");

    HBox hbox = new HBox();
    hbox.getChildren().add(createCustomPlayerSettings());
    hbox.getChildren().add(createCustomGameSettings());

    popup.getDialogPane().setContent(hbox);

    return popup;
  }

  /**
   * Creates a node representing the customizable game settings. This includes the health goal,
   * score goal, gold goal, and inventory goal.
   *
   * @return the created node
   */
  private Node createCustomGameSettings() {
    GridPane results = new GridPane();
    results.add(new Label("Game Settings"), 0, 0);
    results.add(new Label("Health goal:"), 0, 1);
    results.add(new Label("Score goal:"), 0, 2);
    results.add(new Label("Gold goal"), 0, 3);
    results.add(new Label("Inventory goal:"), 0, 4);

    results.add(healthGoal, 1, 1);
    results.add(scoreGoal, 1, 2);
    results.add(goldGoal, 1, 3);
    results.add(inventoryGoal, 1, 4);

    return results;
  }

  /**
   * Creates a node representing the customizable player settings. This includes the starting
   * health, starting score, starting gold, and starting inventory.
   *
   * @return the created node
   */
  private Node createCustomPlayerSettings() {
    GridPane results = new GridPane();
    results.add(new Label("Player Settings"), 0, 0);
    results.add(new Label("Starting health:"), 0, 1);
    results.add(new Label("Starting score:"), 0, 2);
    results.add(new Label("Starting gold"), 0, 3);
    results.add(new Label("Starting inventory items:"), 0, 4);

    results.add(startingHealth, 1, 1);
    results.add(startingScore, 1, 2);
    results.add(startingGold, 1, 3);
    results.add(startingInventory, 1, 4);

    return results;
  }
}

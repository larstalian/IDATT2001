package edu.ntnu.idatt2001.paths.controller;

import edu.ntnu.idatt2001.paths.model.goals.*;
import edu.ntnu.idatt2001.paths.view.CustomizeGameOptionsView;
import java.util.List;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * This class is the controller for the game options customization view. It handles the operations
 * related to user interactions with the game options.
 */
public class CustomizeGameOptionsViewController {

  private final CustomizeGameOptionsView customizeGameOptionsView;
  private boolean isModified;

  /**
   * Constructs a new CustomizeGameOptionsViewController. This involves initializing the
   * customization view and setting the modified flag to false.
   */
  public CustomizeGameOptionsViewController() {
    customizeGameOptionsView = new CustomizeGameOptionsView();
    isModified = false;
  }

  /**
   * Checks if the game options have been modified by the user.
   *
   * @return true if the game options have been modified, false otherwise
   */
  public boolean isModified() {
    return isModified;
  }

  /**
   * Displays the game options customization view to the user. This method also sets up the buttons
   * and their actions for the view.
   */
  public void show() {
    Dialog<Void> popup = customizeGameOptionsView.createDialog();
    popup.getDialogPane().getStyleClass().add("/css/default.css");

    ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    popup.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

    popup.setResultConverter(
        e -> {
          if (e == saveButtonType) {
            isModified = true;
          }
          return null;
        });

    popup.showAndWait();
  }

  /**
   * Retrieves the health goal entered by the user.
   *
   * @return the health goal as an integer
   */
  public int getHealthGoal() {
    String healthGoal = customizeGameOptionsView.getHealthGoal().getText();
    return healthGoal.isBlank() ? 0 : Integer.parseInt(healthGoal);
  }

  /**
   * Retrieves the score goal entered by the user.
   *
   * @return the score goal as an integer
   */
  public int getScoreGoal() {
    String scoreGoal = customizeGameOptionsView.getScoreGoal().getText();
    return scoreGoal.isBlank() ? 0 : Integer.parseInt(scoreGoal);
  }

  /**
   * Retrieves the gold goal entered by the user.
   *
   * @return the gold goal as an integer
   */
  public int getGoldGoal() {
    String goldGoal = customizeGameOptionsView.getGoldGoal().getText();
    return goldGoal.isBlank() ? 0 : Integer.parseInt(goldGoal);
  }

  /**
   * Retrieves the inventory goal entered by the user.
   *
   * @return the inventory goal as a string array
   */
  public String[] getInventoryGoal() {
    String inventoryGoal = customizeGameOptionsView.getInventoryGoal().getText();
    return inventoryGoal.isBlank() ? new String[0] : inventoryGoal.split(" ");
  }

  /**
   * Retrieves the starting health entered by the user.
   *
   * @return the starting health as an integer
   */
  public int getStartingHealth() {
    String startingHealth = customizeGameOptionsView.getStartingHealth().getText();
    return startingHealth.isBlank() ? 100 : Integer.parseInt(startingHealth);
  }

  /**
   * Retrieves the starting score entered by the user.
   *
   * @return the starting score as an integer
   */
  public int getStartingScore() {
    String startingScore = customizeGameOptionsView.getStartingScore().getText();
    return startingScore.isBlank() ? 0 : Integer.parseInt(startingScore);
  }

  /**
   * Retrieves the starting gold entered by the user.
   *
   * @return the starting gold as an integer
   */
  public int getStartingGold() {
    String startingGold = customizeGameOptionsView.getStartingGold().getText();
    return startingGold.isBlank() ? 0 : Integer.parseInt(startingGold);
  }

  /**
   * Retrieves the starting inventory entered by the user.
   *
   * @return the starting inventory as a string array
   */
  public String[] getStartingInventory() {
    String startingInventory = customizeGameOptionsView.getStartingInventory().getText();
    return startingInventory.isBlank() ? new String[0] : startingInventory.split(" ");
  }

  /**
   * Retrieves the goals entered by the user.
   *
   * @return the list of goals
   */
  public List<Goal> getGoals() {
    return List.of(
        new HealthGoal(getHealthGoal()),
        new ScoreGoal(getScoreGoal()),
        new GoldGoal(getGoldGoal()),
        new InventoryGoal(List.of(getInventoryGoal())));
  }
}

package edu.ntnu.idatt2001.paths.controller;

import edu.ntnu.idatt2001.paths.model.goals.*;
import edu.ntnu.idatt2001.paths.view.CustomizeGameOptionsView;
import java.util.List;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class CustomizeGameOptionsViewController {

  private final CustomizeGameOptionsView customizeGameOptionsView;
  private boolean isModified;

  public CustomizeGameOptionsViewController() {
    customizeGameOptionsView = new CustomizeGameOptionsView();
    isModified = false;
  }

  public boolean isModified() {
    return isModified;
  }

  public void show() {
    Dialog<Void> popup = customizeGameOptionsView.createDialog();

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

  public int getHealthGoal() {
    String healthGoal = customizeGameOptionsView.getHealthGoal().getText();
    return healthGoal.isBlank() ? 0 : Integer.parseInt(healthGoal);
  }

  public int getScoreGoal() {
    String scoreGoal = customizeGameOptionsView.getScoreGoal().getText();
    return scoreGoal.isBlank() ? 0 : Integer.parseInt(scoreGoal);
  }

  public int getGoldGoal() {
    String goldGoal = customizeGameOptionsView.getGoldGoal().getText();
    return goldGoal.isBlank() ? 0 : Integer.parseInt(goldGoal);
  }

  public String[] getInventoryGoal() {
    String inventoryGoal = customizeGameOptionsView.getInventoryGoal().getText();
    return inventoryGoal.isBlank() ? new String[0] : inventoryGoal.split(" ");
  }

  public int getStartingHealth() {
    String startingHealth = customizeGameOptionsView.getStartingHealth().getText();
    return startingHealth.isBlank() ? 100 : Integer.parseInt(startingHealth);
  }

  public int getStartingScore() {
    String startingScore = customizeGameOptionsView.getStartingScore().getText();
    return startingScore.isBlank() ? 0 : Integer.parseInt(startingScore);
  }

  public int getStartingGold() {
    String startingGold = customizeGameOptionsView.getStartingGold().getText();
    return startingGold.isBlank() ? 0 : Integer.parseInt(startingGold);
  }

  public String[] getStartingInventory() {
    String startingInventory = customizeGameOptionsView.getStartingInventory().getText();
    return startingInventory.isBlank() ? new String[0] : startingInventory.split(" ");
  }

  public List<Goal> getGoals() {
    return List.of(
        new HealthGoal(getHealthGoal()),
        new ScoreGoal(getScoreGoal()),
        new GoldGoal(getGoldGoal()),
        new InventoryGoal(List.of(getInventoryGoal())));
  }
}

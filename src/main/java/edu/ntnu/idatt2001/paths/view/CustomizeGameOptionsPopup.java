package edu.ntnu.idatt2001.paths.view;

import edu.ntnu.idatt2001.paths.model.goals.*;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CustomizeGameOptionsPopup {

  private final TextField healthGoal;
  private final TextField scoreGoal;
  private final TextField goldGoal;
  private final TextField inventoryGoal;
  private final TextField startingHealth;
  private final TextField startingScore;
  private final TextField startingGold;
  private final TextField startingInventory;
  private boolean isModified;

  public CustomizeGameOptionsPopup() {
    healthGoal = new TextField();
    scoreGoal = new TextField();
    goldGoal = new TextField();
    inventoryGoal = new TextField();
    startingHealth = new TextField();
    startingScore = new TextField();
    startingGold = new TextField();
    startingInventory = new TextField();
    isModified = false; 
  }

  public boolean isModified() {
    return isModified;
  }

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

  public void show() {
    Dialog<Void> popup = new Dialog<>();
    popup.setTitle("Customize Game Options");

    HBox hbox = new HBox();
    hbox.getChildren().add(createCustomPlayerSettings());
    hbox.getChildren().add(createCustomGameSettings());

    popup.getDialogPane().setContent(hbox);

    ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    popup.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

    popup.setResultConverter(e -> {
      if (e == saveButtonType) {
        isModified = true;
      }
      return null;
    });

    popup.showAndWait();
  }

  public int getHealthGoal() {
    return healthGoal.getText().isBlank()  ? 0 : Integer.parseInt(healthGoal.getText());
  }

  public int getScoreGoal() {
    return scoreGoal.getText().isBlank() ? 0 : Integer.parseInt(scoreGoal.getText());
  }

  public int getGoldGoal() {
    return goldGoal.getText().isBlank() ? 0 : Integer.parseInt(goldGoal.getText());
  }

  public String[] getInventoryGoal() {
    return inventoryGoal.getText().isBlank() ? new String[0] : inventoryGoal.getText().split(" ");
  }

  public int getStartingHealth() {
    return startingHealth.getText().isBlank() ? 100 : Integer.parseInt(startingHealth.getText());
  }

  public int getStartingScore() {
    return startingScore.getText().isBlank() ? 0 : Integer.parseInt(startingScore.getText());
  }

  public int getStartingGold() {
    return startingGold.getText().isBlank() ? 0 : Integer.parseInt(startingGold.getText());
  }

  public String[] getStartingInventory() {
    return startingInventory.getText().isBlank()
        ? new String[0]
        : startingInventory.getText().split(" ");
  }
  
  public List<Goal> getGoals(){
    return List.of(new HealthGoal(getHealthGoal()), new ScoreGoal(getScoreGoal()), new GoldGoal(getGoldGoal()), new InventoryGoal(List.of(getInventoryGoal())));
  }}

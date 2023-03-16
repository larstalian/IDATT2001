package edu.ntnu.idatt2001.paths.actions;

public class ActionFactory {

    /**
     * Creates an Action instance based on the provided action name and value.
     *
     * @param actionName the name of the action
     * @param actionValue the value associated with the action
     * @return an instance of the Action class corresponding to the action name
     * @throws IllegalArgumentException if the action name is not recognized or the value is invalid
     */
    public static Action createAction(String actionName, String actionValue) {
        switch (actionName) {
            case "goldChange":
                int gold = Integer.parseInt(actionValue);
                return new GoldAction(gold);
            case "healthChange":
                int health = Integer.parseInt(actionValue);
                return new HealthAction(health);
            case "inventoryChange":
                return new InventoryAction(actionValue);
            case "scoreChange":
                int points = Integer.parseInt(actionValue);
                return new ScoreAction(points);
            default:
                throw new IllegalArgumentException("Unrecognized action name: " + actionName);
        }
    }
}
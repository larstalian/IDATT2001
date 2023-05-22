package edu.ntnu.idatt2001.paths.view.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This utility class contains methods for creating and managing JavaFX widgets such as alerts.
 *
 * <p>The class provides static methods, meaning that they can be called directly on the class
 * without needing to instantiate an object. Currently, the class provides a method for creating an
 * Alert with specified title, header, and content. More widget-related methods can be added as
 * necessary.
 *
 * <p>As a utility class, the Widgets class is not meant to be instantiated. All of its methods
 * should be stateless and functional, meaning they don't rely on or change any instance-level
 * data.
 */
public class Widgets {

  private Widgets() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Creates an Alert with the given title, header, and content.
   *
   * @param title   the title for the alert
   * @param header  the header text for the alert
   * @param content the content text for the alert. If empty, no content will be added.
   * @return the Alert created with the specified parameters
   */
  public static Alert createAlert(String title, String header, String content) {
    Alert alert = new Alert(Alert.AlertType.NONE, "", ButtonType.CLOSE);
    alert.setTitle(title);
    alert.setHeaderText(header);

    if (!content.isEmpty()) {
      TextArea textArea = new TextArea(content);
      textArea.setEditable(false);
      textArea.setWrapText(true);
      alert.getDialogPane().setContent(textArea);
    }

    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
    alertStage.addEventFilter(
        WindowEvent.WINDOW_CLOSE_REQUEST,
        event -> {
          alert.setResult(ButtonType.CLOSE);
          alert.close();
        });

    return alert;
  }
}

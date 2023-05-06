package edu.ntnu.idatt2001.paths.view.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Widgets {

  /**
   * Creates an Alert with the given title, header, and content.
   *
   * @param title the title for the alert
   * @param header the header text for the alert
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

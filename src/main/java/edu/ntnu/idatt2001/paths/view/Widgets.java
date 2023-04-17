package edu.ntnu.idatt2001.paths.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Widgets {

  public static Alert createAlert(String title, String header, String content) {
    Alert alert = new Alert(Alert.AlertType.NONE, "", ButtonType.OK);
    alert.setTitle(title);
    alert.setHeaderText(header);

    if (!content.isEmpty()) {
      TextArea textArea = new TextArea(content);
      textArea.setEditable(false);
      textArea.setWrapText(true);
      alert.getDialogPane().setContent(textArea);
    }

    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
    alertStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
      alert.setResult(ButtonType.CLOSE);
      alert.close();
    });

    return alert;
  }
}

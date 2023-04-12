package edu.ntnu.idatt2001.paths.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

public class Widgets {

  public static Alert createAlert(String title, String header, String content) {
    Alert alert = new Alert(Alert.AlertType.NONE, content, ButtonType.OK);
    alert.setTitle(title);
    alert.setHeaderText(header);
    TextArea textArea = new TextArea(content);
    textArea.setEditable(false);
    textArea.setWrapText(true);

    alert.getDialogPane().setContent(textArea);
    return alert;
  }
}

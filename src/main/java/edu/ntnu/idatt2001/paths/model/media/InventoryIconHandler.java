package edu.ntnu.idatt2001.paths.model.media;

import java.net.URL;
import javafx.scene.image.Image;

public class InventoryIconHandler {
  private static final String ICON_PATH = "/images.inventory-cons/";
  private static final String ICON_EXTENSION = ".png";

  private InventoryIconHandler() {}

  public static Image getIcon(String icon) {
    String path = ICON_PATH + icon + ICON_EXTENSION;
    URL resource = InventoryIconHandler.class.getResource(icon);
    return resource == null ? null : new Image(resource.toExternalForm());
  }
}

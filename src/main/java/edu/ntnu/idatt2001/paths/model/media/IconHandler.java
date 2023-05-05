package edu.ntnu.idatt2001.paths.model.media;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.image.Image;

/**
 * The {@code InventoryIconHandler} class is responsible for loading inventory icons based on the
 * icon name. It supports both PNG and JPEG image formats.
 *
 * <p>Usage: To obtain an inventory icon, call the static {@code getIcon} method with the desired
 * icon name as a parameter.
 *
 * <p>Note: This class is not meant to be instantiated.
 */
public class IconHandler {
  private static final Path INVENTORY_ICON_PATH = Path.of("src/main/resources/images/inventory-icons/");
  private static final Path ICON_PATH = Path.of("src/main/resources/images/icons/");

  /** Private constructor to prevent instantiation. */
  private IconHandler() {}

  /**
   * Retrieves the icon image for the specified icon name. The icon name should not include the file
   * extension. This method supports both PNG and JPEG image formats.
   *
   * @param icon the name of the icon to retrieve, without the file extension
   * @return the {@code Image} object representing the icon, or {@code null} 
   * if the icon is not found
   * @throws IOException if an I/O error occurs
   */
  public static Image getInventoryIcon(final String icon) throws IOException {
    String iconNameLowerCase = icon.toLowerCase();
    Path pngPath = INVENTORY_ICON_PATH.resolve(iconNameLowerCase + ".png");
    Path jpegPath = INVENTORY_ICON_PATH.resolve(iconNameLowerCase + ".jpeg");

    if (Files.exists(pngPath)) {
      return new Image(Files.newInputStream(pngPath));
    } else if (Files.exists(jpegPath)) {
      return new Image(Files.newInputStream(jpegPath));
    } else {
      return null;
    }
  }

  /**
   * Retrieves the general-purpose icon image for the specified icon name. The icon name should not
   * include the file extension. This method supports both PNG and JPEG image formats.
   *
   * @param icon the name of the icon to retrieve, without the file extension
   * @return the {@code Image} object representing the icon, or {@code null}
   * if the icon is not found
   * @throws IOException if an I/O error occurs
   */
  public static Image getIcon(final String icon) throws IOException {
    String iconNameLowerCase = icon.toLowerCase();
    Path pngPath = ICON_PATH.resolve(iconNameLowerCase + ".png");
    Path jpegPath = ICON_PATH.resolve(iconNameLowerCase + ".jpeg");

    if (Files.exists(pngPath)) {
      return new Image(Files.newInputStream(pngPath));
    } else if (Files.exists(jpegPath)) {
      return new Image(Files.newInputStream(jpegPath));
    } else {
      return null;
    }
  }
}

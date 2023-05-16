package edu.ntnu.idatt2001.paths.model.media;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

class IconHandlerTest {

  @Test
  void getInventoryIconReturnsNullForNonExistingFile() throws IOException {
    Image image = IconHandler.getInventoryIcon("non-existing-file");
    assertNull(image);
  }

  @Test
  void getIconReturnsNullForNonExistingFile() throws IOException {
    Image image = IconHandler.getIcon("non-existing-file");
    assertNull(image);
  }
}

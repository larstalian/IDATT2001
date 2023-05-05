package edu.ntnu.idatt2001.paths.model.filehandlers.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.serializers.GameDeserializer;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.serializers.LinkDeserializer;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.serializers.StoryDeserializer;
import edu.ntnu.idatt2001.paths.model.filehandlers.json.serializers.StorySerializer;
import edu.ntnu.idatt2001.paths.model.game.Game;
import edu.ntnu.idatt2001.paths.model.story.Link;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import edu.ntnu.idatt2001.paths.model.story.Story;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class for handling saving and reading of Game objects to and from files. The files are stored in
 * JSON format with a ".json" extension in the "src/main/resources/games" directory.
 *
 * <p>The game is also saved with the passage the player is currently at
 *
 * <p>Files are being saved and serialized using default Jackson serialization, and uses custom
 * deserializing for the {@link Game} class, {@link Story} class, and {@link Link} class because
 * Jackson's default deserialization cannot deserialize certain properties within these classes.
 *
 * <p>To use this class to write and read games to and from files, create a new instance of this
 * class and use the {@link #saveGameToFile(Game, Passage, List)} and {@link #loadGameFromFile
 * (String)} methods. For example:
 *
 * <pre>{@code
 * GameFileHandler gameFileHandler = new GameFileHandler();
 * Game myGame = new Game(player, story, goals);
 * gameFileHandler.saveGameToFile(myGame, currentPassage);
 * Game loadedGame = gameFileHandler.loadGameFromStyle("myGame").getGame();
 * }</pre>
 *
 * @see GameDeserializer
 * @see StorySerializer
 * @see StoryDeserializer
 * @see LinkDeserializer
 */
public class GameFileHandler {

  private static final Path filePath = Paths.get("src/main/resources/games");
  private final ObjectMapper objectMapper;

  /**
   * Class constructor that initializes the ObjectMapper for JSON serialization and deserialization.
   * It also registers custom serializers and deserializers for certain classes.
   */
  public GameFileHandler() {
    objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addSerializer(Story.class, new StorySerializer());
    module.addDeserializer(Story.class, new StoryDeserializer());
    module.addDeserializer(Link.class, new LinkDeserializer());
    module.addDeserializer(GameData.class, new GameDeserializer());
    objectMapper.registerModule(module);
  }

  /**
   * Retrieves a list of all game files saved in the "src/main/resources/games" directory.
   *
   * @return a list of all game file names, as strings
   * @throws IOException if there is an issue listing the files in the directory
   */
  public static List<String> getGameFiles() throws IOException {
    Files.createDirectories(filePath);
    try (Stream<Path> pathStream = Files.list(Paths.get("src/main/resources/games"))) {
      return pathStream
          .filter(Files::isRegularFile)
          .map(Path::getFileName)
          .map(Path::toString)
          .collect(Collectors.toList());
    }
  }

  /**
   * Saves the given game to a file with a filename based on the game's story title, and the
   * list of visited passages as well.
   *
   * @param game the game to be saved
   * @param passage the passage the player is currently at
   * @param visitedPassages the passages the player has visited
   * @throws IOException if there is an issue writing the game to the file
   * @throws NullPointerException if the game is null
   */
  public void saveGameToFile(Game game, Passage passage, List<Passage> visitedPassages)
      throws IOException {
    Objects.requireNonNull(game, "Game cannot be null");
    String fileName = game.getStory().getTitle();

    GameData gameData = new GameData(game, passage, visitedPassages);
    String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(gameData);

    Path gameFilePath = filePath.resolve(fileName + ".json");
    Files.write(gameFilePath, jsonString.getBytes());
  }

  /**
   * Saves the given game to a file with a filename based on the game's story title, without
   * specifying visited passages.
   *
   * @param game the game to be saved
   * @param passage the passage the player is currently at
   * @throws IOException if there is an issue writing the game to the file
   * @throws NullPointerException if the game is null
   */
  public void saveGameToFile(Game game, Passage passage) throws IOException {
    this.saveGameToFile(game, passage, new ArrayList<>());
  }

  /**
   * Loads a game from a file with the given filename.
   *
   * @param filename the name of the file to load the game from (without the ".json" extension)
   * @return the loaded game
   * @throws IOException if there is an issue reading the game from the file
   * @throws NullPointerException if the filename is null
   */
  public GameData loadGameFromFile(String filename) throws IOException {
    Objects.requireNonNull(filename, "Filename cannot be null");

    Path gameFilePath = filePath.resolve(filename + ".json");
    String jsonString = new String(Files.readAllBytes(gameFilePath));
    return objectMapper.readValue(jsonString, GameData.class);
  }

  /**
   * Returns the file path where games are stored.
   *
   * @return the file path
   */
  public Path getFilePath() {
    return filePath;
  }
}

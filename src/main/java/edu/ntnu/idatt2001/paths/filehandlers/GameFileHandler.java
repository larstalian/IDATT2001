package edu.ntnu.idatt2001.paths.filehandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.ntnu.idatt2001.paths.game.Game;
import edu.ntnu.idatt2001.paths.story.Link;
import edu.ntnu.idatt2001.paths.story.Story;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class GameFileHandler {

  private final ObjectMapper objectMapper;

  private final Path filePath;

  public GameFileHandler() {
    objectMapper = new ObjectMapper();
    filePath = Paths.get("src/main/resources/games");
    SimpleModule module = new SimpleModule();
    module.addSerializer(Story.class, new StorySerializer());
    module.addDeserializer(Story.class, new StoryDeserializer());
    module.addDeserializer(Link.class, new LinkDeserializer());
    module.addDeserializer(Game.class, new GameDeserializer());
    objectMapper.registerModule(module);
  }

  public void saveGameToFile(Game game) throws IOException {
    Objects.requireNonNull(game, "Game cannot be null");
    String fileName = game.getStory().getTitle();

    String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);
    Path gameFilePath = filePath.resolve(fileName + ".json");
    Files.write(gameFilePath, jsonString.getBytes());
  }

  public Game loadGameFromStyle(String filename) throws IOException {
    Objects.requireNonNull(filename, "Filename cannot be null");

    Path gameFilePath = filePath.resolve(filename + ".json");
    String jsonString = new String(Files.readAllBytes(gameFilePath));
    return objectMapper.readValue(jsonString, Game.class);
  }

  public Path getFilePath() {
    return filePath;
  }
}

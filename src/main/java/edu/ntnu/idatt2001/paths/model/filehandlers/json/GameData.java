package edu.ntnu.idatt2001.paths.model.filehandlers.json;

import edu.ntnu.idatt2001.paths.model.filehandlers.json.serializers.GameDeserializer;
import edu.ntnu.idatt2001.paths.model.game.Game;
import edu.ntnu.idatt2001.paths.model.story.Passage;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code GameData} class represents a container for a {@link Game}, {@link Passage} and a list
 * of visited passages objects. This class is used to store both the game state and the current
 * passage when saving and loading game data to and from files.
 *
 * @see GameFileHandler
 * @see GameDeserializer
 */
public class GameData {
  private Game game;
  private Passage passage;
  private List<Passage> visitedPassages;

  /**
   * Constructs a new {@code GameData} object with the specified game and passage.
   *
   * @param game the {@link Game} object to be stored in this {@code GameData}
   * @param passage the {@link Passage} object to be stored in this {@code GameData}
   */
  public GameData(Game game, Passage passage) {
    this.game = game;
    this.passage = passage;
    visitedPassages = new ArrayList<>();
  }

  /**
   * Constructs a new {@code GameData} object with the specified game, passage, and list of visited
   * passages.
   *
   * @param game the {@link Game} object to be stored in this {@code GameData}
   * @param passage the {@link Passage} object to be stored in this {@code GameData}
   * @param visitedPassages the list of visited passages to be stored in this {@code GameData}
   */
  public GameData(Game game, Passage passage, List<Passage> visitedPassages) {
    this(game, passage);
    this.visitedPassages = visitedPassages;
  }

  public List<Passage> getVisitedPassages() {
    return visitedPassages;
  }

  /**
   * Returns the {@link Game} object stored in this {@code GameData}.
   *
   * @return the {@link Game} object
   */
  public Game getGame() {
    return game;
  }

  /**
   * Sets the {@link Game} object stored in this {@code GameData}.
   *
   * @param game the {@link Game} object to be stored
   */
  public void setGame(Game game) {
    this.game = game;
  }

  /**
   * Returns the {@link Passage} object stored in this {@code GameData}.
   *
   * @return the {@link Passage} object
   */
  public Passage getPassage() {
    return passage;
  }

  /**
   * Sets the {@link Passage} object stored in this {@code GameData}.
   *
   * @param passage the {@link Passage} object to be stored
   */
  public void setPassage(Passage passage) {
    this.passage = passage;
  }
}

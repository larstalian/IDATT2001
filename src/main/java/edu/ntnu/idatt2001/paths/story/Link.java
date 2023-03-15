package edu.ntnu.idatt2001.paths.story;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ntnu.idatt2001.paths.actions.Action;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import static edu.ntnu.idatt2001.paths.story.Link.LinkConstants.*;

/**
 * The Link class represents a hyperlink or a doorway from one passage to another, with an
 * associated text label and optional actions.
 *
 * <p>Links are used to connect one passage to another in a story. Each Link has a text label that
 * describes the link, and a reference to the target passage. Links may also include a list of
 * optional Actions that are performed when the link is followed. Actions are represented as
 * instances of actions implementing the {@link Action} interface.
 *
 * <p>Links are immutable: their contents and actions cant be modified.
 *
 * <p>To create a new Link, provide a text label and reference as arguments. Actions can be added to
 * the Link using the {@link #addAction(Action)} method.
 *
 * <p>Links for a passage can also be created automatically when creating a new passage using the
 * {@link Story#addPassage(Passage)} method.
 *
 * @see Action
 * @see Passage
 */
@EqualsAndHashCode(of = "ref")
public class Link {

  private final String text;
  private final String ref;

  private final List<Action> actions;

  /**
   * Constructs a new Link object with the given text label and reference.
   *
   * @param text the text label for the link
   * @param ref the reference to the target passage
   * @throws IllegalArgumentException if the text label or reference is invalid
   */
  @JsonCreator
  public Link(@JsonProperty("text") String text, @JsonProperty("ref") String ref) {
    if (text.length() < 2 || text.length() > TEXT_MAX_LENGTH) {
      throw new IllegalArgumentException(
          "Text and must be between "
              + TEXT_MIN_LENGTH
              + " and "
              + TEXT_MAX_LENGTH
              + " characters.");
    }
    if (ref.length() < REF_MIN_LENGTH || ref.length() > REF_MAX_LENGTH) {
      throw new IllegalArgumentException(
          "Ref must be between " + REF_MIN_LENGTH + " and " + REF_MAX_LENGTH + " characters.");
    }
    this.text = text;
    this.ref = ref;
    this.actions = new ArrayList<>();
  }

  /**
   * Returns the text label for the link.
   *
   * @return the text label for the link
   */
  public String getText() {
    return text;
  }

  /**
   * Returns the reference to the target passage.
   *
   * @return the reference to the target passage
   */
  public String getRef() {
    return ref;
  }

  /**
   * Returns the list of Actions associated with the link.
   *
   * @return the list of Actions associated with the link
   */
  public List<Action> getActions() {
    return actions;
  }

  /**
   * Adds an Action to the list of Actions associated with the link.
   *
   * @param action the Action to be added
   * @return {@code true} if the Action was added to the list, {@code false} otherwise
   */
  public boolean addAction(final Action action) {
    return actions.add(action);
  }

  /**
   * Returns a string representation of the link, including its text label, reference, and actions.
   *
   * @return a string representation of the link, including its text label, reference, and actions
   */
  @Override
  public String toString() {
    return "Game.Link{"
        + "text='"
        + text
        + '\''
        + ", ref='"
        + ref
        + '\''
        + ", actions="
        + actions
        + '}';
  }

  /**
   * The `LinkConstants` class defines constants used in the `Link` class.
   *
   * <p>These constants include the maximum and minimum lengths for the `text` and `ref` fields in a
   * `Link` object. They are used to ensure that the `text` and `ref` fields are within the expected
   * range of lengths.
   *
   * <p>This class is not intended to be instantiated, but rather provides a namespace for constants
   * that are used throughout the `Link` class.
   *
   * @see Link
   */
  static class LinkConstants {

    static final int TEXT_MAX_LENGTH = 50;
    static final int REF_MIN_LENGTH = 2;
    static final int REF_MAX_LENGTH = 50;
    static final int TEXT_MIN_LENGTH = 2;
  }
}

package edu.ntnu.idatt2001.paths.model.story;

import static edu.ntnu.idatt2001.paths.model.story.Passage.PassageConstants.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.EqualsAndHashCode;

/**
 * The Passage class represents a section of a story or narrative, with a title, content, and
 * optional links to other Passages.
 *
 * <p>A Passage contains a title and content, and can contain links to other Passages. Links are
 * represented as instances of the {@link Link} class. A Passage can be thought of as a "scene" in a
 * collection of scenes in a story, where the links represent the door or a key into another
 * passage.
 *
 * <p>To create a new Passage, provide a title and content as arguments. Links can be added to the
 * Passage using the {@link #addLink(Link)} method.
 *
 * <p>Passages are immutable: once created, their contents and links cannot be modified. However,
 * new Passages can be added to the map in {@link Story} by creating new instances and adding links
 * to existing Passages.
 *
 * <p>The Passage class also provides several methods for working with the passage and its links,
 * including getters for the title, content, and links, a method for checking if the Passage has any
 * links, and a method for adding links to the Passage. The {@link #toString()} method returns a
 * string representation of the Passage, including its title, content, and links.
 *
 * @see Link
 * @see Story
 */
@EqualsAndHashCode(of = "title")
public class Passage {

  @JsonProperty private final String title;
  @JsonProperty private final List<Link> links;
  @JsonProperty private String content;
  @JsonProperty private Mood mood;
  @JsonProperty private boolean singleVisitOnly;

  /**
   * Constructs a new Passage object with the given title and content.
   *
   * @param title the title of the passage
   * @param content the content of the passage
   * @throws IllegalArgumentException if the title or content is null
   */
  @JsonCreator
  public Passage(
      @JsonProperty("title") String title,
      @JsonProperty("content") String content,
      @JsonProperty("mood") Mood mood,
      @JsonProperty("singleVisitOnly") boolean singleVisitOnly) {

    if (title.length() <= MIN_TITLE_LENGTH || title.length() > MAX_TITLE_LENGTH) {
      throw new IllegalArgumentException("Passage title must be between 1 and 20 characters long");
    }
    if (content.length() <= MIN_CONTENT_LENGTH || content.length() > MAX_CONTENT_LENGTH) {
      throw new IllegalArgumentException(
          "Passage content must be between 1 and 400 characters long");
    }

    this.mood = mood == null ? Mood.NONE : mood;
    this.title = title;
    this.content = content;
    this.links = new ArrayList<>();
    this.singleVisitOnly = singleVisitOnly;
  }

  /**
   * Constructs a new Passage object with the given title and content.
   *
   * @param title the title of the passage
   * @param content the content of the passage
   * @throws IllegalArgumentException if the title or content is null
   */
  public Passage(String title, String content) {
    this(title, content, Mood.NONE, false);
  }

  /**
   * Checks if the passage is meant for single visit only.
   *
   * @return {@code true} if the passage is meant for single visit only, {@code false} otherwise
   */
  public boolean isSingleVisitOnly() {
    return singleVisitOnly;
  }

  /**
   * Sets the single visit only status for the passage.
   *
   * @param singleVisitOnly a boolean value indicating if the passage is meant for single visit only
   */
  public void setSingleVisitOnly(boolean singleVisitOnly) {
    this.singleVisitOnly = singleVisitOnly;
  }

  public Mood getMood() {
    return mood;
  }

  public void setMood(Mood value) {
    this.mood = value;
  }

  /**
   * Returns the title of the passage.
   *
   * @return the title of the passage
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the content of the passage.
   *
   * @return the content of the passage
   */
  public String getContent() {
    return content;
  }

  /**
   * Sets the content of the passage.
   *
   * @param content the content of the passage
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * Returns the list of links in the passage.
   *
   * @return the list of links in the passage
   */
  public List<Link> getLinks() {
    return links;
  }

  /**
   * Adds a link to the list of links.
   *
   * @param link the link to be added
   * @return {@code true} if the link was added to the list, {@code false} otherwise
   * @throws IllegalArgumentException if the link already exists in the list
   */
  public boolean addLink(Link link) {
    Objects.requireNonNull(link, "Link cannot be null");
    if (links.contains(link)) {
      throw new IllegalArgumentException("Link already exists in the list");
    }
    return links.add(link);
  }

  /**
   * Checks if the passage has any links.
   *
   * @return {@code true} if the passage has any links, {@code false} otherwise
   */
  public boolean hasLinks() {
    return !links.isEmpty();
  }

  /**
   * Returns a string representation of the passage, including its title, content, and links.
   *
   * @return a string representation of the passage, including its title, content, and links.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Title: ").append(title).append("\n");
    sb.append("Content: ").append(content).append("\n");
    sb.append("Links:\n");
    for (Link link : links) {
      sb.append("- ").append(link.getText()).append(": ").append(link.getRef()).append("\n");
    }
    return sb.toString();
  }

  /**
   * Constants for the Passage class defining the minimum and maximum length of the title and
   * content.
   */
  static class PassageConstants {
    static final int MIN_TITLE_LENGTH = 1;
    static final int MAX_TITLE_LENGTH = 20;
    static final int MIN_CONTENT_LENGTH = 1;
    static final int MAX_CONTENT_LENGTH = 400;
  }
}

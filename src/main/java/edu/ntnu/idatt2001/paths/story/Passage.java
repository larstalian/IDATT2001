package edu.ntnu.idatt2001.paths.story;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * The Passage class represents a section of a story or narrative, with a title, content, and optional links to other Passages.
 *
 * <p>A Passage contains a title and content, and can contain links to other Passages. Links are represented
 * as instances of the {@link Link} class. A Passage can be thought of as a "scene" in a collection of scenes in a story, where the
 * links represent the door or a key into another passage.</p>
 *
 * <p>To create a new Passage, provide a title and content as arguments. Links can be added to the Passage
 * using the {@link #addLink(Link)} method.</p>
 *
 * <p>Passages are immutable: once created, their contents and links cannot be modified. However, new Passages can be added to
 * the map in {@link Story} by creating new instances and adding links to existing Passages.</p>
 *
 * <p>The Passage class also provides several methods for working with the passage and its links, including getters for the title,
 * content, and links, a method for checking if the Passage has any links, and a method for adding links to the Passage. The {@link
 * #toString()} method returns a string representation of the Passage, including its title, content, and links.</p>
 *
 * @see Link
 * @see Story
 */
@EqualsAndHashCode
public class Passage {
    private final String title;
    private final String content;
    private final List<Link> links;

    /**
     * Constructs a new Passage object with the given title and content.
     *
     * @param title   the title of the passage
     * @param content the content of the passage
     * @throws IllegalArgumentException if either the title or content parameter is null
     */
    public Passage(String title, String content) throws IllegalArgumentException {
        if (title == null || content == null) {
            throw new IllegalArgumentException("Title and content cannot be null.");
        }
        this.title = title;
        this.content = content;
        this.links = new ArrayList<>();
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
     * @throws IllegalArgumentException if the link is null or already exists in the list
     */
    public boolean addLink(Link link) {
        if (link == null) {
            throw new IllegalArgumentException("Link cannot be null");
        }
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
            sb.append("- ").append(link.getText()).append(": ")
                    .append(link.getRef()).append("\n");
        }
        return sb.toString();
    }
}

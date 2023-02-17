package edu.ntnu.idatt2001.paths.story;

import java.util.ArrayList;
import java.util.List;

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

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

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

    public boolean hasLinks() {
        return !links.isEmpty();
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passage)) return false;

        Passage passage = (Passage) o;

        if (!getTitle().equals(passage.getTitle())) return false;
        if (!getContent().equals(passage.getContent())) return false;
        return getLinks().equals(passage.getLinks());
    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + getContent().hashCode();
        result = 31 * result + getLinks().hashCode();
        return result;
    }
}

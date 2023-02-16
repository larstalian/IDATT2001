package edu.ntnu.idatt2001.paths.story;

import java.util.List;

public class Passage {
    String title;
    String content;
    List<Link> links;

    public Passage(String title, String content) {
        this.title = title;
        this.content = content;
        this.links = new java.util.ArrayList<>();
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

    public boolean addLink(Link link) {
        return links.add(link);
    }

    public boolean hasLinks(String ref) {
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

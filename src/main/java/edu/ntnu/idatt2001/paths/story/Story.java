package edu.ntnu.idatt2001.paths.story;

import java.util.Collection;
import java.util.Map;

public class Story {
    String title;
    Passage openingPassage;
    Map<Link, Passage> passages;

    public Story(String title, Passage openingPassage) {
        this.title = title;
        this.openingPassage = openingPassage;
        this.passages = new java.util.HashMap<>();
    }

    public String getTitle() {
        return title;
    }

    public Passage getOpeningPassage() {
        return openingPassage;
    }

    public boolean addPassage(Passage passage) {
        Link link = new Link(passage.getTitle(), passage.getTitle());
        return passages.put(link, passage) != null;
    }

    public Passage getPassage(Link link) {
        return passages.get(link);
    }

    public Collection<Passage> getPassages() {
        return passages.values();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(title).append("\n");
        sb.append("Opening Passage:\n");
        sb.append(openingPassage.toString()).append("\n");
        sb.append("Passages:\n");
        for (Passage passage : passages.values()) {
            sb.append("- ").append(passage.getTitle()).append(": ").append(passage.getContent()).append("\n");
        }
        return sb.toString();
    }

}


package edu.ntnu.idatt2001.paths;

import java.util.Collection;
import java.util.Map;

public class Story {
    String title;
    Passage openingPassage;
    Map<Link, Passage> passages;

    public Story(String title, Passage openingPassage) {
        this.title = title;
        this.openingPassage = openingPassage;
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
        final StringBuilder sb = new StringBuilder("Story{");
        sb.append("title='").append(title).append('\'');
        sb.append(", openingPassage=").append(openingPassage);
        sb.append('}');
        return sb.toString();
    }
}


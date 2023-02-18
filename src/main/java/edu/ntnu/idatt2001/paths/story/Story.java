package edu.ntnu.idatt2001.paths.story;

import java.util.*;

public class Story {
    private final String title;
    private final Passage openingPassage;
    private final Map<Link, Passage> passages;

    public Story(String title, Passage openingPassage) {
        this.title = title;
        this.openingPassage = openingPassage;
        this.passages = new HashMap<>();
    }

    public String getTitle() {
        return title;
    }

    public Passage getOpeningPassage() {
        return openingPassage;
    }

    public boolean addPassage(Passage passage) {
        Link link = new Link(passage.getTitle(), passage.getTitle());
        passages.put(link, passage);
        return true;
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
        sb.append(openingPassage.getContent()).append("\n");
        sb.append("Passages:\n");
        List<Passage> passageList = new ArrayList<>(passages.values());
        passageList.sort(Comparator.comparing(Passage::getTitle));
        for (Passage passage : passageList) {
            sb.append("- ").append(passage.getTitle()).append(": ").append(passage.getContent()).append("\n");
        }
        return sb.toString();
    }
}


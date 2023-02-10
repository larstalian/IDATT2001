package edu.ntnu.idatt2001.paths;

import edu.ntnu.idatt2001.paths.actions.Action;

import java.util.List;

public class Link {
    String text;
    String ref;
    List<Action> actions;

    public Link(String text, String ref) {
        this.text = text;
        this.ref = ref;
    }

    public String getText() {
        return text;
    }

    public String getRef() {
        return ref;
    }

    public List<Action> getActions() {
        return actions;
    }

    public boolean addAction(Action action) {
        return actions.add(action);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Game.Link{");
        sb.append("text='").append(text).append('\'');
        sb.append(", ref='").append(ref).append('\'');
        sb.append(", actions=").append(actions);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;

        Link link = (Link) o;

        if (!getText().equals(link.getText())) return false;
        return getRef().equals(link.getRef());
    }

    @Override
    public int hashCode() {
        int result = getText().hashCode();
        result = 31 * result + getRef().hashCode();
        return result;
    }
}

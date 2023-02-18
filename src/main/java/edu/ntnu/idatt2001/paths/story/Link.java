package edu.ntnu.idatt2001.paths.story;

import edu.ntnu.idatt2001.paths.actions.Action;

import java.util.List;

/**
 * The Link class represents a hyperlink or a doorway from one passage to another, with an associated text label and optional actions.
 *
 * <p>Links are used to connect one passage to another in a story. Each Link has a text label that describes
 * the link, and a reference to the target passage. Links may also include a list of optional Actions that are performed
 * when the link is followed. Actions are represented as instances of actions implementing the {@link Action} interface. </p>
 *
 * <p>Links are immutable: their contents and and actions cant be modified. </p>
 *
 * <p>To create a new Link, provide a text label and reference as arguments. Actions can be added to
 * the Link using the {@link #addAction(Action)} method.</p>
 *
 * <p>Links for a passage can also be created automatically
 * when creating a new passage using the {@link Story#addPassage(Passage)} method.</p>
 *
 * @see Action
 * @see Passage
 */
public class Link {
    private final String text;
    private final String ref;
    private final List<Action> actions;

    /**
     * Constructs a new Link object with the given text label and reference.
     *
     * @param text the text label for the link
     * @param ref  the reference to the target passage
     */
    public Link(String text, String ref) {
        this.text = text;
        this.ref = ref;
        this.actions = new java.util.ArrayList<>();
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
    public boolean addAction(Action action) {
        return actions.add(action);
    }

    /**
     * Returns a string representation of the link, including its text label, reference, and actions.
     *
     * @return a string representation of the link, including its text label, reference, and actions
     */
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

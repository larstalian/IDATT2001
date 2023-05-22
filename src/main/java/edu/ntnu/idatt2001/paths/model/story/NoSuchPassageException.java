package edu.ntnu.idatt2001.paths.model.story;

/**
 * This exception is thrown when a requested passage does not exist in a Story. This could happen,
 * for example, when trying to access a Passage by its title or ID, but no such Passage exists
 * within the Story.
 */
public class NoSuchPassageException extends RuntimeException {

  /**
   * Creates a new exception with a default message.
   */
  public NoSuchPassageException() {
    super("The requested passage does not exist.");
  }

  /**
   * Creates a new exception with a custom message.
   *
   * @param message A String containing a custom message for this exception.
   */
  public NoSuchPassageException(String message) {
    super(message);
  }
}

package edu.ntnu.idatt2001.paths.model.story;

/**
 * The Mood enum represents the mood of a passage. It is used to determine which media should be
 * added when the player enters a passage.
 *
 * <p>The enum is immutable and cannot be modified.
 *
 * @see Passage
 */
public enum Mood {
  HAPPY,
  SAD,
  BOSS_BATTLE,
  VICTORY,
  DEFEAT,
  SPOOKY,
  SNEAKY,
  NONE
}

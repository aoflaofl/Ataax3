package com.spamalot.boardgame;

/**
 * Hold the score of a Game.
 *
 * @author gej
 *
 */
public class Score {
  /** Black score. */
  private int blackScore;

  /** White score. */
  private int whiteScore;

  /**
   * Construct a score object.
   *
   * @param numBlack
   *          Count of black pieces
   * @param numWhite
   *          Count of white pieces
   *
   */
  public Score(final int numBlack, final int numWhite) {
    this.blackScore = numBlack;
    this.whiteScore = numWhite;
  }

  /**
   * Get the Black Score.
   * 
   * @return the black score.
   */
  public int getBlackScore() {
    return this.blackScore;
  }

  /**
   * Get the White Score.
   * 
   * @return the white score.
   */
  public int getWhiteScore() {
    return this.whiteScore;
  }

  /**
   * Set number of Black pieces.
   * 
   * @param numBlack
   *          the black score to set
   */
  public void setBlack(final int numBlack) {
    this.blackScore = numBlack;
  }

  /**
   * Set the White Score.
   * 
   * @param numWhite
   *          the white score to set
   */
  public void setWhite(final int numWhite) {
    this.whiteScore = numWhite;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Score [black=");
    builder.append(this.blackScore);
    builder.append(", white=");
    builder.append(this.whiteScore);
    builder.append("]");
    return builder.toString();
  }
}

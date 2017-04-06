package com.spamalot.boardgame;

/**
 * Hold the score of an AtaxxGame.
 *
 * @author gej
 *
 */
public class Score {
  /** Black score. */
  private int black;

  /** White score. */
  private int white;

  /**
   * Construct a score object.
   *
   * @param b
   *          Count of black pieces
   * @param w
   *          Count of white pieces
   *
   */
  public Score(final int b, final int w) {
    this.black = b;
    this.white = w;
  }

  /**
   * @return the black
   */
  public int getBlack() {
    return this.black;
  }

  /**
   * @return the white
   */
  public int getWhite() {
    return this.white;
  }

  /**
   * @param b
   *          the black score to set
   */
  public void setBlack(final int b) {
    this.black = b;
  }

  /**
   * @param w
   *          the white score to set
   */
  public void setWhite(final int w) {
    this.white = w;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AtaxxScore [black=");
    builder.append(this.black);
    builder.append(", white=");
    builder.append(this.white);
    builder.append("]");
    return builder.toString();
  }
}

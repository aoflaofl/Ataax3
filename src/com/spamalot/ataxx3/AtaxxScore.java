package com.spamalot.ataxx3;

/**
 * Hold the score of an AtaxxGame.
 * 
 * @author gej
 *
 */
class AtaxxScore {
  /** Black score. */
  private int black;

  /** White score. */
  private int white;

  /** Number of blocked squares. */
  private int blocked;

  /**
   * @return the black
   */
  public int getBlack() {
    return this.black;
  }

  /**
   * @param b
   *          the black score to set
   */
  public void setBlack(final int b) {
    this.black = b;
  }

  /**
   * Construct a score object.
   * 
   * @param b
   *          Count of black pieces
   * @param w
   *          Count of white pieces
   * @param blocked
   *          Number of blocked squares
   */
  AtaxxScore(final int b, final int w, final int bl) {
    this.black = b;
    this.white = w;
    this.blocked = bl;
  }

  /**
   * @return the white
   */
  public int getWhite() {
    return this.white;
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

  public int getBlocked() {
    return blocked;
  }

}

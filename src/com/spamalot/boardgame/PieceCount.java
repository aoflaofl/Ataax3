package com.spamalot.boardgame;

/**
 * Hold the piece counts of a Game.
 *
 * @author gej
 *
 */
class PieceCount {
  /** Black Piece count. */
  private int blackCount;

  /** White Piece count. */
  private int whiteCount;

  /**
   * Construct a PieceCount object.
   *
   * @param numBlack
   *          Count of black pieces
   * @param numWhite
   *          Count of white pieces
   *
   */
  PieceCount(final int numBlack, final int numWhite) {
    this.blackCount = numBlack;
    this.whiteCount = numWhite;
  }

  /**
   * Get the Black Piece count.
   * 
   * @return the black score.
   */
  public int getBlackCount() {
    return this.blackCount;
  }

  /**
   * Get the White Piece count.
   * 
   * @return the white score.
   */
  public int getWhiteCount() {
    return this.whiteCount;
  }

  /**
   * Set number of Black pieces.
   * 
   * @param numBlack
   *          the black count to set
   */
  public void setBlackCount(final int numBlack) {
    this.blackCount = numBlack;
  }

  /**
   * Set number of White pieces.
   * 
   * @param numWhite
   *          the white count to set
   */
  public void setWhiteCount(final int numWhite) {
    this.whiteCount = numWhite;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Piece Count [black=");
    builder.append(this.blackCount);
    builder.append(", white=");
    builder.append(this.whiteCount);
    builder.append("]");
    return builder.toString();
  }
}

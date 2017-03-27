package com.spamalot.ataxx3;

/**
 * A piece in a board game.
 *
 * @author gej
 *
 */
class Piece {

  /** Color of this piece. */
  private PieceColor color;

  /**
   * Construct a Piece.
   *
   * @param c
   *          the color.
   */
  Piece(final PieceColor c) {
    this.setColor(c);
  }

  /** Flip the color of this piece. */
  public final void flip() {
    setColor(this.color.getOpposite());
  }

  /**
   * Get the color.
   *
   * @return the color.
   */
  public final PieceColor getColor() {
    return this.color;
  }

  /**
   * Set the color.
   *
   * @param c
   *          the color.
   */
  private void setColor(final PieceColor c) {
    this.color = c;
  }

  @Override
  public final String toString() {
    return this.color.toString();
  }
}

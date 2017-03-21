package com.spamalot.ataxx3;

/**
 * A piece in an Ataxx game.
 *
 * @author gej
 *
 */
class AtaxxPiece {

  /** Color of this piece. */
  private AtaxxColor color;

  /**
   * Construct an Ataxx piece.
   *
   * @param c
   *          the color.
   */
  AtaxxPiece(final AtaxxColor c) {
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
  public final AtaxxColor getColor() {
    return this.color;
  }

  /**
   * Set the color.
   *
   * @param c
   *          the color.
   */
  private void setColor(final AtaxxColor c) {
    this.color = c;
  }

  @Override
  public final String toString() {
    return this.color.toString();
  }
}

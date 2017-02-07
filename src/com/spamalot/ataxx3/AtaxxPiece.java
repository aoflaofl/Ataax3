package com.spamalot.ataxx3;

/**
 * A piece in an Ataxx game.
 * 
 * @author gej
 *
 */
public class AtaxxPiece {

  /** Color of this piece. */
  private AtaxxColor color;

  /**
   * Construct an Ataxx piece.
   * 
   * @param c
   *          the color.
   */
  public AtaxxPiece(final AtaxxColor c) {
    this.setColor(c);
  }

  /**
   * Get the color.
   * 
   * @return the color.
   */
  public final AtaxxColor getColor() {
    return color;
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

  /** Flip the color of this piece. */
  public final void flip() {
    setColor(color.getOpposite());
  }

  @Override
  public final String toString() {
    return color.getRepresentation();
  }
}

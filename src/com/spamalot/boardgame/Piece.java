package com.spamalot.boardgame;

/**
 * A piece in a board game.
 *
 * @author gej
 *
 */
public class Piece {

  /** Color of this piece. */
  private PieceColor color;

  /**
   * Construct a Piece.
   *
   * @param clr
   *          the color.
   */
  public Piece(final PieceColor clr) {
    this.setColor(clr);
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
   * @param clr
   *          the color.
   */
  private void setColor(final PieceColor clr) {
    this.color = clr;
  }

  @Override
  public final String toString() {
    return this.color.toString();
  }
}

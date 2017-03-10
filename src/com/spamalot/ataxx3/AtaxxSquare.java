package com.spamalot.ataxx3;

/**
 * A square on an Ataxx Board.
 * 
 * @author gej
 *
 */
class AtaxxSquare {
  /**
   * Type of square.
   * 
   * @author gej
   *
   */
  public enum Type {
    /** Means the square can hold a piece. */
    PLAYABLE,
    /** Means the square is prevented from holding a piece. */
    BLOCKED
  }

  /** Piece in the square. */
  private AtaxxPiece piece;
  /** Type of Square. */
  private Type type;

  /**
   * Construct an AtaxxSqaure object.
   * 
   * @param t
   *          Type of Square.
   */
  AtaxxSquare(final Type t) {
    this.type = t;
  }

  /**
   * @return AtaxxPiece in this AtaxxSquare.
   */
  public AtaxxPiece getPiece() {
    return this.piece;
  }

  /**
   * Set the piece.
   * 
   * @param p
   *          Piece to set
   */
  public void setPiece(final AtaxxPiece p) {
    this.piece = p;
  }
}

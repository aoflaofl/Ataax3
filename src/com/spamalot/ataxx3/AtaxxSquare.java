package com.spamalot.ataxx3;

/**
 * A square on an Ataxx Board.
 * 
 * @author gej
 *
 */
/**
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

  /** Type of Square. */
  private Type type;

  /** Piece in the square. */
  private AtaxxPiece piece;

  /** Coordinate of this square. */
  private Coordinate coordinate;

  /**
   * Construct an AtaxxSquare object.
   * 
   * @param t
   *          Type of Square
   * @param rank
   *          rank the piece is on
   * @param file
   *          file the piece is on
   */
  AtaxxSquare(final Type t, final int file, final int rank) {
    this.type = t;
    this.coordinate = new Coordinate(file, rank);
  }

  /**
   * Get the AtaxxPiece in this AtaxxSquare.
   * 
   * @return the AtaxxPiece in this AtaxxSquare.
   */
  public AtaxxPiece getPiece() {
    return this.piece;
  }

  /**
   * Set the piece.
   * 
   * @param p
   *          AtaxxPiece to set in this AtaxxSquare
   */
  public void setPiece(final AtaxxPiece p) {
    this.piece = p;
  }

  /**
   * Get the Coordinate this Square is at in the board.
   * 
   * @return the coordinate
   */
  public Coordinate getCoordinate() {
    return this.coordinate;
  }

  /**
   * Get the numeric file this AtaxxSquare is at.
   * 
   * @return the numeric file this AtaxxSquare is at.
   */
  public int getFile() {
    return this.coordinate.getX();
  }

  /**
   * Get the numeric rank this AtaxxSquare is at.
   * 
   * @return the numeric rank this AtaxxSquare is at.
   */
  public int getRank() {
    return this.coordinate.getY();
  }
}

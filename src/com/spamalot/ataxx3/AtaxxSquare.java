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
   * Get file as a character for printing.
   * 
   * @return File as a character for printing.
   */
  private char getFileAsChar() {
    return (char) (this.coordinate.getX() + 'a');
  }

  /**
   * Get the numeric rank this AtaxxSquare is at.
   * 
   * @return the numeric rank this AtaxxSquare is at.
   */
  public int getRank() {
    return this.coordinate.getY();
  }

  /**
   * Remove and return a piece from the Square.
   * 
   * @return the AtaxxPiece in this square.
   */
  public AtaxxPiece pickupPiece() {
    AtaxxPiece p = this.piece;
    this.piece = null;
    return p;
  }

  /**
   * Check if square is empty.
   * 
   * @return true if square is empty.
   */
  public boolean isEmpty() {
    return this.type == Type.PLAYABLE && this.piece == null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.coordinate.hashCode();
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AtaxxSquare other = (AtaxxSquare) obj;
    if (this.coordinate == null) {
      if (other.coordinate != null) {
        return false;
      }
    } else if (!this.coordinate.equals(other.coordinate)) {
      return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getFileAsChar());
    builder.append(getRank() + 1);
    return builder.toString();
  }

  /**
   * Set this as a blocked square.
   */
  public void setBlocked() {
    this.type = Type.BLOCKED;
  }

  /**
   * @return true if this is a blocked square.
   */
  public boolean isBlocked() {
    return this.type == Type.BLOCKED;
  }
}

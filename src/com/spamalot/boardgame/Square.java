package com.spamalot.boardgame;

import java.util.EnumMap;
import java.util.List;

/**
 * Square in a Boardgame.
 * 
 * @author gej
 *
 */
public class Square {

  /**
   * Type of square.
   * 
   * @author gej
   *
   */
  public enum Type {
    /** Means the square can hold a piece. */
    OPEN,
    /** Means the square is prevented from holding a piece. */
    BLOCKED
  }

  /** Type of Square. */
  private Type type;

  /** Piece in the square. */
  private Piece piece;

  /** Coordinate of this square. */
  private Coordinate coordinate;

  /** The Squares that are in the first ring around this one. */
  private Square[] oneAwaySquares;

  /** The Squares that are in the second ring around this one. */
  private Square[] twoAwaySquares;

  /** The Squares in Directions. */
  private EnumMap<Direction, Square> directionMap = new EnumMap<>(Direction.class);

  /**
   * Construct an Square object.
   * 
   * @param typ
   *          Type of Square
   * @param rank
   *          rank the Square is on
   * @param file
   *          file the Square is on
   */
  Square(final Type typ, final int file, final int rank) {
    this.type = typ;
    this.coordinate = new Coordinate(file, rank);
  }

  /**
   * Construct an OPEN Square object.
   * 
   * @param file
   *          file the Square is on
   * @param rank
   *          rank the Square is on
   */
  Square(final int file, final int rank) {
    this(Square.Type.OPEN, file, rank);
  }

  /**
   * Get the Piece in this Square. Does not remove the Piece from the Square.
   * 
   * @return the Piece in this Square.
   */
  public Piece getPiece() {
    return this.piece;
  }

  /**
   * Set the Piece in this Square.
   * 
   * @param pc
   *          Piece to set in this Square
   */
  public void setPiece(final Piece pc) {
    this.piece = pc;
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
   * Get the numeric file this Square is at.
   * 
   * @return the numeric file this Square is at.
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
   * Get the numeric rank this Square is at.
   * 
   * @return the numeric board rank this Square is at.
   */
  public int getRank() {
    return this.coordinate.getY();
  }

  /**
   * Remove and return a piece from the Square.
   * 
   * @return the Piece in this Square.
   */
  public Piece pickupPiece() {
    Piece ret = this.piece;
    this.piece = null;
    return ret;
  }

  /**
   * Check if square is empty.
   * 
   * @return true if square is empty.
   */
  public boolean isEmpty() {
    return this.type == Type.OPEN && this.piece == null;
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
    Square other = (Square) obj;
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
  final void setBlocked() {
    this.type = Type.BLOCKED;
  }

  /**
   * @return true if this is a blocked square.
   */
  public boolean isBlocked() {
    return this.type == Type.BLOCKED;
  }

  /**
   * Set List of Squares that are neighbors (one square away) from this one.
   * 
   * @param oneAwaySqs
   *          List of Squares that are one away from this one
   */
  public void setOneAwaySquares(final List<Square> oneAwaySqs) {
    this.oneAwaySquares = new Square[oneAwaySqs.size()];
    oneAwaySqs.toArray(this.oneAwaySquares);
  }

  /**
   * Set List of Squares that are two squares away from this one.
   * 
   * @param twoAwaySqs
   *          List of Squares that are two away from this one
   */
  public void setTwoAwaySquares(final List<Square> twoAwaySqs) {
    this.twoAwaySquares = new Square[twoAwaySqs.size()];
    twoAwaySqs.toArray(this.twoAwaySquares);
  }

  /**
   * @return array of Squares that are in the first ring around this Square.
   */
  public Square[] getOneAwaySquares() {
    return this.oneAwaySquares;
  }

  /**
   * @return array of Squares that are in the second ring around this Square.
   */
  public Square[] getTwoAwaySquares() {
    return this.twoAwaySquares;
  }

  /**
   * Set the Square in the direction.
   * 
   * @param dir
   *          the Direction
   * @param sq
   *          the Square in the direction
   */
  final void setSquareInDirection(final Direction dir, final Square sq) {
    this.directionMap.put(dir, sq);
  }

  /**
   * Return the Square in the given direction.
   * 
   * @param dir
   *          Direction to get Square
   * @return the Square.
   */
  public final Square getSquareInDirection(final Direction dir) {
    return this.directionMap.get(dir);
  }
}

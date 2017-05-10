package com.spamalot.boardgame;

/**
 * The {@code PieceColor} enum defines the possible colors of a
 * {@link com.spamalot.boardgame.Piece Piece} in a board game. You can have any
 * color you want so long as it is Black or White.
 * 
 * @author gej
 *
 */
public enum PieceColor {

  /** White. */
  WHITE,
  /** Black. */
  BLACK;

  /** Opposite color. */
  private PieceColor opposite;

  /** What the color looks like when printed. */
  private String representation;

  static {
    WHITE.opposite = BLACK;
    WHITE.representation = "O";
    BLACK.opposite = WHITE;
    BLACK.representation = "#";
  }

  /**
   * Get the Opposite color.
   * 
   * @return the opposite color.
   */
  public PieceColor getOpposite() {
    return this.opposite;
  }

  /**
   * How the piece should be displayed in the toString.
   * 
   * @return the representation
   */
  @Override
  public String toString() {
    return this.representation;
  }

}

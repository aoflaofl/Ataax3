package com.spamalot.ataxx3;

/**
 * Colors of pieces in a board game.
 * 
 * @author gej
 *
 */
enum PieceColor {

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
   * @return the opposite color.
   */
  PieceColor getOpposite() {
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

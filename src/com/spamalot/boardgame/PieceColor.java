package com.spamalot.boardgame;

/**
 * Colors of pieces in a board game.
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

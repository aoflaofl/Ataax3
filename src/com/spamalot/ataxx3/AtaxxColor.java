package com.spamalot.ataxx3;

/**
 * Colors in a game of Ataxx.
 * 
 * @author gej
 *
 */
public enum AtaxxColor {

  /** White. */
  WHITE,
  /** Black. */
  BLACK;

  /** Opposite color. */
  private AtaxxColor opposite;

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
  AtaxxColor getOpposite() {
    return this.opposite;
  }

  /**
   * @return the representation
   */
  String getRepresentation() {
    return this.representation;
  }

}

package com.spamalot.ataxx3;

/**
 * Colors in a game of Ataxx.
 * 
 * @author gej
 *
 */
enum AtaxxColor {

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
   * How the piece should be displayed in the toString.
   * 
   * @return the representation
   */
  @Override
  public String toString() {
    return this.representation;
  }

}

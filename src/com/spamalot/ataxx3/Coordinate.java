package com.spamalot.ataxx3;

/**
 * Hold two ordinates. Should be immutable.
 * 
 * @author gej
 *
 */
final class Coordinate {
  /** The X ordinate. */
  private final int x;
  /** The Y ordinate. */
  private final int y;

  /**
   * Construct a Coordinate.
   * 
   * @param xOrd
   *          the X ordinate
   * @param yOrd
   *          the Y ordinate
   */
  Coordinate(final int xOrd, final int yOrd) {
    this.x = xOrd;
    this.y = yOrd;
  }

  /**
   * @return the x ordinate.
   */
  public int getX() {
    return this.x;
  }

  /**
   * @return the y ordinate.
   */
  public int getY() {
    return this.y;
  }
}

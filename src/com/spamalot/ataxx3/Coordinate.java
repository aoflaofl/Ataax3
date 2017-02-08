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
   * @param x
   *          the X ordinate
   * @param y
   *          the Y ordinate
   */
  Coordinate(final int x, final int y) {
    this.x = x;
    this.y = y;
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

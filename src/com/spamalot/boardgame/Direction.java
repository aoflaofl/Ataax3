package com.spamalot.boardgame;

/**
 * Directions to use while navigating a board.
 * 
 * @author gej
 *
 */
enum Direction {
  /** The eight Directions. */
  N(-1, 0), E(0, 1), W(0, -1), S(1, 0), NE(-1, 1), NW(-1, -1), SE(1, 1), SW(1, -1);

  /** The rise. */
  private int rise;
  /** The run. */
  private int run;

  /**
   * Construct a Direction.
   * 
   * @param ri
   *          the Rise
   * @param ru
   *          the Run
   */
  Direction(final int ri, final int ru) {
    setRise(ri);
    setRun(ru);
  }

  /**
   * Get the geometric rise of this Direction.
   * 
   * @return the rise
   */
  public int getRise() {
    return this.rise;
  }

  /**
   * Set the geometric rise of this Direction.
   * 
   * @param rs
   *          the rise to set
   */
  private void setRise(final int rs) {
    this.rise = rs;
  }

  /**
   * Get the geometric run of this Direction.
   * 
   * @return the run
   */
  public int getRun() {
    return this.run;
  }

  /**
   * Set the geometric run of this Direction.
   * 
   * @param rn
   *          the run to set
   */
  private void setRun(final int rn) {
    this.run = rn;
  }

}

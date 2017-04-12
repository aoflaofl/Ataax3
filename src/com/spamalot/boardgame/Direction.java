package com.spamalot.boardgame;

/**
 * @author gej
 *
 */
public enum Direction {
  N(-1, 0), E(0, 1), W(0, -1), S(-1, 0);

  private int rise;
  private int run;

  Direction(final int rs, final int rn) {
    setRise(rs);
    setRun(rn);
  }

  /**
   * @return the rise
   */
  public int getRise() {
    return this.rise;
  }

  /**
   * @param rs
   *          the rise to set
   */
  private void setRise(final int rs) {
    this.rise = rs;
  }

  /**
   * @return the run
   */
  public int getRun() {
    return this.run;
  }

  /**
   * @param rn
   *          the run to set
   */
  private void setRun(final int rn) {
    this.run = rn;
  }

}

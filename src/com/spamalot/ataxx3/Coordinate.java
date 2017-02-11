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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("[x=");
    builder.append(this.x);
    builder.append(", y=");
    builder.append(this.y);
    builder.append("]");
    return builder.toString();
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
    result = prime * result + this.x;
    result = prime * result + this.y;
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
    Coordinate other = (Coordinate) obj;
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    return true;
  }
}

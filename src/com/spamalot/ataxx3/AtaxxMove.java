package com.spamalot.ataxx3;

/**
 * Hold details of a move in a game of Ataxx.
 * 
 * @author gej
 *
 */
class AtaxxMove {
  /**
   * The two types of an Ataxx move.
   * 
   * @author gej
   *
   */
  public enum Type {
    /** Grow to an adjacent (orthogonal and diagonal). */
    EXPAND,
    /** Jump to a square two squares away (Knight moves count). */
    JUMP
  }

  /** The color making this move. */
  private AtaxxColor color;

  /** The from square. */
  private Coordinate from;
  /** The to square. */
  private Coordinate to;

  /**
   * The From Coordinate.
   * 
   * @return the from
   */
  public Coordinate getFrom() {
    return this.from;
  }

  /**
   * The To Coordinate.
   * 
   * @return the to
   */
  public Coordinate getTo() {
    return this.to;
  }

  /** What Type of Ataxx move this is. */
  private Type type;

  /**
   * Construct an Ataxx Move.
   * 
   * @param moveType
   *          the type of move
   * @param pieceColor
   *          the color of the piece
   * @param fromCoord
   *          the from square
   * @param toCoord
   *          the to square
   */
  AtaxxMove(final Type moveType, final AtaxxColor pieceColor, final Coordinate fromCoord, final Coordinate toCoord) {
    this.type = moveType;
    this.color = pieceColor;
    this.from = fromCoord;
    this.to = toCoord;

  }

  /**
   * The Color.
   * 
   * @return the color
   */
  public final AtaxxColor getColor() {
    return this.color;
  }

  /**
   * Get the type of this move.
   * 
   * @return the Type.
   */
  public final Type getType() {
    return this.type;
  }

  /**
   * Set the color.
   * 
   * @param c
   *          the color to set
   */
  public final void setColor(final AtaxxColor c) {
    this.color = c;
  }

  /**
   * Set the type of this move.
   * 
   * @param t
   *          the Type.
   */
  public final void setType(final Type t) {
    this.type = t;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("[type=");
    builder.append(this.type);
    builder.append(", color=");
    builder.append(this.color);
    builder.append(", from=");
    builder.append(this.from);
    builder.append(", to=");
    builder.append(this.to);
    builder.append("]");
    return builder.toString();
  }
}

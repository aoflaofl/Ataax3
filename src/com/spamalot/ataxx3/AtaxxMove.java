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
  enum Type {
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
   * Construct an Ataax Move.
   * 
   * @param type
   *          the type of move
   * @param color
   *          the color of the piece
   * @param from
   *          the from square
   * @param to
   *          the to square
   */
  AtaxxMove(final Type type, final AtaxxColor color, final Coordinate from, final Coordinate to) {
    this.type = type;
    this.color = color;
    this.from = from;
    this.to = to;

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
}

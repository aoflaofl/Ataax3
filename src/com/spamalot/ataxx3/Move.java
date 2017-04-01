package com.spamalot.ataxx3;

/**
 * A Move in a board game.
 * 
 * @author gej
 *
 */
class Move implements Comparable<Move> {

  /** The color making this move. */
  private PieceColor color;

  /** The from square. */
  private Square from;

  /** The to square. */
  private Square to;
  /** AI evaluation of move. Set to min value until evaluated. */
  private int evaluation = Integer.MIN_VALUE;

  /**
   * Set the to Square of the Move.
   * 
   * @param toCoord
   *          the Square being moved to
   */
  protected void setToSquare(final Square toCoord) {
    this.to = toCoord;
  }

  /**
   * Set the from Square of the Move.
   * 
   * @param fromCoord
   *          the Square being moved from
   */
  protected void setFromSquare(final Square fromCoord) {
    this.from = fromCoord;
  }

  /**
   * The From Coordinate.
   * 
   * @return the from
   */
  public Square getFromSquare() {
    return this.from;
  }

  /**
   * The To Coordinate.
   * 
   * @return the to
   */
  public Square getToSquare() {
    return this.to;
  }

  /**
   * The Color.
   * 
   * @return the color
   */
  public final PieceColor getColor() {
    return this.color;
  }

  /**
   * Set the color.
   * 
   * @param c
   *          the color to set
   */
  public final void setColor(final PieceColor c) {
    this.color = c;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("[");
    builder.append("color=");
    builder.append(this.color);
    builder.append(", from=");
    builder.append(this.from);
    builder.append(", to=");
    builder.append(this.to);
    builder.append(", evaluation=");
    builder.append(this.evaluation);
    builder.append("]");
    return builder.toString();
  }

  /**
   * Set the eveluation.
   * 
   * @param v
   *          the evaluation of this move
   */
  public void setEvaluation(final int v) {
    this.evaluation = v;
  }

  /**
   * @return the evaluation of this move.
   */
  public int getEvaluation() {
    return this.evaluation;
  }

  @Override
  public int compareTo(final Move o) {
    return o.evaluation - this.evaluation;
  }

}
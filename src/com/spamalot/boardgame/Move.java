package com.spamalot.boardgame;

/**
 * A Move in a board game.
 * 
 * @author gej
 *
 */
public class Move implements Comparable<Move> {

  /** The color making this move. */
  private PieceColor color;

  /** The from square. */
  private Square from;

  /** The to square. */
  private Square to;

  /** AI evaluation of move. Initialized to min value until evaluated. */
  private int evaluation = Integer.MIN_VALUE;

  /**
   * Set the to Square of the Move.
   * 
   * @param toSquare
   *          the Square being moved to
   */
  protected void setToSquare(final Square toSquare) {
    this.to = toSquare;
  }

  /**
   * Set the from Square of the Move.
   * 
   * @param fromSquare
   *          the Square being moved from
   */
  protected void setFromSquare(final Square fromSquare) {
    this.from = fromSquare;
  }

  /**
   * Get the From Square.
   * 
   * @return the from Square.
   */
  public Square getFromSquare() {
    return this.from;
  }

  /**
   * Get the To Square.
   * 
   * @return the to Square.
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

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
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
   * Set the evaluation.
   * 
   * @param v
   *          the evaluation of this move
   */
  public void setEvaluation(final int v) {
    this.evaluation = v;
  }

  /**
   * Get the Evaluation.
   * 
   * @return the evaluation of this move.
   */
  public int getEvaluation() {
    return this.evaluation;
  }

  /**
   * Note: this class has a natural ordering that is inconsistent with equals.
   */
  @Override
  public int compareTo(final Move o) {
    return o.evaluation - this.evaluation;
  }

}
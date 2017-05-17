package com.spamalot.boardgame;

/**
 * A Move in a board game.
 * 
 * @author gej
 *
 */
public class Move implements Comparable<Move> {

  /**
   * The two types of an Ataxx move.
   * 
   * @author gej
   *
   */
  public enum Type {
    /** Drop a new piece on the board. */
    DROP,
    /** Jump a piece from one Square to another. */
    JUMP,
    /** Don't make a move at all. */
    PASS
  }

  /** The color making this move. */
  private PieceColor color;

  /** The from square. */
  private Coordinate from;

  /** The to square. */
  private Coordinate to;

  /** AI evaluation of move. Initialized to min value until evaluated. */
  private int evaluation = Integer.MIN_VALUE;

  /** What Type of Move this is. */
  private Type moveType;

  /**
   * Construct a PASS Move.
   */
  public Move() {
    setType(Type.PASS);
  }

  /**
   * Create a Move with a certain type.
   * 
   * @param type
   *          Type of Move to create
   */
  public Move(final Type type) {
    setType(type);
  }

  /**
   * Set the Move's Type.
   * 
   * @param t
   *          the Move's Type
   */
  public void setType(final Type t) {
    this.moveType = t;
  }

  /**
   * Get the Move's Type.
   * 
   * @return the Move's Type.
   */
  public Type getType() {
    return this.moveType;
  }

  /**
   * Set the to Square of the Move.
   * 
   * @param toSquare
   *          the Square being moved to
   */
  protected void setToSquare(final Coordinate toSquare) {
    this.to = toSquare;
  }

  /**
   * Set the from Square of the Move.
   * 
   * @param fromSquare
   *          the Square being moved from
   */
  protected void setFromSquare(final Coordinate fromSquare) {
    this.from = fromSquare;
  }

  /**
   * Get the From Square.
   * 
   * @return the from Square.
   */
  public Coordinate getFromSquare() {
    return this.from;
  }

  /**
   * Get the To Square.
   * 
   * @return the to Square.
   */
  public Coordinate getToSquare() {
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
   * @param clr
   *          the color to set
   */
  public final void setColor(final PieceColor clr) {
    this.color = clr;
  }

  /*
   * (non-Javadoc)
   * 
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
   * @param eval
   *          the evaluation of this move
   */
  public void setEvaluation(final int eval) {
    this.evaluation = eval;
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
    result = prime * result + ((this.color == null) ? 0 : this.color.hashCode());
    result = prime * result + this.evaluation;
    result = prime * result + ((this.from == null) ? 0 : this.from.hashCode());
    result = prime * result + ((this.to == null) ? 0 : this.to.hashCode());
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
    if (!(obj instanceof Move)) {
      return false;
    }
    Move other = (Move) obj;
    if (this.color != other.color) {
      return false;
    }
    if (this.evaluation != other.evaluation) {
      return false;
    }
    if (this.from == null) {
      if (other.from != null) {
        return false;
      }
    } else if (!this.from.equals(other.from)) {
      return false;
    }
    if (this.to == null) {
      if (other.to != null) {
        return false;
      }
    } else if (!this.to.equals(other.to)) {
      return false;
    }
    return true;
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
  public int compareTo(final Move move) {
    return this.evaluation - move.evaluation;
  }

}

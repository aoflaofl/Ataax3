package com.spamalot.reversi;

import com.spamalot.boardgame.Move;
import com.spamalot.boardgame.PieceColor;
import com.spamalot.boardgame.Square;

/**
 * A Move in a Reversi game.
 * 
 * @author gej
 *
 */
class ReversiMove extends Move {
  /**
   * The two types of an Ataxx move.
   * 
   * @author gej
   *
   */
  public enum Type {
    /** Drop a piece on the board. */
    DROP,
    /** If there are no other moves, then this is the move type. */
    PASS
  }

  /** What Type of Reversi move this is. */
  private Type type;

  /**
   * Construct a Reversi Move.
   * 
   * @param pieceColor
   *          the color of the piece
   * @param toCoord
   *          the to square
   */
  ReversiMove(final PieceColor pieceColor, final Square toCoord) {
    this.type = Type.DROP;
    setColor(pieceColor);
    setToSquare(toCoord);
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
   * Set the type of this move.
   * 
   * @param t
   *          the Type.
   */
  public final void setType(final Type t) {
    this.type = t;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (!(obj instanceof ReversiMove)) {
      return false;
    }
    ReversiMove other = (ReversiMove) obj;
    if (this.type != other.type) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ReversiMove [getType()=");
    builder.append(getType());
    builder.append(", getToSquare()=");
    builder.append(getToSquare());
    builder.append(", getColor()=");
    builder.append(getColor());
    builder.append(", getEvaluation()=");
    builder.append(getEvaluation());
    builder.append(", hashCode()=");
    builder.append(hashCode());
    builder.append("]");
    return builder.toString();
  }
}

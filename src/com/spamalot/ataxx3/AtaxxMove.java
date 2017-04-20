package com.spamalot.ataxx3;

import com.spamalot.boardgame.Move;
import com.spamalot.boardgame.PieceColor;
import com.spamalot.boardgame.Square;

/**
 * Hold details of a move in a game of Ataxx.
 * 
 * @author gej
 *
 */
class AtaxxMove extends Move {
  /**
   * The two types of an Ataxx move.
   * 
   * @author gej
   *
   */
  public enum Type {
    /** Grow to an adjacent (orthogonal or diagonal). */
    EXPAND,
    /** Jump to a square two squares away (Knight moves count). */
    JUMP
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
  AtaxxMove(final Type moveType, final PieceColor pieceColor, final Square fromCoord, final Square toCoord) {
    this.type = moveType;
    setColor(pieceColor);
    setFromSquare(fromCoord);
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
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("[type=");
    builder.append(this.type);
    builder.append(", color=");
    builder.append(getColor());
    builder.append(", from=");
    builder.append(getFromSquare());
    builder.append(", to=");
    builder.append(getToSquare());
    builder.append(", evaluation=");
    builder.append(getEvaluation());
    builder.append("]");
    return builder.toString();
  }

  /**
   * Do a compare.
   * 
   * @param o
   *          the other
   * @return the compare
   */
  @Override
  public int compareTo(final Move o) {
    AtaxxMove mov = (AtaxxMove) o;

    int ret = super.compareTo(mov);
    if (ret == 0) {
      ret = this.getType().compareTo(mov.getType());
    }

    return ret;
  }
}

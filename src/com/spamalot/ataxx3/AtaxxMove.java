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
    super(moveType);
    setColor(pieceColor);
    setFromSquare(fromCoord);
    setToSquare(toCoord);
  }

  /** Make a move only with a type. */
  AtaxxMove() {
    super();
  }

  /* (non-Javadoc)
   * @see com.spamalot.boardgame.Move#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("[type=");
    builder.append(getType());
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

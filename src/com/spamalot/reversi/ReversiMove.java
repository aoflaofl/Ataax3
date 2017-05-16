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
   * Construct a Reversi Move.
   * 
   * @param pieceColor
   *          the color of the piece
   * @param toCoord
   *          the to square
   */
  ReversiMove(final PieceColor pieceColor, final Square toCoord) {
    super(Type.DROP);
    setColor(pieceColor);
    setToSquare(toCoord.getCoordinate());
  }

  /**
   * Create default Move.
   */
  ReversiMove() {
    super();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
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
    if (getType() != other.getType()) {
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

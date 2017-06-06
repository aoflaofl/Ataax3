package com.spamalot.ataxx3;

import com.spamalot.boardgame.Piece;
import com.spamalot.boardgame.UndoMove;

import java.util.List;

/**
 * Hold information to undo a move in Ataxx.
 * 
 * @author gej
 *
 */
class AtaxxUndoMove extends UndoMove<AtaxxMove> {

  /**
   * Construct an UndoMove object.
   * 
   * @param move
   *          Move that might be undone
   * @param flipped2
   *          List of coordinates of pieces to flip
   */
  AtaxxUndoMove(final AtaxxMove move, final List<Piece> flipped2) {
    setMove(move);
    this.flipped = flipped2;
  }

  /** List of coordinates of pieces that have been flipped. */
  private List<Piece> flipped;

  /**
   * Get the flipped piece list.
   * 
   * @return the flipped
   */
  public List<Piece> getFlipped() {
    return this.flipped;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("UndoMove [move=");
    builder.append(getMove());
    builder.append(", flipped=");
    builder.append(getFlipped());
    builder.append("\n]");
    return builder.toString();
  }

}

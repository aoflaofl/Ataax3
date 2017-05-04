package com.spamalot.ataxx3;

import com.spamalot.boardgame.Square;

import java.util.List;

/**
 * Hold information to undo a move in Ataxx.
 * 
 * @author gej
 *
 */
class AtaxxUndoMove {

  /**
   * Construct an UndoMove object.
   * 
   * @param move2
   *          Move that might be undone
   * @param flipped2
   *          List of coordinates of pieces to flip
   */
  AtaxxUndoMove(final AtaxxMove move2, final List<Square> flipped2) {
    this.move = move2;
    this.flipped = flipped2;
  }

  /** Ataxx move that has been made. */
  private AtaxxMove move;

  /** List of coordinates of pieces that have been flipped. */
  private List<Square> flipped;

  /**
   * Get the Move.
   * 
   * @return the move
   */
  public AtaxxMove getMove() {
    return this.move;
  }

  /**
   * Get the flipped piece list.
   * 
   * @return the flipped
   */
  public List<Square> getFlipped() {
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

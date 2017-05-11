package com.spamalot.boardgame;

/**
 * Base class for an undo move.
 * 
 * @author gej
 *
 * @param <T>
 *          A Move Type
 */
public class UndoMove<T extends Move> {

  /** Move that has been made. */
  private T move;

  /**
   * Get the Move.
   * 
   * @return the move
   */
  public T getMove() {
    return this.move;
  }

  /**
   * Set the Move.
   * 
   * @param m
   *          the Move
   */
  public void setMove(final T m) {
    this.move = m;
  }
}

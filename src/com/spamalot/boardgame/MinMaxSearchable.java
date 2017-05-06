package com.spamalot.boardgame;

import java.util.List;

/**
 * Objects that implement the MinMaxSearchable interface are able to be searched
 * using a MinMax type (like NegaMax) algorithm.
 * 
 * @author gej
 *
 * @param <T>
 *          Type that MinMax tree will be built of
 */
public interface MinMaxSearchable<T extends Move> {

  /**
   * Check if the game is over.
   * 
   * @return true if the game is over, false otherwise.
   */
  boolean isOver();

  /**
   * An evaluation of the position from white's perspective. Positive numbers
   * mean better for White, negative numbers mean better for Black.
   * 
   * @param gameOver
   *          True if game is over and is being evaluated for that
   * @return evaluation value.
   */
  int evaluate(boolean gameOver);

  /**
   * Take back the last move made.
   */
  void undoLastMove();

  /**
   * Make a move on the board.
   * 
   * @param move
   *          Move to make
   */
  void makeMove(T move);

  /**
   * Get the color to move.
   * 
   * @return the Color that is to move.
   */
  PieceColor getColorToMove();

  /**
   * Get list of moves.
   * 
   * @return a list of moves.
   */
  List<T> getAvailableMoves();

}

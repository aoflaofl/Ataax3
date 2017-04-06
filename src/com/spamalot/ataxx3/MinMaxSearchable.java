package com.spamalot.ataxx3;

import java.util.List;

interface MinMaxSearchable {

  /**
   * Get a list of moves.
   * 
   * @return a list of available moves.
   */
  List<Moveable> getAvailableMoves();

  /**
   * Check if the game is over.
   * 
   * @return true if the game is over.
   */
  boolean isOver();

  /**
   * An evaluation of the position from white's perspective.
   * 
   * @param gameOver
   *          True if game is over and is being evaluated for that
   * @return evaluation value.
   */
  int evaluate(boolean gameOver);

  void undoLastMove();

  void makeMove(final Moveable move);

  PieceColor getToMove();

}
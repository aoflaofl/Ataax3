package com.spamalot.ataxx3;

import com.spamalot.boardgame.PieceColor;

interface MinMaxSearchable {

  /**
   * Check if the game is over.
   * 
   * @return true if the game is over, false otherwise.
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

  void makeMove(Evaluatable move);

  PieceColor getToMove();
}
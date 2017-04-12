package com.spamalot.reversi;

import com.spamalot.boardgame.Board;
import com.spamalot.boardgame.Direction;
import com.spamalot.boardgame.Square;

/**
 * The Reversi Board.
 * 
 * @author gej
 *
 */
class ReversiBoard extends Board {

  /**
   * Construct a Reversi board object.
   * 
   * @param size
   *          Length of each side of the board
   */
  ReversiBoard(final int size) {
    super(size);
    initSquares(this.getSquares());

  }

  /**
   * Initialize the Squares to have information about their neighbors.
   * 
   * @param s
   *          Array of squares that make up the board
   */
  private void initSquares(final Square[][] s) {
    for (int rank = 0; rank < getNumRanks(); rank++) {
      for (int file = 0; file < getNumFiles(); file++) {
        Square sq = s[file][rank];
        for (Direction d : Direction.values()) {
          System.out.println(d);
        }
      }
    }
  }

}

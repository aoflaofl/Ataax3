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
public class ReversiBoard extends Board {

  /**
   * Construct a Reversi board object.
   * 
   * @param size
   *          Length of each side of the board
   */
  ReversiBoard(final int size) {
    super(size);
    initSquares(getSquares());
  }

  /**
   * Initialize the Squares to have information about their neighbors.
   * 
   * @param s
   *          Array of squares that make up the board
   */
  @Override
  protected void initSquares(final Square[][] s) {
    for (int rank = 0; rank < getNumRanks(); rank++) {
      for (int file = 0; file < getNumFiles(); file++) {
        Square sq = s[file][rank];
        for (Direction direction : Direction.values()) {
          System.out.println(direction);
          if (isPlayableSquare(file + direction.getRun(), rank + direction.getRise())) {
            sq.setSquareInDirection(direction, s[file + direction.getRun()][rank + direction.getRise()]);
          }
        }
      }
    }
  }
}

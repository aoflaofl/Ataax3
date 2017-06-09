package com.spamalot.ataxx3;

import com.spamalot.boardgame.Board;
import com.spamalot.boardgame.GameException;
import com.spamalot.boardgame.Square;

/**
 * Represent a board for an Ataxx game.
 * 
 * @author gej
 *
 */
class AtaxxBoard extends Board {

  /**
   * Don't go smaller than this.
   */
  private static final int MINIMUM_BOARD_SIZE = 4;

  /**
   * Construct a square Ataxx Board with the given size.
   * 
   * @param size
   *          Size of each side of the Ataxx Board
   * @throws GameException
   *           if board is too small.
   */
  AtaxxBoard(final int size) throws GameException {
    super(size);

    if (size < MINIMUM_BOARD_SIZE) {
      throw new GameException("Board must have minimum size of " + MINIMUM_BOARD_SIZE + ".");
    }

    setBlocked(1, 1);
    setBlocked(getNumFiles() - 2, getNumRanks() - 2);
    setBlocked(1, getNumRanks() - 2);
    setBlocked(getNumFiles() - 2, 1);

    initSquares();
  }

  /**
   * Initialize the Squares to have information about their neighbors.
   */
  @Override
  protected void initSquares() {
    for (int rank = 0; rank < getNumRanks(); rank++) {
      for (int file = 0; file < getNumFiles(); file++) {
        Square sq = this.getSquares()[file][rank];

        sq.setOneAwaySquares(getOneAwaySquares(sq));
        sq.setTwoAwaySquares(getTwoAwaySquares(sq));
      }
    }
  }
}

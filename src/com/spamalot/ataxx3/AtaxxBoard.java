package com.spamalot.ataxx3;

import com.spamalot.boardgame.Board;
import com.spamalot.boardgame.GameException;
import com.spamalot.boardgame.Piece;
import com.spamalot.boardgame.PieceColor;
import com.spamalot.boardgame.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a board for an Ataxx game.
 * 
 * @author gej
 *
 */
class AtaxxBoard extends Board {

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

  /**
   * Flip the pieces around the square that don't match the passed in color and
   * make them match passed in color.
   * 
   * @param ataxxSquare
   *          AtaxxSquare around which to flip
   * @param color
   *          Color to flip to
   * @return a List of AtaxxSquares that had flipped pieces.
   */
  static final List<Square> flipPiecesAroundSquare(final Square ataxxSquare, final PieceColor color) {
    PieceColor oppositeColor = color.getOpposite();

    List<Square> ret = new ArrayList<>();

    Square[] squares = ataxxSquare.getOneAwaySquares();
    for (Square sq : squares) {
      Piece piece = sq.getPiece();
      if (piece != null && piece.getColor() == oppositeColor) {
        piece.flip();
        ret.add(sq);
      }
    }

    return ret;
  }
}
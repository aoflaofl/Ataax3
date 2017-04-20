package com.spamalot.ataxx3;

import com.spamalot.boardgame.Board;
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
public class AtaxxBoard extends Board {

  /**
   * Construct a square Ataxx Board with the given size.
   * 
   * @param size
   *          Size of each side of the Ataxx Board
   */
  AtaxxBoard(final int size) {
    super(size);
    setBlocked(1, 1);
    setBlocked(5, 5);
    setBlocked(1, 5);
    setBlocked(5, 1);
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
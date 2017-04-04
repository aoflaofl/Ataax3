package com.spamalot.ataxx3;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a board for an Ataxx game.
 * 
 * @author gej
 *
 */
class AtaxxBoard extends Board {

  /** Number of Blocked Squares. */
  private int numBlockedSquares;

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
    initSquares(this.getSquares());
  }

  /**
   * @param s
   *          Array of squares that make up the board
   */
  private void initSquares(final Square[][] s) {
    for (int rank = 0; rank < getNumRanks(); rank++) {
      for (int file = 0; file < getNumFiles(); file++) {
        Square sq = s[file][rank];

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

    Square[] s = ataxxSquare.getOneAwaySquares();
    for (Square sq : s) {
      Piece piece = sq.getPiece();
      if (piece != null && piece.getColor() == oppositeColor) {
        piece.flip();
        ret.add(sq);
      }
    }

    return ret;
  }

  /**
   * Get the current score of the game.
   * 
   * @return the score object.
   */
  public final AtaxxScore getScore() {
    int numBlack = 0;
    int numWhite = 0;

    for (int rank = 0; rank < getNumRanks(); rank++) {
      for (int file = 0; file < getNumFiles(); file++) {
        Square ataxxSquare = this.getSquares()[file][rank];
        Piece attaxPiece = ataxxSquare.getPiece();
        if (attaxPiece != null) {
          if (attaxPiece.getColor() == PieceColor.BLACK) {
            numBlack++;
          } else {
            numWhite++;
          }
        }

      }
    }
    return new AtaxxScore(numBlack, numWhite);
  }

  @Override
  public final String toString() {
    StringBuilder s = new StringBuilder();
    s.append("   ");
    for (byte j = 0; j < this.getNumFiles(); j++) {
      s.append((char) ('a' + j));
    }
    s.append("\n\n");

    for (int i = 0; i < this.getNumRanks(); i++) {
      s.append(i + 1);
      s.append("  ");
      for (int j = 0; j < this.getNumFiles(); j++) {

        Square squareAt = getSquareAt(j, i);

        if (squareAt.isBlocked()) {
          s.append('X');
        } else {
          Piece p = squareAt.getPiece();
          if (p == null) {
            s.append(".");
          } else {
            s.append(p);
          }
        }
      }
      s.append("\n");
    }

    return s.toString();
  }

  /**
   * Set a square as blocked.
   * 
   * @param file
   *          File of Square
   * @param rank
   *          Rank of Square
   */
  public void setBlocked(final int file, final int rank) {
    Square sq = getSquareAt(file, rank);
    sq.setBlocked();
    // sq.setOneAwaySquares(getOneAwaySquares(sq));
    this.numBlockedSquares++;
  }

  /**
   * @return the number of blocked squares.
   */
  public int getNumBlockedSquares() {
    return this.numBlockedSquares;
  }
}
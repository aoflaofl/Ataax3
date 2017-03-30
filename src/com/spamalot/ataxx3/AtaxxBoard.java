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

  AtaxxBoard(final int size) {
    super(size);

    Square[][] s = this.getSquares();
    for (int rank = 0; rank < getNumRanks(); rank++) {
      for (int file = 0; file < getNumFiles(); file++) {
        Square sq = s[file][rank];

        sq.setOneAway(getOneAwaySquares(sq));
        sq.setTwoAway(getTwoAwaySquares(sq));
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
  final List<Square> flipPiecesAroundSquare(final Square ataxxSquare, final PieceColor color) {
    PieceColor oppositeColor = color.getOpposite();

    int minFile = Math.max(ataxxSquare.getFile() - 1, 0);
    int maxFile = Math.min(ataxxSquare.getFile() + 1, this.getNumFiles() - 1);
    int minRank = Math.max(ataxxSquare.getRank() - 1, 0);
    int maxRank = Math.min(ataxxSquare.getRank() + 1, this.getNumRanks() - 1);

    List<Square> ret = new ArrayList<>();

    for (int file = minFile; file <= maxFile; file++) {
      for (int rank = minRank; rank <= maxRank; rank++) {

        Square square = getSquareAt(file, rank);
        Piece piece = square.getPiece();

        if (piece != null && piece.getColor() == oppositeColor) {
          piece.flip();
          ret.add(square);
        }
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
    int black = 0;
    int white = 0;

    for (int i = 0; i < getNumRanks(); i++) {
      for (int j = 0; j < getNumFiles(); j++) {
        Square ataxxSquare = this.getSquares()[j][i];
        Piece p = ataxxSquare.getPiece();
        if (p != null) {
          if (p.getColor() == PieceColor.BLACK) {
            black++;
          } else {
            white++;
          }
        }

      }
    }
    return new AtaxxScore(black, white);
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
}
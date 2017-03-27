package com.spamalot.ataxx3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Generate moves.
 * 
 * @author gej
 *
 */
class AtaxxMoveGenerator {

  /** Board for moves. */
  private AtaxxGame ataxxGame;

  /** Avoid coordinates of expand moves that have already been seen. */
  private Set<Square> seen = new HashSet<>();

  /**
   * Construct the move generator.
   * 
   * @param game
   *          Board to construct generator for.
   */
  AtaxxMoveGenerator(final AtaxxGame game) {
    this.ataxxGame = game;
  }

  /**
   * Generate a list of moves.
   * 
   * @param toMove
   *          Color to move.
   * @return list of moves.
   */
  public List<AtaxxMove> getAvailableMoves(final PieceColor toMove) {
    List<AtaxxMove> result = new ArrayList<>();
    this.seen.clear();
    for (int rank = 0; rank < this.ataxxGame.getNumRanks(); rank++) {
      for (int file = 0; file < this.ataxxGame.getNumFiles(); file++) {
        Square sq = this.ataxxGame.getSquareAt(file, rank);
        if (sq.getPiece() != null && sq.getPiece().getColor().equals(toMove)) {
          result.addAll(generateMovesForSquare(sq));
        }
      }
    }

    return result;
  }

  /**
   * Generate a list of moves for a square.
   * 
   * @param square
   *          Square to generate moves for
   * @return A list of moves.
   */
  private List<AtaxxMove> generateMovesForSquare(final Square square) {
    int squareRank = square.getRank();
    int squareFile = square.getFile();

    List<AtaxxMove> result = new ArrayList<>();

    for (int fileDiff = -2; fileDiff <= 2; fileDiff++) {
      for (int rankDiff = -2; rankDiff <= 2; rankDiff++) {
        if (Math.abs(fileDiff) == 2 || Math.abs(rankDiff) == 2) {
          // We've got a jumper
          Square toSquare = this.ataxxGame.getSquareAt(squareFile + fileDiff, squareRank + rankDiff);
          if (toSquare != null && toSquare.isEmpty()) {
            result.add(new AtaxxMove(AtaxxMove.Type.JUMP, square.getPiece().getColor(), square, toSquare));
          }
        } else {
          if (!(fileDiff == 0 && rankDiff == 0)) {
            // It's an expansion
            Square toSquare = this.ataxxGame.getSquareAt(squareFile + fileDiff, squareRank + rankDiff);
            if (toSquare != null && toSquare.isEmpty()) {
              if (!this.seen.contains(toSquare)) {
                this.seen.add(toSquare);
                result.add(new AtaxxMove(AtaxxMove.Type.EXPAND, square.getPiece().getColor(), square, toSquare));
              }
            }
          }
        }
      }
    }
    return result;
  }

}
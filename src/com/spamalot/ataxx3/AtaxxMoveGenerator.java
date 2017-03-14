package com.spamalot.ataxx3;

import java.util.ArrayList;
import java.util.Collections;
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
  private Set<AtaxxSquare> seen = new HashSet<>();

  /**
   * Construct the move generator.
   * 
   * @param ataxxGame
   *          Board to construct generator for.
   */
  AtaxxMoveGenerator(final AtaxxGame ataxxGame) {
    this.ataxxGame = ataxxGame;
  }

  /**
   * Generate a list of moves.
   * 
   * @param toMove
   *          Color to move.
   * @return list of moves.
   */
  public List<AtaxxMove> getAvailableMoves(final AtaxxColor toMove) {
    List<AtaxxMove> result = new ArrayList<>();
    this.seen.clear();
    for (int rank = 0; rank < this.ataxxGame.getNumRanks(); rank++) {
      for (int file = 0; file < this.ataxxGame.getNumFiles(); file++) {
        AtaxxSquare sq = this.ataxxGame.getSquareAt(file, rank);
        if (sq.getPiece() != null && sq.getPiece().getColor().equals(toMove)) {
          result.addAll(generateMovesForSquare(sq));
        }
      }
    }
    Collections.sort(result);
    return result;
  }

  private List<AtaxxMove> generateMovesForSquare(final AtaxxSquare square) {
    int squareRank = square.getRank();
    int squareFile = square.getFile();

    List<AtaxxMove> result = new ArrayList<>();

    for (int fileDiff = -2; fileDiff <= 2; fileDiff++) {
      for (int rankDiff = -2; rankDiff <= 2; rankDiff++) {
        if (Math.abs(fileDiff) == 2 || Math.abs(rankDiff) == 2) {
          // We've got a jumper
          AtaxxSquare toSquare = this.ataxxGame.getSquareAt(squareFile + fileDiff, squareRank + rankDiff);
          if (toSquare != null && toSquare.isEmpty()) {
            result.add(new AtaxxMove(AtaxxMove.Type.JUMP, square.getPiece().getColor(), square, toSquare));
          }
        } else {
          if (!(fileDiff == 0 && rankDiff == 0)) {
            // It's an expansion
            AtaxxSquare toSquare = this.ataxxGame.getSquareAt(squareFile + fileDiff, squareRank + rankDiff);
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
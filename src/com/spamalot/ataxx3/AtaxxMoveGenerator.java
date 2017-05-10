package com.spamalot.ataxx3;

import com.spamalot.boardgame.Game;
import com.spamalot.boardgame.Move;
import com.spamalot.boardgame.PieceColor;
import com.spamalot.boardgame.Square;

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
  private Game ataxxGame;

  /** Avoid coordinates of expand moves that have already been seen. */
  private Set<Square> seen = new HashSet<>();

  /**
   * Construct the move generator.
   * 
   * @param game
   *          Board to construct generator for.
   */
  AtaxxMoveGenerator(final Game game) {
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

    if (result.size() == 0) {
      result.add(new AtaxxMove());
    }

    return result;
  }

  /**
   * Generate a list of moves for a square.
   * 
   * @param fromSquare
   *          Square to generate moves for
   * @return A list of moves.
   */
  private static Set<AtaxxMove> generateMovesForSquare(final Square fromSquare) {

    Set<AtaxxMove> result = new HashSet<>();

    for (Square sq : fromSquare.getOneAwaySquares()) {
      if (sq.isEmpty()) {
        result.add(new AtaxxMove(Move.Type.DROP, fromSquare.getPiece().getColor(), fromSquare, sq));
      }
    }

    for (Square sq : fromSquare.getTwoAwaySquares()) {
      if (sq.isEmpty()) {
        result.add(new AtaxxMove(Move.Type.JUMP, fromSquare.getPiece().getColor(), fromSquare, sq));
      }
    }

    return result;
  }

}

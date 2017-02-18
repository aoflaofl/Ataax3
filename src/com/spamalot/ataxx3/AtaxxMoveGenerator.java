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
  private AtaxxBoard boardObj;

  /** Avoid coordinates that have already been seen. */
  private Set<Coordinate> seen = new HashSet<>();

  /**
   * Construct the move generator.
   * 
   * @param inBoard
   *          Board to construct generator for.
   */
  AtaxxMoveGenerator(final AtaxxBoard inBoard) {
    this.boardObj = inBoard;
  }

  /**
   * Build list of expansion moves.
   * 
   * @param ataxxPiece
   *          the piece.
   * @param i
   *          the i
   * @param j
   *          the j
   * @return a list of expand moves.
   */
  private List<AtaxxMove> expandMoves(final AtaxxPiece ataxxPiece, final int i, final int j) {

    Coordinate fromCoord = new Coordinate(i, j);

    int minX = Math.max(fromCoord.getX() - 1, 0);
    int maxX = Math.min(fromCoord.getX() + 1, this.boardObj.getWidth() - 1);
    int minY = Math.max(fromCoord.getY() - 1, 0);
    int maxY = Math.min(fromCoord.getY() + 1, this.boardObj.getHeight() - 1);

    List<AtaxxMove> result = new ArrayList<>();

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {
        if (this.boardObj.getPieceAt(x, y) == null) {
          Coordinate toCoord = new Coordinate(x, y);
          if (!this.seen.contains(toCoord)) {
            result.add(new AtaxxMove(AtaxxMove.Type.EXPAND, ataxxPiece.getColor(), fromCoord, toCoord));
            this.seen.add(toCoord);
          }
        }
      }
    }

    return result;
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
    this.seen = new HashSet<>();
    for (int i = 0; i < this.boardObj.getHeight(); i++) {
      for (int j = 0; j < this.boardObj.getWidth(); j++) {
        if (this.boardObj.getPieceAt(i, j) != null && this.boardObj.getPieceAt(i, j).getColor().equals(toMove)) {

          result.addAll(expandMoves(this.boardObj.getPieceAt(i, j), i, j));

        }
      }
    }
    return result;
  }

}
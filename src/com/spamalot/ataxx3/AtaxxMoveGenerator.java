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
  private AtaxxGame boardObj;

  /** Avoid coordinates of expand moves that have already been seen. */
  private Set<Coordinate> seen = new HashSet<>();

  /**
   * Construct the move generator.
   * 
   * @param ataxxGame
   *          Board to construct generator for.
   */
  AtaxxMoveGenerator(final AtaxxGame ataxxGame) {
    this.boardObj = ataxxGame;
  }

  /**
   * Build list of expansion moves.
   * 
   * @param ataxxColor
   *          the piece
   * @param fromCoord
   *          Coordinate from
   * @return a list of expand moves.
   */
  private List<AtaxxMove> expandMoves(final AtaxxColor ataxxColor, final Coordinate fromCoord) {
    int minX = Math.max(fromCoord.getX() - 1, 0);
    int maxX = Math.min(fromCoord.getX() + 1, this.boardObj.getWidth() - 1);
    int minY = Math.max(fromCoord.getY() - 1, 0);
    int maxY = Math.min(fromCoord.getY() + 1, this.boardObj.getHeight() - 1);

    List<AtaxxMove> result = new ArrayList<>();

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {
        if (this.boardObj.getColorOfPieceAt(new Coordinate(x, y)) == null) {
          Coordinate toCoord = new Coordinate(x, y);
          if (!this.seen.contains(toCoord)) {
            result.add(new AtaxxMove(AtaxxMove.Type.EXPAND, ataxxColor, fromCoord, toCoord));
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
        if (this.boardObj.getColorOfPieceAt(new Coordinate(i, j)) != null && this.boardObj.getColorOfPieceAt(new Coordinate(i, j)).equals(toMove)) {
          Coordinate fromCoord = new Coordinate(i, j);
          result.addAll(expandMoves(toMove, fromCoord));
          //result.addAll(jumpMoves(toMove, fromCoord));
        }
      }
    }
    return result;
  }

  /**
   * Find jump moves.
   * 
   * @param toMove
   *          Color to move
   * @param fromCoord
   *          coordinate jumping from
   * @return list of jump moves.
   */
  private List<AtaxxMove> jumpMoves(final AtaxxColor toMove, final Coordinate fromCoord) {
    // TODO Auto-generated method stub
    return null;
  }

}
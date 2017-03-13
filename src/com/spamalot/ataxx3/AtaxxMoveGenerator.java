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
  private Set<Coordinate> seen = new HashSet<>();

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
    int maxX = Math.min(fromCoord.getX() + 1, this.ataxxGame.getWidth() - 1);
    int minY = Math.max(fromCoord.getY() - 1, 0);
    int maxY = Math.min(fromCoord.getY() + 1, this.ataxxGame.getHeight() - 1);

    List<AtaxxMove> result = new ArrayList<>();

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {
        if (this.ataxxGame.getSquareAt(x, y).getPiece() == null) {
          Coordinate toCoord = new Coordinate(x, y);
          if (!this.seen.contains(toCoord)) {
            AtaxxSquare f = this.ataxxGame.getSquareAt(fromCoord.getX(), fromCoord.getY());
            AtaxxSquare t = this.ataxxGame.getSquareAt(toCoord.getX(), toCoord.getY());
            result.add(new AtaxxMove(AtaxxMove.Type.EXPAND, ataxxColor, f, t));
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
    for (int i = 0; i < this.ataxxGame.getHeight(); i++) {
      for (int j = 0; j < this.ataxxGame.getWidth(); j++) {
        if (this.ataxxGame.getSquareAt(i, j).getPiece() != null && this.ataxxGame.getSquareAt(i, j).getPiece().getColor().equals(toMove)) {
          Coordinate fromCoord = new Coordinate(i, j);
          result.addAll(expandMoves(toMove, fromCoord));
          result.addAll(jumpMoves(toMove, fromCoord));
        }
      }
    }
    Collections.sort(result);
    return result;
  }

  /**
   * Find jump moves.
   * 
   * @param colorToMove
   *          Color to move
   * @param fromCoord
   *          coordinate jumping from
   * @return list of jump moves.
   */
  private List<AtaxxMove> jumpMoves(final AtaxxColor colorToMove, final Coordinate fromCoord) {
    AtaxxSquare f = this.ataxxGame.getSquareAt(fromCoord.getX(), fromCoord.getY());

    List<AtaxxMove> result = new ArrayList<>();

    for (int i = -2; i <= 2; i += 2) {
      for (int j = -2; j <= 2; j += 2) {
        if (!(i == 0 && j == 0)) {
          AtaxxMove m = new AtaxxMove(AtaxxMove.Type.JUMP, colorToMove, f, this.ataxxGame.getSquareAt(fromCoord.getX() + i, fromCoord.getY() + j));
          if (this.ataxxGame.isOnBoard(m) && AtaxxGame.toSquareIsEmpty(m)) {
            result.add(m);
          }
        }
      }
    }

    // TODO: Clean up, but avoid premature optimizing
    int i = -2;
    int j = -1;

    AtaxxMove m = new AtaxxMove(AtaxxMove.Type.JUMP, colorToMove, f, this.ataxxGame.getSquareAt(fromCoord.getX() + i, fromCoord.getY() + j));
    if (this.ataxxGame.isOnBoard(m) && AtaxxGame.toSquareIsEmpty(m)) {
      result.add(m);
    }

    j = 1;
    m = new AtaxxMove(AtaxxMove.Type.JUMP, colorToMove, f, this.ataxxGame.getSquareAt(fromCoord.getX() + i, fromCoord.getY() + j));
    if (this.ataxxGame.isOnBoard(m) && AtaxxGame.toSquareIsEmpty(m)) {
      result.add(m);
    }

    i = -1;
    j = -2;
    m = new AtaxxMove(AtaxxMove.Type.JUMP, colorToMove, f, this.ataxxGame.getSquareAt(fromCoord.getX() + i, fromCoord.getY() + j));
    if (this.ataxxGame.isOnBoard(m) && AtaxxGame.toSquareIsEmpty(m)) {
      result.add(m);
    }

    j = 2;
    m = new AtaxxMove(AtaxxMove.Type.JUMP, colorToMove, f, this.ataxxGame.getSquareAt(fromCoord.getX() + i, fromCoord.getY() + j));
    if (this.ataxxGame.isOnBoard(m) && AtaxxGame.toSquareIsEmpty(m)) {
      result.add(m);
    }

    i = 1;
    j = -2;
    m = new AtaxxMove(AtaxxMove.Type.JUMP, colorToMove, f, this.ataxxGame.getSquareAt(fromCoord.getX() + i, fromCoord.getY() + j));
    if (this.ataxxGame.isOnBoard(m) && AtaxxGame.toSquareIsEmpty(m)) {
      result.add(m);
    }

    j = 2;
    m = new AtaxxMove(AtaxxMove.Type.JUMP, colorToMove, f, this.ataxxGame.getSquareAt(fromCoord.getX() + i, fromCoord.getY() + j));
    if (this.ataxxGame.isOnBoard(m) && AtaxxGame.toSquareIsEmpty(m)) {
      result.add(m);
    }

    i = 2;
    j = -1;
    m = new AtaxxMove(AtaxxMove.Type.JUMP, colorToMove, f, this.ataxxGame.getSquareAt(fromCoord.getX() + i, fromCoord.getY() + j));
    if (this.ataxxGame.isOnBoard(m) && AtaxxGame.toSquareIsEmpty(m)) {
      result.add(m);
    }

    j = +1;
    m = new AtaxxMove(AtaxxMove.Type.JUMP, colorToMove, f, this.ataxxGame.getSquareAt(fromCoord.getX() + i, fromCoord.getY() + j));
    if (this.ataxxGame.isOnBoard(m) && AtaxxGame.toSquareIsEmpty(m)) {
      result.add(m);
    }

    return result;
  }

}
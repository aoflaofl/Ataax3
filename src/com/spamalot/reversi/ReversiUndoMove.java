package com.spamalot.reversi;

import com.spamalot.boardgame.Piece;
import com.spamalot.boardgame.UndoMove;

import java.util.List;

/**
 * Class to handle Undo Information.
 * 
 * @author gej
 *
 */
public class ReversiUndoMove extends UndoMove<ReversiMove> {

  /**
   * Class to hold undo information.
   * 
   * @param move
   *          Move to Undo
   * @param piecesToFlip
   *          Pieces Flipped
   */
  public ReversiUndoMove(final ReversiMove move, final List<Piece> piecesToFlip) {
    setMove(move);
  }

  /** List of pieces that have been flipped. */
  private List<Piece> flippedPieces;

  /**
   * Set the flipped pieces.
   * 
   * @param flipped
   *          the flipped pieces
   */
  public void setFlipped(final List<Piece> flipped) {
    this.flippedPieces = flipped;
  }

  /**
   * Get the flipped piece list.
   * 
   * @return the flipped
   */
  public List<Piece> getFlipped() {
    return this.flippedPieces;
  }

}

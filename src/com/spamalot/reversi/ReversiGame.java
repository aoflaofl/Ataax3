package com.spamalot.reversi;

import com.spamalot.ataxx3.Evaluatable;
import com.spamalot.boardgame.Game;
import com.spamalot.boardgame.GameException;
import com.spamalot.boardgame.MinMaxSearchable;
import com.spamalot.boardgame.Piece;
import com.spamalot.boardgame.PieceColor;

/**
 * Handle the Reversi Game.
 * 
 * @author gej
 *
 */
class ReversiGame extends Game implements MinMaxSearchable {
  /** Default Board Size Constant. */
  private static final int DEFAULT_REVERSI_BOARD_SIZE = 8;

  /**
   * Create a Reversi board of the default size.
   * 
   * @throws GameException
   *           if something goes wrong.
   */
  ReversiGame() throws GameException {
    this(DEFAULT_REVERSI_BOARD_SIZE);
  }

  /**
   * Construct the Ataxx game with a square board of a specified size.
   * 
   * @param size
   *          Size of a side
   * @throws GameException
   *           when there is some Ataxx related problem.
   */
  private ReversiGame(final int size) throws GameException {
    setBoard(new ReversiBoard(size));
  }

  /**
   * Set up board.
   */
  @Override
  protected void initBoard() {

    dropPiece(new Piece(PieceColor.WHITE), getBoard().getSquareAt(3, 3));
    dropPiece(new Piece(PieceColor.WHITE), getBoard().getSquareAt(4, 4));

    dropPiece(new Piece(PieceColor.BLACK), getBoard().getSquareAt(3, 4));
    dropPiece(new Piece(PieceColor.BLACK), getBoard().getSquareAt(4, 3));

  }

  @Override
  public boolean isOver() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int evaluate(final boolean gameOver) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void undoLastMove() {
    // TODO Auto-generated method stub

  }

  @Override
  public void makeMove(final Evaluatable move) {
    // TODO Auto-generated method stub

  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ReversiGame [board=\n");
    builder.append(getBoard());
    builder.append("toMove=");
    builder.append(getColorToMove());
    // builder.append("\ngetAvailableMoves()=");
    // builder.append(getAvailableMoves());
    // builder.append("\nUndo move list=" + this.undoMoveStack);
    builder.append("\n]");
    return builder.toString();
  }
}

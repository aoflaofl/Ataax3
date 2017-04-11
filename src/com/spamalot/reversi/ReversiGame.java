package com.spamalot.reversi;

import com.spamalot.ataxx3.Evaluatable;
import com.spamalot.boardgame.GameException;
import com.spamalot.boardgame.MinMaxSearchable;
import com.spamalot.boardgame.PieceColor;

/**
 * Handle the Reversi Game.
 * 
 * @author gej
 *
 */
class ReversiGame implements MinMaxSearchable {
  /** Default Board Size Constant. */
  private static final int DEFAULT_REVERSI_BOARD_SIZE = 8;
  /** The board. */
  private ReversiBoard board;

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
    this.board = new ReversiBoard(size);
    initBoard();
  }

  /**
   * Set up board.
   */
  private void initBoard() {
    // TODO Auto-generated method stub

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
  public PieceColor getToMove() {
    // TODO Auto-generated method stub
    return null;
  }
}

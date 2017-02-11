package com.spamalot.ataxx3;

import java.util.List;

/**
 * Handle a game of Ataxx.
 * 
 * @author gej
 *
 */
class AtaxxGame {
  /** Default Board Size Constant. */
  private static final int DEFAULT_BOARD_SIZE = 7;

  /** The board for this game. */
  private AtaxxBoard board;

  /** Which color is moving. White moves first. */
  private AtaxxColor toMove = AtaxxColor.WHITE;

  /**
   * Construct the Ataxx game.
   * 
   * @throws AtaxxException
   *           when there is some Ataxx related problem.
   */
  AtaxxGame() throws AtaxxException {
    this(DEFAULT_BOARD_SIZE);
  }

  /**
   * Construct the Ataxx game with a square board of a specified size.
   * 
   * @param size
   *          Size of a side
   * @throws AtaxxException
   *           when there is some Ataxx related problem.
   */
  private AtaxxGame(final int size) throws AtaxxException {
    this.board = new AtaxxBoard(size);
    System.out.println(this.board);
    initBoard();
    System.out.println(this.board);

    AtaxxMove move1 = new AtaxxMove(AtaxxMove.Type.EXPAND, AtaxxColor.WHITE, new Coordinate(0, 0), new Coordinate(1, 1));
    AtaxxMove move2 = new AtaxxMove(AtaxxMove.Type.JUMP, AtaxxColor.WHITE, new Coordinate(0, 0), new Coordinate(2, 2));

    this.board.makeMove(move1);
    System.out.println(this.board);
    this.board.makeMove(move2);
    System.out.println(this.board);
  }

  /**
   * Put the initial pieces for a standard game of Ataxx.
   * 
   * @throws AtaxxException
   *           when there is some Ataxx related problem.
   */
  private void initBoard() throws AtaxxException {
    this.board.setPiece(AtaxxColor.WHITE, 0, 0);
    this.board.setPiece(AtaxxColor.WHITE, this.board.getHeight() - 1, this.board.getWidth() - 1);

    this.board.setPiece(AtaxxColor.BLACK, 0, this.board.getWidth() - 1);
    this.board.setPiece(AtaxxColor.BLACK, this.board.getHeight() - 1, 0);

    System.out.println(this.board);

  }

  public List<AtaxxMove> getAvailableMoves() {
    return this.board.getAvailableMoves(this.toMove);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AtaxxGame [board=\n").append(this.board).append("toMove=").append(this.toMove).append("\ngetAvailableMoves()=").append(getAvailableMoves()).append("]");
    return builder.toString();
  }

}

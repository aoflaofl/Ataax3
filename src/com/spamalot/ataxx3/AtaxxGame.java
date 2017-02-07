package com.spamalot.ataxx3;

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

  /**
   * Construct the Ataxx game.
   * 
   * @throws AtaxxException
   *           when there is some Ataax related problem.
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
   *           when there is some Ataax related problem.
   */
  AtaxxGame(final int size) throws AtaxxException {
    this.board = new AtaxxBoard(size);
    System.out.println(board);
    initBoard();
    System.out.println(board);

    AtaxxMove m = new AtaxxMove(AtaxxMove.Type.EXPAND, AtaxxColor.WHITE, new Coordinate(0, 0), new Coordinate(1, 1));
    AtaxxMove m2 = new AtaxxMove(AtaxxMove.Type.JUMP, AtaxxColor.WHITE, new Coordinate(0, 0), new Coordinate(2, 2));

    board.makeMove(m);
    System.out.println(board);
    board.makeMove(m2);
    System.out.println(board);
  }

  /**
   * Put the initial pieces for a standard game of Ataxx.
   * 
   * @throws AtaxxException
   *           when there is some Ataax related problem.
   */
  private void initBoard() throws AtaxxException {
    board.setPiece(AtaxxColor.WHITE, 0, 0);
    board.setPiece(AtaxxColor.WHITE, board.getHeight() - 1, board.getWidth() - 1);

    board.setPiece(AtaxxColor.BLACK, 0, board.getWidth() - 1);
    board.setPiece(AtaxxColor.BLACK, board.getHeight() - 1, 0);

    System.out.println(board);

  }

}

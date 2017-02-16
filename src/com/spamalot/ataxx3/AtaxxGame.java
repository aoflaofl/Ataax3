package com.spamalot.ataxx3;

import java.util.List;
import java.util.Stack;

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

  /** Stack for move list. */
  private Stack<AtaxxUndoMove> moveStack = new Stack<>();

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

    makeMove(move1);
    System.out.println(this.board);

    makeMove(move2);
    System.out.println(this.board);

    undoLastMove();
    System.out.println(this.board);
  }

  /**
   * Make a move in Ataxx game.
   * 
   * @param move
   *          The move to make
   * @throws AtaxxException
   *           When something goes wrong
   */
  private void makeMove(final AtaxxMove move) throws AtaxxException {
    this.board.makeMove(move);
    List<Coordinate> flipped = this.board.flipPiecesAroundToSquare(move);
    this.moveStack.push(new AtaxxUndoMove(move, flipped));
  }

  /**
   * Undo the effects of the last move made.
   */
  private void undoLastMove() {
    AtaxxUndoMove move = this.moveStack.pop();
    this.board.undoMove(move.getMove());
    this.board.unflip(move.getFlipped());
  }

  /**
   * Put the initial pieces for a standard game of Ataxx.
   * 
   * TODO: Possibly add barrier squares.
   * 
   * @throws AtaxxException
   *           when there is some Ataxx related problem.
   */
  private void initBoard() throws AtaxxException {
    this.board.setPiece(AtaxxColor.WHITE, new Coordinate(0, 0));
    this.board.setPiece(AtaxxColor.WHITE, new Coordinate(this.board.getHeight() - 1, this.board.getWidth() - 1));

    this.board.setPiece(AtaxxColor.BLACK, new Coordinate(0, this.board.getWidth() - 1));
    this.board.setPiece(AtaxxColor.BLACK, new Coordinate(this.board.getHeight() - 1, 0));

    System.out.println(this.board);

  }

  /**
   * Get a list of moves.
   * 
   * @return a list of available moves.
   */
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
    builder.append("AtaxxGame [board=\n");
    builder.append(this.board);
    builder.append("toMove=");
    builder.append(this.toMove);
    builder.append("\ngetAvailableMoves()=");
    builder.append(getAvailableMoves());
    builder.append("\nUndo move list=" + this.moveStack);
    builder.append("\n]");
    return builder.toString();
  }

}

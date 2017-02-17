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
    System.out.println("a" + this.board);
    initBoard();
    System.out.println("b" + this.board);

    AtaxxPiece x = pickupPiece(new Coordinate(0, 0));
    System.out.println("c" + this.board);
    dropPiece(x, new Coordinate(0, 0));
    System.out.println(this.board);

    AtaxxMove move1 = new AtaxxMove(AtaxxMove.Type.EXPAND, AtaxxColor.WHITE, new Coordinate(0, 0), new Coordinate(1, 1));
    AtaxxMove move2 = new AtaxxMove(AtaxxMove.Type.JUMP, AtaxxColor.WHITE, new Coordinate(0, 0), new Coordinate(2, 2));

    makeMove(move1);
    System.out.println(this.board);

    makeMove(move2);
    System.out.println(this.board);

    undoLastMove();
    System.out.println(this.board);

    makeMove(move2);
    System.out.println(this.board);

    AtaxxMove move3 = new AtaxxMove(AtaxxMove.Type.EXPAND, AtaxxColor.BLACK, new Coordinate(0, 6), new Coordinate(1, 5));
    makeMove(move3);
    System.out.println(this.board);

    AtaxxMove move4 = new AtaxxMove(AtaxxMove.Type.JUMP, AtaxxColor.BLACK, new Coordinate(1, 5), new Coordinate(1, 3));
    makeMove(move4);
    System.out.println(this.board);
    undoLastMove();
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
    // TODO: Check for legality
    if (!isLegal(move)) {
      System.out.println("Problem with move.");
    }
    switch (move.getType()) {
      case EXPAND:
        dropPiece(new AtaxxPiece(move.getColor()), move.getTo());
        // this.board[move.getTo().getX()][move.getTo().getY()] = new
        // AtaxxPiece(move.getColor());
        break;
      case JUMP:
        AtaxxPiece p = pickupPiece(move.getFrom());
        dropPiece(p, move.getTo());

        // this.board[move.getTo().getX()][move.getTo().getY()] =
        // this.board[move.getFrom().getX()][move.getFrom().getY()];
        // this.board[move.getFrom().getX()][move.getFrom().getY()] = null;
        break;
      default:
        break;
    }
    List<Coordinate> flipped = this.board.flipPiecesAroundSquare(move.getTo(), move.getColor());
    this.moveStack.push(new AtaxxUndoMove(move, flipped));
  }

  /**
   * Undo the effects of the last move made.
   */
  private void undoLastMove() {
    AtaxxUndoMove move = this.moveStack.pop();
    this.undoMove(move.getMove());
    this.board.flipPieces(move.getFlipped());
  }

  /**
   * Undo the effects a move.
   * 
   * @param move
   *          the move
   */
  private void undoMove(final AtaxxMove move) {
    switch (move.getType()) {
      case EXPAND:
        this.board.getBoard()[move.getTo().getX()][move.getTo().getY()] = null;
        break;
      case JUMP:
        this.board.getBoard()[move.getTo().getX()][move.getTo().getY()] = null;
        this.board.getBoard()[move.getFrom().getX()][move.getFrom().getY()] = new AtaxxPiece(move.getColor());
        break;
      default:
        break;
    }
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
    dropPiece(new AtaxxPiece(AtaxxColor.WHITE), new Coordinate(0, 0));
    dropPiece(new AtaxxPiece(AtaxxColor.WHITE), new Coordinate(this.board.getHeight() - 1, this.board.getWidth() - 1));

    dropPiece(new AtaxxPiece(AtaxxColor.BLACK), new Coordinate(0, this.board.getWidth() - 1));
    dropPiece(new AtaxxPiece(AtaxxColor.BLACK), new Coordinate(this.board.getHeight() - 1, 0));
  }

  /**
   * Get a list of moves.
   * 
   * @return a list of available moves.
   */
  public List<AtaxxMove> getAvailableMoves() {
    return this.getAvailableMoves(this.toMove);
  }

  /**
   * Generate moves.
   * 
   * @param toMv
   *          Color to move.
   * @return a list of moves.
   */
  private List<AtaxxMove> getAvailableMoves(final AtaxxColor toMv) {
    AtaxxMoveGenerator gen = new AtaxxMoveGenerator(this.board);
    return gen.getAvailableMoves(toMv);
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

  /**
   * Pick up a piece from the board.
   * 
   * TODO: Exception if square empty.
   * 
   * @param c
   *          Coordinate to pick up piece at
   * @return the Piece.
   * @throws AtaxxException
   *           if no piece in square
   */
  private AtaxxPiece pickupPiece(final Coordinate c) throws AtaxxException {
    AtaxxPiece p = this.board.getPieceAtCoord(c);
    if (p == null) {
      throw new AtaxxException("No piece in square.");
    }
    this.board.putPieceAtCoord(null, c);
    return p;
  }

  /**
   * Drop a piece on the board.
   * 
   * TODO: Exception if square already occupied.
   * 
   * @param p
   *          the Piece
   * @param c
   *          the Coordinate
   * @throws AtaxxException
   *           if square is not empty
   */
  private void dropPiece(final AtaxxPiece p, final Coordinate c) throws AtaxxException {
    if (this.board.getPieceAtCoord(c) == null) {
      this.board.putPieceAtCoord(p, c);
    } else {
      throw new AtaxxException("Square is not empty.");
    }
  }

  /**
   * Check if a move is legal.
   * 
   * @param move
   *          the move to check
   * @return true if the move is legal to make on this board.
   */
  private boolean isLegal(final AtaxxMove move) {
    return isLegalColor(move) && isOnBoard(move) && toSquareIsEmpty(move) && pieceInFromSquareMatchesColor(move);
  }

  /**
   * Check whether the color in the move is legal.
   * 
   * @param move
   *          the move to check
   * @return true if this is a legal color in an Ataxx game.
   */
  private static boolean isLegalColor(final AtaxxMove move) {
    return (move.getColor() == AtaxxColor.WHITE || move.getColor() == AtaxxColor.BLACK);
  }

  /**
   * @param move
   *          the move to check
   * @return true if the squares involved are on the board.
   */
  private boolean isOnBoard(final AtaxxMove move) {
    return (this.board.isOnBoard(move.getFrom()) && this.board.isOnBoard(move.getTo()));
  }

  /**
   * Check from piece is the correct color.
   * 
   * @param move
   *          the move
   * @return true if the from piece has the correct color for the move.
   */
  private boolean pieceInFromSquareMatchesColor(final AtaxxMove move) {
    return this.board.getPieceAtCoord(move.getFrom()).getColor() == move.getColor();
  }

  /**
   * @param move
   *          the move to check.
   * @return true if the square is empty.
   */
  private boolean toSquareIsEmpty(final AtaxxMove move) {
    return this.board.squareIsEmpty(move.getTo());
  }

}

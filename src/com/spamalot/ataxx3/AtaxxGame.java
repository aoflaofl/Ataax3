package com.spamalot.ataxx3;

import java.util.List;
import java.util.Stack;

/**
 * Handle a game of Ataxx.
 * 
 * @author gej
 *
 */
public class AtaxxGame {
  /** Default Board Size Constant. */
  private static final int DEFAULT_BOARD_SIZE = 7;

  /** The board for this game. */
  private AtaxxBoard board;

  /** Which color is moving. White moves first. */
  private AtaxxColor colorToMove = AtaxxColor.WHITE;

  /** Stack for move list. */
  private Stack<AtaxxUndoMove> moveStack = new Stack<>();

  /**
   * Construct the Ataxx game.
   * 
   * @throws AtaxxException
   *           when there is some Ataxx related problem.
   */
  public AtaxxGame() throws AtaxxException {
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
   * Make a move in Ataxx game.
   * 
   * @param move
   *          The move to make
   * @throws AtaxxException
   *           When something goes wrong
   */
  private void makeMove(final AtaxxMove move) throws AtaxxException {
    if (!isLegal(move)) {
      System.out.println("Problem with move.");
    }

    AtaxxPiece piece = null;
    switch (move.getType()) {
      case EXPAND:
        piece = new AtaxxPiece(move.getColor());
        break;
      case JUMP:
        piece = pickupPiece(move.getFrom());
        break;
      default:
        throw new AtaxxException(move, "Wrong Move Type.");
    }
    dropPiece(piece, move.getTo());

    List<Coordinate> flipped = this.board.flipPiecesAroundSquare(move.getTo(), move.getColor());

    this.moveStack.push(new AtaxxUndoMove(move, flipped));
  }

  /**
   * Undo the effects of the last move made.
   */
  private void undoLastMove() {
    AtaxxUndoMove move = this.moveStack.pop();
    this.undoMove(move.getMove());
    this.board.flipPiecesAtCoordinates(move.getFlipped());
  }

  /**
   * Undo the effects a move.
   * 
   * @param move
   *          the move
   */
  private void undoMove(final AtaxxMove move) {
    this.board.putPieceAtCoord(null, move.getTo());
    if (move.getType() == AtaxxMove.Type.JUMP) {
      this.board.putPieceAtCoord(new AtaxxPiece(move.getColor()), move.getFrom());
    }
  }

  /**
   * Get a list of moves.
   * 
   * @return a list of available moves.
   */
  public List<AtaxxMove> getAvailableMoves() {
    return this.getAvailableMoves(this.colorToMove);
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

  /**
   * Pick up a piece from the board.
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
    boolean ret = isLegalColor(move) && isOnBoard(move) && toSquareIsEmpty(move) && pieceInFromSquareMatchesColor(move) && checkExpandDistance(move);

    return ret;
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

  /**
   * Check distance between from square and to square that this is a legal
   * expand move.
   * 
   * @param move
   *          the move to check
   * @return true if the to square can be expanded to from the from square.
   */
  private static boolean checkExpandDistance(final AtaxxMove move) {
    int xDiff = Math.abs(move.getTo().getX() - move.getFrom().getX());
    int yDiff = Math.abs(move.getTo().getY() - move.getFrom().getY());

    // If it's the same square, obviously not right
    if (xDiff == 0 && yDiff == 0) {
      return false;
    }

    if (move.getType() == AtaxxMove.Type.EXPAND) {
      if (xDiff > 1 || yDiff > 1) {
        return false;
      }
    } else if (move.getType() == AtaxxMove.Type.JUMP) {
      if (xDiff == 2 && yDiff > 2) {
        return false;
      }
      if (yDiff == 2 && xDiff > 2) {
        return false;
      }
    }

    return true;
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
    builder.append(this.colorToMove);
    builder.append("\ngetAvailableMoves()=");
    builder.append(getAvailableMoves());
    builder.append("\nUndo move list=" + this.moveStack);
    builder.append("\n]");
    return builder.toString();
  }

  /**
   * Allow compare of color. For testing purposes.
   * 
   * @param coord
   *          Coordinate of piece to check
   * @param clr
   *          Color to compare to
   * @return true if they match.
   */
  public final boolean compareColorOfPieceAt(final Coordinate coord, final AtaxxColor clr) {
    AtaxxPiece p = this.board.getPieceAtCoord(coord);
    return p.getColor() == clr;
  }
}

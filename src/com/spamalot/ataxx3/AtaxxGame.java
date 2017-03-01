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

  private final int width;
  private final int height;

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
    initBoard();

    this.height = size;
    this.width = size;

    System.out.println(this);
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
  public void makeMove(final AtaxxMove move) throws AtaxxException {
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
  public void undoLastMove() {
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
    AtaxxMoveGenerator gen = new AtaxxMoveGenerator(this);
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
  public AtaxxPiece pickupPiece(final Coordinate c) throws AtaxxException {
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
  public void dropPiece(final AtaxxPiece p, final Coordinate c) throws AtaxxException {
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
  final boolean isOnBoard(final AtaxxMove move) {
    return (this.board.isOnBoard(move.getFrom()) && this.board.isOnBoard(move.getTo()));
  }

  /**
   * Check from piece is the correct color.
   * 
   * @param move
   *          the move
   * @return true if the from piece exists and has the correct color for the
   *         move.
   */
  private boolean pieceInFromSquareMatchesColor(final AtaxxMove move) {
    AtaxxPiece p = this.board.getPieceAtCoord(move.getFrom());
    if (p == null) {
      return false;
    }
    return p.getColor() == move.getColor();
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
   * Allow compare of color.
   * 
   * @param coord
   *          Coordinate of piece to check
   * @return true if they match.
   */
  public final AtaxxColor getColorOfPieceAt(final Coordinate coord) {
    AtaxxPiece p = this.board.getPieceAtCoord(coord);
    if (p == null) {
      return null;
    }
    return p.getColor();
  }

  /**
   * @return the width
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * @return the height
   */
  public int getHeight() {
    return this.height;
  }

  public final AtaxxScore getScore() {
    return this.board.getScore();
  }

  final String boardToString() {
    return this.board.toString();
  }

  final AtaxxMove parseMove(final String text) throws AtaxxException {
    if (text.length() != 4) {
      throw new AtaxxException("Not an Ataxx move.");
    }

    Coordinate from = textPositionToCoordinate(text.substring(0, 2));
    Coordinate to = textPositionToCoordinate(text.substring(2, 4));

    int diff = Coordinate.maxDiff(from, to);

    if (diff > 2) {
      throw new AtaxxException("Illegal Move, too far away");
    }

    AtaxxMove.Type moveType = null;
    if (diff == 2) {
      moveType = AtaxxMove.Type.JUMP;
    } else {
      moveType = AtaxxMove.Type.EXPAND;
    }

    return new AtaxxMove(moveType, this.colorToMove, from, to);
  }

  /**
   * Turn text position into a Coordinate.
   * 
   * @param text
   *          Text to turn into coordinate
   * @return the Coordinate.
   */
  private static Coordinate textPositionToCoordinate(final String text) {
    char file = text.charAt(0);
    char rank = text.charAt(1);
    int x = file - 'a';
    int y = rank - '0' - 1;
    Coordinate coord = new Coordinate(x, y);
    return coord;
  }

}

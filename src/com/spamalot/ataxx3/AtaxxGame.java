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

  /** Which color is currently to move. White moves first. */
  private AtaxxColor colorToMove = AtaxxColor.WHITE;

  /** Stack for undo move list. */
  private Stack<AtaxxUndoMove> undoMoveStack = new Stack<>();

  /**
   * Construct the Ataxx game with default size of side.
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
    initBoard();
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
  void makeMove(final AtaxxMove move) throws AtaxxException {
    if (!isLegal(move)) {
      throw new AtaxxException(move, "Problem with move.");
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

    this.undoMoveStack.push(new AtaxxUndoMove(move, flipped));

    this.colorToMove = this.colorToMove.getOpposite();
  }

  /**
   * Undo the effects of the last move made.
   */
  void undoLastMove() {
    AtaxxUndoMove move = this.undoMoveStack.pop();
    this.undoPieceMove(move.getMove());
    this.board.flipPiecesAtCoordinates(move.getFlipped());
    this.colorToMove = this.colorToMove.getOpposite();
  }

  /**
   * Undo the piece move effects of a move.
   * 
   * @param move
   *          the move
   */
  private void undoPieceMove(final AtaxxMove move) {
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
   * @param toMove
   *          Color to move.
   * @return a list of moves.
   */
  private List<AtaxxMove> getAvailableMoves(final AtaxxColor toMove) {
    AtaxxMoveGenerator gen = new AtaxxMoveGenerator(this);
    return gen.getAvailableMoves(toMove);
  }

  /**
   * Pick up a piece from the board. This removes the piece from the board.
   * 
   * @param coord
   *          Coordinate to pick up piece
   * @return the Piece.
   * @throws AtaxxException
   *           if no piece in square
   */
  AtaxxPiece pickupPiece(final Coordinate coord) throws AtaxxException {
    AtaxxPiece piece = this.board.getPieceAtCoord(coord);
    if (piece == null) {
      throw new AtaxxException("No piece in square.");
    }
    this.board.putPieceAtCoord(null, coord);
    return piece;
  }

  /**
   * Drop a piece on the board.
   * 
   * @param piece
   *          the Piece
   * @param coord
   *          the Coordinate
   * @throws AtaxxException
   *           if square is not empty
   */
  void dropPiece(final AtaxxPiece piece, final Coordinate coord) throws AtaxxException {
    if (this.board.getPieceAtCoord(coord) == null) {
      this.board.putPieceAtCoord(piece, coord);
    } else {
      throw new AtaxxException("Square is not empty.");
    }
  }

  /**
   * Check if a move is legal.
   * 
   * TODO: Avoid premature optimization, but this will probably have to be
   * removed to make the AI more efficient.
   * 
   * @param move
   *          the move to check
   * @return true if the move is legal to make on this board.
   */
  private boolean isLegal(final AtaxxMove move) {
    boolean ret = isLegalColor(move) && isOnBoard(move) && toSquareIsEmpty(move) && pieceInFromSquareMatchesColor(move) && checkDistance(move);

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
    AtaxxPiece piece = this.board.getPieceAtCoord(move.getFrom());
    if (piece == null) {
      return false;
    }
    return piece.getColor() == move.getColor();
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
   * Check distance between from square and to square that this is a legal move
   * for the move type.
   * 
   * @param move
   *          the move to check
   * @return true if the to square can be moved to from the from square.
   */
  private static boolean checkDistance(final AtaxxMove move) {
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
    builder.append("\nUndo move list=" + this.undoMoveStack);
    builder.append("\n]");
    return builder.toString();
  }

  /**
   * Get color of piece at coordinate.
   * 
   * @param coord
   *          Coordinate of piece
   * @return color of piece.
   */
  final AtaxxColor getColorOfPieceAt(final Coordinate coord) {
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
    return this.board.getWidth();
  }

  /**
   * @return the height
   */
  public int getHeight() {
    return this.board.getHeight();
  }

  /**
   * Get the score object for the game.
   * 
   * @return the score object.
   */
  public final AtaxxScore getScore() {
    return this.board.getScore();
  }

  /**
   * Get the board as a String.
   * 
   * @return the board as a String.
   */
  final String boardToString() {
    return this.board.toString();
  }

  /**
   * Parses a String and returns an AtaxxMove object if it can be converted.
   * 
   * @param text
   *          The String of the move
   * @return An AtaxxMove object
   * @throws AtaxxException
   *           when move cannot be parsed.
   */
  final AtaxxMove parseMove(final String text) throws AtaxxException {
    if (text.length() != 4) {
      throw new AtaxxException("Not an Ataxx move.");
    }

    String moveText = text.replaceAll("\\s", "");
    Coordinate from = textPositionToCoordinate(moveText.substring(0, 2));
    Coordinate to = textPositionToCoordinate(moveText.substring(2, 4));

    int maxDiff = Coordinate.maxDiff(from, to);

    if (maxDiff > 2) {
      throw new AtaxxException("Illegal Move, too far away");
    }

    AtaxxMove.Type moveType = null;
    if (maxDiff == 2) {
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

  /**
   * Get the color whose move it is.
   * 
   * @return the color to move.
   */
  public AtaxxColor getToMove() {
    return this.colorToMove;
  }

  /**
   * Check if the game is over.
   * 
   * @return true if the game is over.
   */
  public boolean isOver() {
    AtaxxScore s = getScore();

    int boardSize = getWidth() * getHeight();

    return (s.getBlack() + s.getWhite()) == boardSize;
  }

  /**
   * An evaluation of the position from white's perspective.
   * 
   * @return evaluation value.
   */
  public int evaluate() {
    AtaxxScore s = getScore();
    return s.getWhite() - s.getBlack();
  }

}

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
  private static final int DEFAULT_ATAXX_BOARD_SIZE = 7;

  /** The board for this game. */
  private AtaxxBoard board;

  /** Which color is currently to move. White moves first. */
  private PieceColor colorToMove = PieceColor.WHITE;

  /** Stack for undo move list. */
  private Stack<AtaxxUndoMove> undoMoveStack = new Stack<>();

  /**
   * Construct the Ataxx game with default size of side.
   * 
   * @throws AtaxxException
   *           when there is some Ataxx related problem.
   */
  AtaxxGame() throws AtaxxException {
    this(DEFAULT_ATAXX_BOARD_SIZE);
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
    dropPiece(new Piece(PieceColor.WHITE), this.board.getSquareAt(0, 0));
    dropPiece(new Piece(PieceColor.WHITE), this.board.getSquareAt(this.board.getNumRanks() - 1, this.board.getNumFiles() - 1));

    dropPiece(new Piece(PieceColor.BLACK), this.board.getSquareAt(0, this.board.getNumFiles() - 1));
    dropPiece(new Piece(PieceColor.BLACK), this.board.getSquareAt(this.board.getNumRanks() - 1, 0));

    // this.board.getSquareAt(1, 1).setBlocked();
    // this.board.getSquareAt(5, 5).setBlocked();
    // this.board.getSquareAt(1, 5).setBlocked();
    // this.board.getSquareAt(5, 1).setBlocked();

    // dropPiece(new AtaxxPiece(AtaxxColor.WHITE), this.board.getSquareAt(0,
    // 2));
    // dropPiece(new AtaxxPiece(AtaxxColor.WHITE), this.board.getSquareAt(1,
    // 2));
    // dropPiece(new AtaxxPiece(AtaxxColor.WHITE), this.board.getSquareAt(6,
    // 6));
    // dropPiece(new AtaxxPiece(AtaxxColor.BLACK), this.board.getSquareAt(0,
    // 4));

  }

  /**
   * Make a move in Ataxx game. For performance reasons it is assumed move has
   * been checked for legality before calling this method.
   * 
   * @param move
   *          The move to make
   * @throws AtaxxException
   *           When something goes wrong
   */
  void makeMove(final AtaxxMove move) {
    // A null move would indicate a pass.
    if (move == null) {
      this.colorToMove = this.colorToMove.getOpposite();
      this.undoMoveStack.push(null);
      return;
    }

    Piece piece = null;
    switch (move.getType()) {
      case EXPAND:
        piece = new Piece(move.getColor());
        break;
      case JUMP:
        piece = move.getFrom().pickupPiece();
        break;
      default:
        break;
    }
    dropPiece(piece, move.getTo());

    List<Square> flipped = this.board.flipPiecesAroundSquare(move.getTo(), move.getColor());

    this.undoMoveStack.push(new AtaxxUndoMove(move, flipped));

    this.colorToMove = this.colorToMove.getOpposite();
  }

  /**
   * Undo the effects of the last move made.
   */
  void undoLastMove() {
    AtaxxUndoMove move = this.undoMoveStack.pop();
    if (move != null) {
      this.undoPieceMove(move.getMove());
      flipPiecesInSquares(move.getFlipped());
    }
    this.colorToMove = this.colorToMove.getOpposite();
  }

  /**
   * Flip the pieces in the AtaxxSquares in the list.
   * 
   * @param listOfSquares
   *          List of Coordinates of pieces to flip
   */
  static final void flipPiecesInSquares(final List<Square> listOfSquares) {
    for (Square square : listOfSquares) {
      square.getPiece().flip();
    }
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
      this.board.putPieceAtCoord(new Piece(move.getColor()), move.getFrom());
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
  private List<AtaxxMove> getAvailableMoves(final PieceColor toMove) {
    AtaxxMoveGenerator gen = new AtaxxMoveGenerator(this);
    return gen.getAvailableMoves(toMove);
  }

  /**
   * Drop a piece on the board. For performance reasons assume legality has been
   * checked before method is called.
   * 
   * @param piece
   *          the Piece
   * @param coord
   *          the Coordinate
   * @throws AtaxxException
   *           if square is not empty
   */
  private static void dropPiece(final Piece piece, final Square coord) {
    // if (coord.getPiece() == null) {
    coord.setPiece(piece);
    // } else {
    // throw new AtaxxException("Square is not empty.");
    // }
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
  private static boolean isLegal(final AtaxxMove move) {
    boolean ret = isLegalColor(move) && /* isOnBoard(move) && */ toSquareIsEmpty(move) && pieceInFromSquareMatchesColor(move) && checkDistance(move);

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
    return (move.getColor() == PieceColor.WHITE || move.getColor() == PieceColor.BLACK);
  }

  /**
   * Check from piece is the correct color.
   * 
   * @param move
   *          the move
   * @return true if the from piece exists and has the correct color for the
   *         move.
   */
  private static boolean pieceInFromSquareMatchesColor(final AtaxxMove move) {
    Piece piece = move.getFrom().getPiece();
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
  private static boolean toSquareIsEmpty(final AtaxxMove move) {
    return move.getTo().getPiece() == null;
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
    int xDiff = Math.abs(move.getTo().getFile() - move.getFrom().getFile());
    int yDiff = Math.abs(move.getTo().getRank() - move.getFrom().getRank());

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
    // builder.append("\ngetAvailableMoves()=");
    // builder.append(getAvailableMoves());
    // builder.append("\nUndo move list=" + this.undoMoveStack);
    builder.append("\n]");
    return builder.toString();
  }

  /**
   * @return the width
   */
  public int getNumFiles() {
    return this.board.getNumFiles();
  }

  /**
   * @return the height
   */
  public int getNumRanks() {
    return this.board.getNumRanks();
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

    Square f = getSquareAt(from.getX(), from.getY());
    Square t = getSquareAt(to.getX(), to.getY());

    return new AtaxxMove(moveType, this.colorToMove, f, t);
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
  public PieceColor getToMove() {
    return this.colorToMove;
  }

  /**
   * Check if the game is over.
   * 
   * @return true if the game is over.
   */
  public boolean isOver() {
    Square[][] b = this.board.getSquares();

    int numRanks = this.getNumRanks();
    int numFiles = this.getNumFiles();

    int white = 0;
    int black = 0;
    for (int f = 0; f < numFiles; f++) {
      for (int r = 0; r < numRanks; r++) {
        Piece p = b[f][r].getPiece();
        if (p != null) {
          if (p.getColor() == PieceColor.WHITE) {
            white++;
          } else {
            black++;
          }
        }
      }
    }

    // AtaxxScore s = getScore();

    int boardSize = numRanks * numFiles;

    return ((black + white == boardSize) || black == 0 || white == 0);
  }

  /**
   * An evaluation of the position from white's perspective.
   * 
   * @return evaluation value.
   */
  public int evaluate(final boolean gameOver) {
    Square[][] b = this.board.getSquares();

    int numRanks = this.getNumRanks();
    int numFiles = this.getNumFiles();

    int white = 0;
    int black = 0;
    for (int f = 0; f < numFiles; f++) {
      for (int r = 0; r < numRanks; r++) {
        Piece p = b[f][r].getPiece();
        if (p != null) {
          if (p.getColor() == PieceColor.WHITE) {
            white++;
          } else {
            black++;
          }
        }
      }
    }
    // AtaxxScore s = getScore();

    if (gameOver) {
      return (white - black) * 10000;
    }

    int material = 0;
    if (white == 0) {
      material = -1000;
    } else if (black == 0) {
      material = 1000;
    } else {
      material = white - black;
    }

    int position = 0;
    return material * 100 + position;
  }

  /**
   * Get the square.
   * 
   * @param file
   *          X
   * @param rank
   *          Y
   * @return the square.
   */
  public Square getSquareAt(final int file, final int rank) {
    if ((file >= 0 && file < this.getNumFiles()) && (rank >= 0 && rank < this.getNumRanks())) {
      return this.board.getSquareAt(file, rank);
    }
    return null;
  }
}

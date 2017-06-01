package com.spamalot.boardgame;

import java.util.List;

/**
 * Game class to be extended.
 * 
 * @author gej
 *
 */
public abstract class Game {

  /** Which color is currently to move. White moves first. */
  private PieceColor colorToMove = PieceColor.WHITE;

  /** The board for this game. */
  private Board board;

  /**
   * Constructor.
   */
  public Game() {
    super();
  }

  /**
   * Get the color whose move it is.
   * 
   * @return the color to move.
   */
  public PieceColor getColorToMove() {
    return this.colorToMove;
  }

  /**
   * Set the color who is to move.
   * 
   * @param toMove
   *          the colorToMove to set
   */
  public void setColorToMove(final PieceColor toMove) {
    this.colorToMove = toMove;
  }

  /**
   * Switch color to move to the opposite color.
   */
  protected void switchColorToMove() {
    setColorToMove(getColorToMove().getOpposite());
  }

  /**
   * Set up board.
   * 
   * @throws GameException
   *           if something goes wrong.
   */
  protected abstract void initBoard() throws GameException;

  /**
   * Make a defensive copy of this object.
   * 
   * @return A copy of this game Object.
   * @throws GameException
   *           if something goes wrong.
   */
  protected abstract Game copy() throws GameException;

  /**
   * Get the current score of the game.
   * 
   * @return the score object.
   */
  public final PieceCount getPieceCount() {
    int numBlack = 0;
    int numWhite = 0;

    Square[][] squares = this.board.getSquares();

    for (int rank = 0; rank < getNumRanks(); rank++) {
      for (int file = 0; file < getNumFiles(); file++) {
        Piece piece = squares[file][rank].getPiece();
        if (piece != null) {
          if (piece.getColor() == PieceColor.BLACK) {
            numBlack++;
          } else {
            numWhite++;
          }
        }

      }
    }
    return new PieceCount(numBlack, numWhite);
  }

  /**
   * Return the number of files on the board.
   * 
   * @return the width
   */
  public int getNumFiles() {
    return this.board.getNumFiles();
  }

  /**
   * Get the number of Ranks on the board.
   * 
   * @return the height
   */
  public int getNumRanks() {
    return this.board.getNumRanks();
  }

  /**
   * Set the board.
   * 
   * @param brd
   *          the board
   */
  protected void setBoard(final Board brd) {
    this.board = brd;
  }

  /**
   * Get the board.
   * 
   * @return the board.
   */
  protected Board getBoard() {
    return this.board;
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
    Board board2 = getBoard();

    if (board2.isOnBoard(file, rank)) {
      return board2.getSquareAt(file, rank);
    }
    return null;
  }

  /**
   * A generic check to see if the Game is over. If no pieces of one color exist
   * or if the Board is full then the Game is considered to be over. This method
   * should be overridden if there is other criteria.
   * 
   * @return true if the Game is over.
   */
  public boolean isOver() {
    PieceCount p = getPieceCount();

    int playableSquares = getBoard().getNumPlayableSquares();

    return ((p.getBlackCount() + p.getWhiteCount() == playableSquares) || p.getBlackCount() == 0 || p.getWhiteCount() == 0);
  }

  /**
   * Get the board as a String.
   * 
   * @return the board as a String.
   */
  public final String boardToString() {
    return getBoard().toString();
  }

  /**
   * Flip some Pieces to their opposite color.
   * 
   * @param piecesToFlip
   *          the Pieces to Flip
   */
  protected static void flipPieces(final List<Piece> piecesToFlip) {
    for (Piece piece : piecesToFlip) {
      piece.flip();
    }
  }

  /**
   * Turn text position into a Coordinate.
   * 
   * @param text
   *          Text to turn into coordinate
   * @return the Coordinate.
   */
  protected static Coordinate textPositionToCoordinate(final String text) {
    char file = text.charAt(0);
    char rank = text.charAt(1);
    int x = file - 'a';
    int y = rank - '0' - 1;
    Coordinate coord = new Coordinate(x, y);
    return coord;
  }

  /**
   * Flip the pieces in the AtaxxSquares in the list.
   * 
   * @param listOfSquares
   *          List of Coordinates of pieces to flip
   */
  protected static void flipPiecesInSquares(final List<Square> listOfSquares) {
    for (Square square : listOfSquares) {
      square.getPiece().flip();
    }
  }
}

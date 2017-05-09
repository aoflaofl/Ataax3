package com.spamalot.boardgame;

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
}

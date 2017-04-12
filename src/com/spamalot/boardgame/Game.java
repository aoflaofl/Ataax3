package com.spamalot.boardgame;

/**
 * Game class to be extended.
 * 
 * @author gej
 *
 */
public abstract class Game {

  /**
   * Drop a piece on the board. For performance reasons assume legality has been
   * checked before method is called.
   * 
   * @param piece
   *          the Piece
   * @param square
   *          the Coordinate
   * @throws GameException
   *           if square is not empty
   */
  protected static void dropPiece(final Piece piece, final Square square) {
    // if (coord.getPiece() == null) {
    square.setPiece(piece);
    // } else {
    // throw new AtaxxException("Square is not empty.");
    // }
  }

  /** Which color is currently to move. White moves first. */
  private PieceColor colorToMove = PieceColor.WHITE;

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
   * @param toMove
   *          the colorToMove to set
   */
  public void setColorToMove(final PieceColor toMove) {
    this.colorToMove = toMove;
  }

  protected void switchColorToMove() {
    setColorToMove(getColorToMove().getOpposite());
  }

  /**
   * Set up board.
   * 
   * @throws GameException
   */
  protected abstract void initBoard() throws GameException;
}
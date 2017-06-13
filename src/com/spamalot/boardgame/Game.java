package com.spamalot.boardgame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
   * Set up a new Game.
   * 
   * @throws GameException
   *           if something goes wrong.
   */
  protected abstract void initGame() throws GameException;

  /**
   * Make a defensive copy of this object.
   * 
   * @return A copy of this game Object.
   * @throws GameException
   *           if something goes wrong.
   */
  protected abstract Game copyGame() throws GameException;

  /**
   * Get the current score of the game.
   * 
   * @return the score object.
   */
  public final PieceCount getPieceCount() {
    return this.board.getPieceCount();
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
  public Board getBoard() {
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
   * Return true if the Square is on the board, has a piece, and that piece is
   * of the opposite color of the side to move.
   * 
   * @param sq
   *          Square to look at
   * @return true if it has an opposite color piece.
   */
  protected boolean hasOppositeColorPiece(final Square sq) {
    return sq != null && !sq.isEmpty() && sq.getPiece().getColor() != this.getColorToMove();
  }

  /**
   * Return true if the Square is on the board, has a piece, and that piece is
   * of the same color of the side to move.
   * 
   * @param sq
   *          Square to look at
   * @return true if square has a same color piece.
   */
  protected boolean hasSameColorPiece(final Square sq) {
    return sq != null && !sq.isEmpty() && sq.getPiece().getColor() == this.getColorToMove();
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
   * Render an array of Squares in a rank into an ascii String for FEN like
   * output.
   * 
   * @param rankArray
   *          Array of Squares
   * @return String in FEN form.
   */
  private static String toRankString(final Square[] rankArray) {
    StringBuilder rankString = new StringBuilder();

    int emptyCount = 0;
    for (Square sq : rankArray) {
      if (!sq.isEmpty()) {
        if (emptyCount > 0) {
          rankString.append(emptyCount);
          emptyCount = 0;
        }
        PieceColor c = sq.getPiece().getColor();
        rankString.append(c.toChar());
      } else {
        emptyCount++;
      }
    }
    if (emptyCount > 0) {
      rankString.append(emptyCount);
      emptyCount = 0;
    }
    return rankString.toString();
  }

  /**
   * Save state of game into a file in a FEN like notation.
   * 
   * @param fileName
   *          name of file to save to.
   */
  public void save(final String fileName) {

    try (FileWriter fw = new FileWriter(fileName); BufferedWriter bw = new BufferedWriter(fw)) {

      String content = this.toSaveString();

      bw.write(content);

      System.out.println("Done");

    } catch (IOException e) {

      e.printStackTrace();

    }

  }

  /**
   * Generate a String with the save state of this game that can be parsed by
   * the parseGameString method.
   * 
   * This method generates a representation of the current state of the game in
   * a FEN like form.
   * 
   * @return a String containing the save state of this game.
   */
  private String toSaveString() {
    StringBuilder ret = new StringBuilder();

    for (int rank = 0; rank < getNumRanks(); rank++) {
      ret.append(toRankString(this.getBoard().getRank(rank)));
      if (rank < getNumRanks() - 1) {
        ret.append("/");
      }
    }
    ret.append(" " + this.getColorToMove().toChar());

    System.out.println(ret);
    return ret.toString();
  }
}

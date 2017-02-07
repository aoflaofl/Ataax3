package com.spamalot.ataxx3;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a board for an Ataxx game.
 * 
 * @author gej
 *
 */
public class AtaxxBoard {
  /** Holds the pieces of the game. */
  private AtaxxPiece[][] board;

  /** The width of this board. */
  private int width;

  /** The height of this board. */
  private int height;

  /**
   * Construct a square Ataxx board of given size.
   * 
   * @param size
   *          size of a side of the square
   */
  public AtaxxBoard(final int size) {
    this(size, size);
  }

  /**
   * Construct an Ataxx board of given width and height.
   * 
   * @param w
   *          width of the board
   * @param h
   *          height of the board
   */
  public AtaxxBoard(final int w, final int h) {
    this.setWidth(w);
    this.setHeight(h);
    this.board = new AtaxxPiece[w][h];
  }

  /**
   * Get the width of this board.
   * 
   * @return the width.
   */
  public final int getWidth() {
    return this.width;
  }

  /**
   * Set the width of this board.
   * 
   * @param w
   *          the width to set
   */
  private void setWidth(final int w) {
    this.width = w;
  }

  /**
   * Get the height of this board.
   * 
   * @return the height.
   */
  public final int getHeight() {
    return this.height;
  }

  /**
   * Set the height of this board.
   * 
   * @param h
   *          the height to set
   */
  private void setHeight(final int h) {
    this.height = h;
  }

  /**
   * Make a move in an Ataxx game.
   * 
   * @param move
   *          the move to make
   * @throws AtaxxException
   *           Exception.
   */
  final void makeMove(final AtaxxMove move) throws AtaxxException {
    // TODO: Throw an illegal move exception or handle errors in some way.
    if (!isLegal(move)) {
      throw new AtaxxException(move, "Move is illegal.");
    }
    switch (move.getType()) {
      case EXPAND:
        this.board[move.getTo().getX()][move.getTo().getY()] = new AtaxxPiece(move.getColor());
        break;
      case JUMP:
        this.board[move.getTo().getX()][move.getTo().getY()] = this.board[move.getFrom().getX()][move.getFrom().getY()];
        this.board[move.getFrom().getX()][move.getFrom().getY()] = null;
        break;
      default:
        break;
    }
    flipPiecesAroundToSquare(move);

  }

  /**
   * @param move
   * @return a List of Coordinates of squares that had flipped pieces.
   */
  private List<Coordinate> flipPiecesAroundToSquare(final AtaxxMove move) {
    AtaxxColor c = move.getColor().getOpposite();
    Coordinate s = move.getTo();
    List<Coordinate> ret = new ArrayList<>();
    int minX = Math.max(s.getX() - 1, 0);
    int maxX = Math.min(s.getX() + 1, this.width);
    int minY = Math.max(s.getY() - 1, 0);
    int maxY = Math.min(s.getY() + 1, this.height);

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {

        if (this.board[x][y] != null && this.board[x][y].getColor() == c) {
          System.out.println(x + ", " + y);
          this.board[x][y].flip();
          Coordinate co = new Coordinate(x, y);
          ret.add(co);
        }
      }
    }
    return ret;
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
   * Check from piece is the correct color.
   * 
   * @param move
   *          the move
   * @return true if the from piece has the correct color for the move.
   */
  private boolean pieceInFromSquareMatchesColor(final AtaxxMove move) {
    return this.board[move.getFrom().getX()][move.getFrom().getY()].getColor() == move.getColor();
  }

  /**
   * @param move
   *          the move to check.
   * @return true if the square is empty.
   */
  private boolean toSquareIsEmpty(final AtaxxMove move) {
    return squareIsEmpty(move.getTo().getX(), move.getTo().getY());
  }

  /**
   * @param x
   *          X coordinate
   * @param y
   *          Y coordinate
   * @return true if square is empty.
   */
  private boolean squareIsEmpty(final int x, final int y) {
    return this.board[x][y] == null;
  }

  /**
   * @param move
   *          the move to check
   * @return true if the squares involved are on the board.
   */
  private boolean isOnBoard(final AtaxxMove move) {
    return (isOnBoard(move.getFrom().getX(), move.getFrom().getY()) && isOnBoard(move.getTo().getX(), move.getTo().getY()));
  }

  /**
   * Check if a square is on the board.
   * 
   * @param x
   *          X coordinate to check
   * @param y
   *          Y coordinate to check
   * @return true if the square is on the board
   */
  private boolean isOnBoard(final int x, final int y) {
    return (x >= 0 && x < this.width && y >= 0 && y < this.height);
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
   * Place a new piece of the given color on the board. This doesn't flip any
   * adjacent pieces, just places the piece on the board.
   * 
   * @param color
   *          Color of piece
   * @param x
   *          X coordinate
   * @param y
   *          Y coordinate
   * @throws AtaxxException
   *           An exception
   */
  public final void setPiece(final AtaxxColor color, final int x, final int y) throws AtaxxException {
    if (!squareIsEmpty(x, y)) {
      throw new AtaxxException("Can't do that.");
    }
    this.board[x][y] = new AtaxxPiece(color);
  }

  @Override
  public final String toString() {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        AtaxxPiece p = this.board[i][j];
        if (p == null) {
          s.append(".");
        } else {
          s.append(p);
        }
      }
      s.append("\n");
    }

    return s.toString();
  }
}
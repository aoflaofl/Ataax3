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

  /** The height of this board. */
  private int height;

  /** The width of this board. */
  private int width;

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
    this.setBoard(new AtaxxPiece[w][h]);
  }

  /**
   * Flip the pieces at the coordinates in the list.
   * 
   * @param flipped
   *          List of Coordinates of pieces to flip
   */
  final void flipPieces(final List<Coordinate> flipped) {
    for (Coordinate c : flipped) {
      this.getBoard()[c.getX()][c.getY()].flip();
    }
  }

  /**
   * Flip the pieces around the square that don't match passed in color.
   * 
   * @param c
   *          Coordinate of square around which to flip
   * @param col
   *          Color to flip to
   * @return a List of Coordinates of squares that had flipped pieces.
   */
  final List<Coordinate> flipPiecesAroundSquare(final Coordinate c, final AtaxxColor col) {
    AtaxxColor oppositeColor = col.getOpposite();

    int minX = Math.max(c.getX() - 1, 0);
    int maxX = Math.min(c.getX() + 1, this.width);
    int minY = Math.max(c.getY() - 1, 0);
    int maxY = Math.min(c.getY() + 1, this.height);

    List<Coordinate> ret = new ArrayList<>();

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {

        if (this.getBoard()[x][y] != null && this.getBoard()[x][y].getColor() == oppositeColor) {
          System.out.println(x + ", " + y);
          this.getBoard()[x][y].flip();
          Coordinate co = new Coordinate(x, y);
          ret.add(co);
        }
      }
    }
    return ret;
  }

  /**
   * @return the board
   */
  public AtaxxPiece[][] getBoard() {
    return this.board;
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
   * Get a Piece.
   * 
   * @param c
   *          Coordinate of piece to return.
   * @return Ataxx Piece.
   */
  final AtaxxPiece getPieceAtCoord(final Coordinate c) {
    return this.getBoard()[c.getX()][c.getY()];
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
   * Check if a square is on the board.
   * 
   * @param sq
   *          The square
   * @return true if the square is on the board
   */
  final boolean isOnBoard(final Coordinate sq) {
    int x = sq.getX();
    int y = sq.getY();
    return (x >= 0 && x < this.width && y >= 0 && y < this.height);
  }

  /**
   * Put a piece on the board. Ignore if square already has a piece.
   * 
   * @param p
   *          the Piece (can be null)
   * @param c
   *          the Coordinate
   */
  final void putPieceAtCoord(final AtaxxPiece p, final Coordinate c) {
    this.getBoard()[c.getX()][c.getY()] = p;
  }

  /**
   * @param b
   *          the board to set
   */
  public void setBoard(final AtaxxPiece[][] b) {
    this.board = b;
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
   * Set the width of this board.
   * 
   * @param w
   *          the width to set
   */
  private void setWidth(final int w) {
    this.width = w;
  }

  /**
   * Check if square is empty.
   * 
   * @param sq
   *          the Square
   * @return true if square is empty.
   */
  final boolean squareIsEmpty(final Coordinate sq) {
    return this.getBoard()[sq.getX()][sq.getY()] == null;
  }

  @Override
  public final String toString() {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        AtaxxPiece p = this.getBoard()[i][j];
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
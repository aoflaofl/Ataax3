package com.spamalot.ataxx3;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a board for an Ataxx game.
 * 
 * @author gej
 *
 */
class AtaxxBoard {

  /** Hold Array of AtaxxSquares to represent the board. */
  private AtaxxSquare[][] board;

  /** The height of this board. */
  private int height;

  /** The width of this board. */
  private int width;

  /**
   * Construct a square board of given size.
   * 
   * @param size
   *          size of a side of the square
   */
  AtaxxBoard(final int size) {
    this(size, size);
  }

  /**
   * Construct a board of given width and height.
   * 
   * @param w
   *          width of the board
   * @param h
   *          height of the board
   */
  AtaxxBoard(final int w, final int h) {
    this.setWidth(w);
    this.setHeight(h);

    initBoard(w, h);
  }

  /**
   * Initialize the board.
   * 
   * @param w
   *          width of board
   * @param h
   *          height of board
   */
  private void initBoard(final int w, final int h) {
    this.board = new AtaxxSquare[w][h];
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        this.board[i][j] = new AtaxxSquare(AtaxxSquare.Type.PLAYABLE);
      }
    }
  }

  /**
   * Set the sqboard.
   * 
   * @param ataxxSquares
   *          Array of AtaxxSquares
   */
  private void setSquareBoard(final AtaxxSquare[][] ataxxSquares) {
    this.board = ataxxSquares;
  }

  /**
   * Flip the pieces at the coordinates in the list.
   * 
   * @param list
   *          List of Coordinates of pieces to flip
   */
  final void flipPiecesAtCoordinates(final List<AtaxxSquare> list) {
    for (AtaxxSquare square : list) {
      square.getPiece().flip();
    }
  }

  /**
   * Flip the pieces around the square that don't match passed in color.
   * 
   * @param coordinate
   *          Coordinate of square around which to flip
   * @param color
   *          Color to flip to
   * @return a List of Coordinates of squares that had flipped pieces.
   */
  final List<AtaxxSquare> flipPiecesAroundSquare(final Coordinate coordinate, final AtaxxColor color) {
    AtaxxColor oppositeColor = color.getOpposite();

    int minX = Math.max(coordinate.getX() - 1, 0);
    int maxX = Math.min(coordinate.getX() + 1, this.width - 1);
    int minY = Math.max(coordinate.getY() - 1, 0);
    int maxY = Math.min(coordinate.getY() + 1, this.height - 1);

    List<AtaxxSquare> ret = new ArrayList<>();

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {

        AtaxxSquare square = getSquareAt(x, y);
        AtaxxPiece piece = square.getPiece();

        if (piece != null && piece.getColor() == oppositeColor) {
          piece.flip();
          ret.add(square);
        }
      }
    }
    return ret;
  }

  /**
   * @param x
   *          the X ordinate
   * @param y
   *          the Y ordinate
   * @return the AtaxxSquare object.
   */
  private AtaxxSquare getSquareAt(final int x, final int y) {
    return this.board[x][y];
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
   * Get a Piece at a Coordinate.
   * 
   * @param c
   *          Coordinate of piece to return.
   * @return Ataxx Piece.
   */
  final AtaxxPiece getPieceAtCoord(final Coordinate c) {
    return getPieceAt(c.getX(), c.getY());
  }

  /**
   * Get a Piece at a location.
   * 
   * @param x
   *          The X ordinate
   * @param y
   *          The Y ordinate
   * 
   * @return Ataxx Piece.
   */
  private AtaxxPiece getPieceAt(final int x, final int y) {
    return this.getSquareBoard()[x][y].getPiece();
  }

  /**
   * Get the Board.
   * 
   * @return the board.
   */
  private AtaxxSquare[][] getSquareBoard() {
    return this.board;
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
    this.getSquareBoard()[c.getX()][c.getY()].setPiece(p);
  }

  /**
   * @param b
   *          the board to set
   */
  // private void setBoard(final AtaxxPiece[][] b) {
  // this.board = b;
  // }

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
    return getPieceAtCoord(sq) == null;
  }

  /**
   * Get the current score of the game.
   * 
   * @return the score object.
   */
  public final AtaxxScore getScore() {
    int black = 0;
    int white = 0;

    for (int i = 0; i < getHeight(); i++) {
      for (int j = 0; j < getWidth(); j++) {
        AtaxxPiece p = getSquareBoard()[i][j].getPiece();
        if (p != null) {
          if (p.getColor() == AtaxxColor.BLACK) {
            black++;
          } else {
            white++;
          }
        }
      }
    }
    return new AtaxxScore(black, white);
  }

  @Override
  public final String toString() {
    StringBuilder s = new StringBuilder();
    s.append("   ");
    for (byte j = 0; j < this.width; j++) {
      s.append((char) ('a' + j));
    }
    s.append("\n\n");

    for (int i = 0; i < this.height; i++) {
      s.append(i + 1);
      s.append("  ");
      for (int j = 0; j < this.width; j++) {
        AtaxxPiece p = getPieceAt(j, i);
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

  public AtaxxSquare getSquareAtCoord(int x, int y) {
    return this.board[x][y];
  }

  /**
   * Get the Square.
   * 
   * @param coordinate
   *          the coordinate
   * @return the square.
   */
  // public AtaxxSquare getSquareAtCoord(final Coordinate coordinate) {
  // return this.board[coordinate.getX()][coordinate.getY()];
  // }

}
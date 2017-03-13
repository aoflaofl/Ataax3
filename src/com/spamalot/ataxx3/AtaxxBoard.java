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
  private AtaxxSquare[][] squares;

  /** The number of ranks on this board. */
  private int numRanks;

  /** The number of files on this board. */
  private int numFiles;

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
   * @param files
   *          width of the board
   * @param ranks
   *          height of the board
   */
  AtaxxBoard(final int files, final int ranks) {
    this.setWidth(files);
    this.setHeight(ranks);

    initBoard(files, ranks);
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
    this.squares = new AtaxxSquare[w][h];
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        this.squares[i][j] = new AtaxxSquare(AtaxxSquare.Type.PLAYABLE, i, j);
      }
    }
  }

  /**
   * Flip the pieces at the coordinates in the list.
   * 
   * @param listOfSquares
   *          List of Coordinates of pieces to flip
   */
  static final void flipPiecesInSquares(final List<AtaxxSquare> listOfSquares) {
    for (AtaxxSquare square : listOfSquares) {
      square.getPiece().flip();
    }
  }

  /**
   * Flip the pieces around the square that don't match passed in color and make
   * them match passed in color.
   * 
   * @param ataxxSquare
   *          Coordinate of square around which to flip
   * @param color
   *          Color to flip to
   * @return a List of AtaxxSquares that had flipped pieces.
   */
  final List<AtaxxSquare> flipPiecesAroundSquare(final AtaxxSquare ataxxSquare, final AtaxxColor color) {
    AtaxxColor oppositeColor = color.getOpposite();

    int minFile = Math.max(ataxxSquare.getFile() - 1, 0);
    int maxFile = Math.min(ataxxSquare.getFile() + 1, this.numFiles - 1);
    int minRank = Math.max(ataxxSquare.getRank() - 1, 0);
    int maxRank = Math.min(ataxxSquare.getRank() + 1, this.numRanks - 1);

    List<AtaxxSquare> ret = new ArrayList<>();

    for (int file = minFile; file <= maxFile; file++) {
      for (int rank = minRank; rank <= maxRank; rank++) {

        AtaxxSquare square = getSquareAt(file, rank);
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
    return this.squares[x][y];
  }

  /**
   * Get the height of this board.
   * 
   * @return the height.
   */
  public final int getHeight() {
    return this.numRanks;
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
    return this.squares;
  }

  /**
   * Get the width of this board.
   * 
   * @return the width.
   */
  public final int getWidth() {
    return this.numFiles;
  }

  /**
   * Check if a square is on the board.
   * 
   * @param ataxxSquare
   *          The square
   * @return true if the square is on the board
   */
  final boolean isOnBoard(final AtaxxSquare ataxxSquare) {
    int file = ataxxSquare.getFile();
    int rank = ataxxSquare.getRank();
    return (file >= 0 && file < this.numFiles && rank >= 0 && rank < this.numRanks);
  }

  /**
   * Put a piece on the board. Ignore if square already has a piece.
   * 
   * @param p
   *          the Piece (can be null)
   * @param ataxxSquare
   *          the Coordinate
   */
  final void putPieceAtCoord(final AtaxxPiece p, final AtaxxSquare ataxxSquare) {
    this.getSquareBoard()[ataxxSquare.getFile()][ataxxSquare.getRank()].setPiece(p);
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
    this.numRanks = h;
  }

  /**
   * Set the width of this board.
   * 
   * @param w
   *          the width to set
   */
  private void setWidth(final int w) {
    this.numFiles = w;
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
    for (byte j = 0; j < this.numFiles; j++) {
      s.append((char) ('a' + j));
    }
    s.append("\n\n");

    for (int i = 0; i < this.numRanks; i++) {
      s.append(i + 1);
      s.append("  ");
      for (int j = 0; j < this.numFiles; j++) {
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

  /**
   * Get the Square.
   * 
   * @param x
   *          the x ordinate
   * @param y
   *          the y ordinate
   * @return the square.
   */
  public AtaxxSquare getSquareAtCoord(final int x, final int y) {
    return this.squares[x][y];
  }
  // public AtaxxSquare getSquareAtCoord(final Coordinate coordinate) {
  // return this.board[coordinate.getX()][coordinate.getY()];
  // }

}
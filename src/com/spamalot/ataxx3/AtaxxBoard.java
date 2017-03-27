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
  private Square[][] squares;

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
   * Construct a board of given number of files and ranks.
   * 
   * @param files
   *          width of the board
   * @param ranks
   *          height of the board
   */
  AtaxxBoard(final int files, final int ranks) {
    this.setNumFiles(files);
    this.setNumRanks(ranks);

    this.setSquares(new Square[files][ranks]);

    initBoard();
  }

  /**
   * Initialize the board.
   */
  private void initBoard() {
    for (int file = 0; file < getNumFiles(); file++) {
      for (int rank = 0; rank < getNumRanks(); rank++) {
        this.squares[file][rank] = new Square(Square.Type.PLAYABLE, file, rank);
      }
    }
  }

  /**
   * Flip the pieces around the square that don't match the passed in color and
   * make them match passed in color.
   * 
   * @param ataxxSquare
   *          AtaxxSquare around which to flip
   * @param color
   *          Color to flip to
   * @return a List of AtaxxSquares that had flipped pieces.
   */
  final List<Square> flipPiecesAroundSquare(final Square ataxxSquare, final PieceColor color) {
    PieceColor oppositeColor = color.getOpposite();

    int minFile = Math.max(ataxxSquare.getFile() - 1, 0);
    int maxFile = Math.min(ataxxSquare.getFile() + 1, this.numFiles - 1);
    int minRank = Math.max(ataxxSquare.getRank() - 1, 0);
    int maxRank = Math.min(ataxxSquare.getRank() + 1, this.numRanks - 1);

    List<Square> ret = new ArrayList<>();

    for (int file = minFile; file <= maxFile; file++) {
      for (int rank = minRank; rank <= maxRank; rank++) {

        Square square = getSquareAt(file, rank);
        Piece piece = square.getPiece();

        if (piece != null && piece.getColor() == oppositeColor) {
          piece.flip();
          ret.add(square);
        }
      }
    }
    return ret;
  }

  /**
   * @param file
   *          the file
   * @param rank
   *          the rank
   * @return the AtaxxSquare.
   */
  public Square getSquareAt(final int file, final int rank) {
    return this.getSquares()[file][rank];
  }

  /**
   * Get the number of ranks on this board.
   * 
   * @return the number of ranks.
   */
  public final int getNumRanks() {
    return this.numRanks;
  }

  /**
   * Get the Board.
   * 
   * @return the board.
   */
  private Square[][] getSquareBoard() {
    return this.getSquares();
  }

  /**
   * Get the width of this board.
   * 
   * @return the width.
   */
  public final int getNumFiles() {
    return this.numFiles;
  }

  /**
   * Put a piece on the board. Ignore if square already has a piece.
   * 
   * @param p
   *          the Piece (can be null)
   * @param ataxxSquare
   *          the Coordinate
   */
  final void putPieceAtCoord(final Piece p, final Square ataxxSquare) {
    this.getSquareBoard()[ataxxSquare.getFile()][ataxxSquare.getRank()].setPiece(p);
  }

  /**
   * Set the number of ranks of this board.
   * 
   * @param ranks
   *          the height to set
   */
  private void setNumRanks(final int ranks) {
    this.numRanks = ranks;
  }

  /**
   * Set the number of files on this board.
   * 
   * @param files
   *          the width to set
   */
  private void setNumFiles(final int files) {
    this.numFiles = files;
  }

  /**
   * Get the current score of the game.
   * 
   * @return the score object.
   */
  public final AtaxxScore getScore() {
    int black = 0;
    int white = 0;

    for (int i = 0; i < getNumRanks(); i++) {
      for (int j = 0; j < getNumFiles(); j++) {
        Square ataxxSquare = this.getSquares()[j][i];
        Piece p = ataxxSquare.getPiece();
        if (p != null) {
          if (p.getColor() == PieceColor.BLACK) {
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

        Square squareAt = getSquareAt(j, i);

        if (squareAt.isBlocked()) {
          s.append('X');
        } else {
          Piece p = squareAt.getPiece();
          if (p == null) {
            s.append(".");
          } else {
            s.append(p);
          }
        }
      }
      s.append("\n");
    }

    return s.toString();
  }

  /**
   * @return the squares
   */
  public Square[][] getSquares() {
    return this.squares;
  }

  /**
   * @param sqs
   *          the squares to set
   */
  public void setSquares(final Square[][] sqs) {
    this.squares = sqs;
  }
}
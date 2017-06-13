package com.spamalot.boardgame;

import java.util.ArrayList;
import java.util.List;

/**
 * Board for board games.
 * 
 * @author gej
 *
 */
public abstract class Board {

  /** Hold Array of Squares for the board. */
  private Square[][] squares;

  /** The number of ranks on this board. */
  private int numRanks;

  /** The number of files on this board. */
  private int numFiles;

  /** Number of Blocked Squares. */
  private int numBlockedSquares;

  /**
   * Construct a square board of given size.
   * 
   * @param size
   *          size of a side of the square
   */
  protected Board(final int size) {
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
  Board(final int files, final int ranks) {
    this.setNumFiles(files);
    this.setNumRanks(ranks);

    this.setSquares(new Square[ranks][files]);

    initBoard();
  }

  /**
   * Method to implement in subclass to initialize properties of the Squares in
   * this Board for the particular game being implemented. This might set up
   * pointers to squares around each square to make computing moves easier, for
   * instance.
   */
  protected abstract void initSquares();

  /**
   * Initialize the board by creating the 2D array of Square Objects.
   */
  private void initBoard() {
    for (int file = 0; file < getNumFiles(); file++) {
      for (int rank = 0; rank < getNumRanks(); rank++) {
        this.squares[rank][file] = new Square(file, rank);
      }
    }
  }

  /**
   * Get a Square from the Board.
   * 
   * @param file
   *          the file
   * @param rank
   *          the rank
   * @return the Square.
   */
  public final Square getSquareAt(final int file, final int rank) {
    return this.squares[rank][file];
  }

  /**
   * Get a Square from the Board using Coordinate.
   * 
   * @param c
   *          the Coordinate
   * @return the Square.
   */
  public final Square getSquareAt(final Coordinate c) {
    return this.squares[c.getY()][c.getX()];
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
   * Get the width of this board.
   * 
   * @return the width.
   */
  public final int getNumFiles() {
    return this.numFiles;
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
   * Get the Square array of this Board.
   * 
   * @return the squares
   */
  public Square[][] getSquares() {
    return this.squares;
  }

  /**
   * Set the Square array of this Board.
   * 
   * @param sqs
   *          the squares to set
   */
  public void setSquares(final Square[][] sqs) {
    this.squares = sqs;
  }

  /**
   * Get a list of Squares that are one away neighbors of the one passed in.
   * 
   * @param sq
   *          The Square
   * @return a List of Squares that are direct neighbors of the one passed in.
   */
  protected final List<Square> getOneAwaySquares(final Square sq) {
    List<Square> ret = new ArrayList<>();
    ret.addAll(getOneAwayOrthogonal(sq));
    ret.addAll(getOneAwayDiagonal(sq));
    return ret;
  }

  /**
   * A list of Squares that are diagonal neighbors of the one passed in.
   * 
   * @param sq
   *          The Square
   * @return a List of Squares that are one away diagonally from this one and
   *         can be occupied.
   */
  private List<Square> getOneAwayDiagonal(final Square sq) {
    List<Square> ret = new ArrayList<>();

    int file = sq.getFile();
    int rank = sq.getRank();

    checkAndAddSquare(ret, file - 1, rank - 1);
    checkAndAddSquare(ret, file + 1, rank + 1);
    checkAndAddSquare(ret, file - 1, rank + 1);
    checkAndAddSquare(ret, file + 1, rank - 1);

    return ret;
  }

  /**
   * @param file
   *          File of Square
   * @param rank
   *          Rank of Square
   * @return true if the Square is on the board and is not blocked.
   */
  protected boolean isPlayableSquare(final int file, final int rank) {
    return isOnBoard(file, rank) && (!this.squares[rank][file].isBlocked());
  }

  /**
   * Check if file and rank is on the Board.
   * 
   * @param file
   *          the file
   * @param rank
   *          the rank
   * @return true if the square at file and rank is on the Board.
   */
  final boolean isOnBoard(final int file, final int rank) {
    return (file >= 0 && file < this.numFiles && rank >= 0 && rank < this.numRanks);
  }

  /**
   * A list of Squares that are orthogonal neighbors of the one passed in.
   * 
   * @param sq
   *          The Square
   * @return a List of Squares that are one away orthogonally from this one and
   *         can be occupied.
   */
  private List<Square> getOneAwayOrthogonal(final Square sq) {
    List<Square> ret = new ArrayList<>();

    int file = sq.getFile();
    int rank = sq.getRank();

    checkAndAddSquare(ret, file - 1, rank);
    checkAndAddSquare(ret, file + 1, rank);
    checkAndAddSquare(ret, file, rank - 1);
    checkAndAddSquare(ret, file, rank + 1);

    return ret;
  }

  /**
   * Check that the Square at file and rank can be played to and add it to the
   * Squares List if so.
   * 
   * @param squareList
   *          the List of Squares
   * @param file
   *          the File
   * @param rank
   *          the Rank
   */
  private void checkAndAddSquare(final List<Square> squareList, final int file, final int rank) {
    if (isPlayableSquare(file, rank)) {
      squareList.add(this.squares[rank][file]);
    }
  }

  /**
   * A list of Squares that are two away neighbors of the one passed in,
   * including knight jumps.
   * 
   * @param sq
   *          The Square
   * @return a List of Squares that are two away neighbors of the one passed in.
   */
  protected final List<Square> getTwoAwaySquares(final Square sq) {
    List<Square> ret = new ArrayList<>();

    ret.addAll(getTwoAwayOrthogonal(sq));
    ret.addAll(getTwoAwayDiagonal(sq));
    ret.addAll(getTwoAwayKnightJump(sq));

    return ret;
  }

  /**
   * A list of Squares that are knight jump neighbors of the one passed in.
   * 
   * @param sq
   *          The Square
   * @return a List of Squares that are knight jumps from this one and can be
   *         occupied.
   */
  private List<Square> getTwoAwayKnightJump(final Square sq) {
    List<Square> ret = new ArrayList<>();

    int file = sq.getFile();
    int rank = sq.getRank();

    checkAndAddSquare(ret, file - 1, rank - 2);
    checkAndAddSquare(ret, file - 1, rank + 2);
    checkAndAddSquare(ret, file + 1, rank - 2);
    checkAndAddSquare(ret, file + 1, rank + 2);

    checkAndAddSquare(ret, file - 2, rank - 1);
    checkAndAddSquare(ret, file - 2, rank + 1);
    checkAndAddSquare(ret, file + 2, rank - 1);
    checkAndAddSquare(ret, file + 2, rank + 1);

    return ret;
  }

  /**
   * A list of Squares that are two away diagonal neighbors of the one passed
   * in.
   * 
   * @param sq
   *          The Square
   * @return a List of Squares that are two away diagonally from this one and
   *         can be occupied.
   */
  private List<Square> getTwoAwayDiagonal(final Square sq) {
    List<Square> ret = new ArrayList<>();

    int file = sq.getFile();
    int rank = sq.getRank();

    checkAndAddSquare(ret, file - 2, rank - 2);
    checkAndAddSquare(ret, file + 2, rank + 2);
    checkAndAddSquare(ret, file - 2, rank + 2);
    checkAndAddSquare(ret, file + 2, rank - 2);

    return ret;
  }

  /**
   * A list of Squares that are two away orthogonal neighbors of the one passed
   * in.
   * 
   * @param sq
   *          The Square
   * @return a List of Squares that are two away orthogonally from this one and
   *         can be occupied.
   */
  private List<Square> getTwoAwayOrthogonal(final Square sq) {
    List<Square> ret = new ArrayList<>();

    int file = sq.getFile();
    int rank = sq.getRank();

    checkAndAddSquare(ret, file - 2, rank);
    checkAndAddSquare(ret, file + 2, rank);
    checkAndAddSquare(ret, file, rank - 2);
    checkAndAddSquare(ret, file, rank + 2);

    return ret;
  }

  @Override
  public final String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("   ");
    for (byte j = 0; j < this.getNumFiles(); j++) {
      sb.append((char) ('a' + j));
    }
    sb.append("\n\n");

    for (int i = 0; i < this.getNumRanks(); i++) {
      sb.append(i + 1);
      sb.append("  ");
      for (int j = 0; j < this.getNumFiles(); j++) {

        Square squareAt = getSquareAt(j, i);

        if (squareAt.isBlocked()) {
          sb.append('X');
        } else {
          Piece piece = squareAt.getPiece();
          if (piece == null) {
            sb.append(".");
          } else {
            sb.append(piece);
          }
        }
      }
      sb.append("\n");
    }

    return sb.toString();
  }

  /**
   * Set a square as blocked.
   * 
   * @param file
   *          File of Square
   * @param rank
   *          Rank of Square
   */
  public void setBlocked(final int file, final int rank) {
    Square sq = getSquareAt(file, rank);
    if (!sq.isBlocked()) {
      sq.setBlocked();
      this.numBlockedSquares++;
    }
  }

  /**
   * Return the number of Squares not allowed to be moved to.
   * 
   * @return the number of blocked squares.
   */
  public int getNumBlockedSquares() {
    return this.numBlockedSquares;
  }

  /**
   * Get the Number of playable Squares.
   * 
   * @return the Number of Playable Squares.
   */
  public int getNumPlayableSquares() {
    return (getNumRanks() * getNumFiles()) - getNumBlockedSquares();
  }

  /**
   * Initialize the Direction Map in the Square.
   * 
   * @param rank
   *          the Rank
   * @param file
   *          the File
   */
  protected void initSquareDirectionMap(final int rank, final int file) {
    Square sq = this.squares[rank][file];
    for (Direction direction : Direction.values()) {

      if (isPlayableSquare(file + direction.getRun(), rank + direction.getRise())) {
        sq.setSquareInDirection(direction, this.squares[rank + direction.getRise()][file + direction.getRun()]);
      }
    }
  }

  /**
   * Make a copy in this Board Object of the Squares in another Board Object.
   * 
   * @param b
   *          the Board to Copy.
   */
  public final void makeCopyOfPiecesInSquaresFromBoard(final Board b) {
    for (int file = 0; file < getNumFiles(); file++) {
      for (int rank = 0; rank < getNumRanks(); rank++) {
        Piece p = b.getSquares()[rank][file].getPiece();
        if (p != null) {
          this.squares[rank][file].setPiece(p.copy());
        }
      }
    }
  }

  /**
   * Doesn't work. Need to reverse the indices.
   * 
   * @param rank
   *          Rank to return
   * @return rank Squares.
   */
  public Square[] getRank(final int rank) {
    return this.squares[rank];
  }

  /**
   * Get the PieceCount object for this board.
   * 
   * @return PieceCount object with count values.
   */
  final PieceCount getPieceCount() {
    int numBlack = 0;
    int numWhite = 0;

    for (int rank = 0; rank < getNumRanks(); rank++) {
      for (int file = 0; file < getNumFiles(); file++) {
        Piece piece = this.squares[rank][file].getPiece();
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
}

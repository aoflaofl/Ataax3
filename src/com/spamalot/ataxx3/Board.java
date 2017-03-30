package com.spamalot.ataxx3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Board for board games.
 * 
 * @author gej
 *
 */
public class Board {

  /** Hold Array of Squares for the board. */
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
  Board(final int size) {
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

    this.setSquares(new Square[files][ranks]);

    initBoard();
  }

  /**
   * Initialize the board.
   */
  private void initBoard() {
    for (int file = 0; file < getNumFiles(); file++) {
      for (int rank = 0; rank < getNumRanks(); rank++) {
        this.squares[file][rank] = new Square(file, rank);
      }
    }
  }

  /**
   * @param file
   *          the file
   * @param rank
   *          the rank
   * @return the AtaxxSquare.
   */
  final Square getSquareAt(final int file, final int rank) {
    return this.squares[file][rank];
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

  final List<Square> getOneAwaySquares(final Square sq) {
    List<Square> ret = new ArrayList<>(8);
    ret.addAll(getOneAwayOrthogonal(sq));
    ret.addAll(getOneAwayDiagonal(sq));
    return ret;
  }

  private Collection<? extends Square> getOneAwayDiagonal(final Square sq) {
    List<Square> ret = new ArrayList<>(4);

    int file = sq.getFile();
    int rank = sq.getRank();

    checkAndAddSquare(ret, file - 1, rank - 1);
    checkAndAddSquare(ret, file + 1, rank + 1);
    checkAndAddSquare(ret, file - 1, rank + 1);
    checkAndAddSquare(ret, file + 1, rank - 1);

    return ret;

  }

  final boolean isPlayableSquare(final int file, final int rank) {
    if (file < 0 || file >= this.numFiles || rank < 0 || rank >= this.numRanks) {
      return false;
    }

    return (!this.squares[file][rank].isBlocked());
  }

  private Collection<? extends Square> getOneAwayOrthogonal(final Square sq) {
    List<Square> ret = new ArrayList<>(4);

    int file = sq.getFile();
    int rank = sq.getRank();

    checkAndAddSquare(ret, file - 1, rank);
    checkAndAddSquare(ret, file + 1, rank);
    checkAndAddSquare(ret, file, rank - 1);
    checkAndAddSquare(ret, file, rank + 1);

    return ret;
  }

  /**
   * @param ret
   * @param file
   * @param rank
   */
  private void checkAndAddSquare(final List<Square> ret, final int file, final int rank) {
    if (isPlayableSquare(file, rank)) {
      ret.add(this.squares[file][rank]);
    }
  }

  final List<Square> getTwoAwaySquares(final Square sq) {
    List<Square> ret = new ArrayList<>(16);

    ret.addAll(getTwoAwayOrthogonal(sq));
    ret.addAll(getTwoAwayDiagonal(sq));
    ret.addAll(getTwoAwayKnightJump(sq));

    return ret;
  }

  private Collection<? extends Square> getTwoAwayKnightJump(Square sq) {
    List<Square> ret = new ArrayList<>(8);

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

  private Collection<? extends Square> getTwoAwayDiagonal(Square sq) {
    List<Square> ret = new ArrayList<>(4);

    int file = sq.getFile();
    int rank = sq.getRank();

    checkAndAddSquare(ret, file - 2, rank - 2);
    checkAndAddSquare(ret, file + 2, rank + 2);
    checkAndAddSquare(ret, file - 2, rank + 2);
    checkAndAddSquare(ret, file + 2, rank - 2);

    return ret;
  }

  private Collection<? extends Square> getTwoAwayOrthogonal(Square sq) {
    List<Square> ret = new ArrayList<>(4);

    int file = sq.getFile();
    int rank = sq.getRank();

    checkAndAddSquare(ret, file - 2, rank);
    checkAndAddSquare(ret, file + 2, rank);
    checkAndAddSquare(ret, file, rank - 2);
    checkAndAddSquare(ret, file, rank + 2);

    return ret;
  }

}
package com.spamalot.ataxx3;

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
   * Put a piece on the board. Ignore if square already has a piece.
   * 
   * @param p
   *          the Piece (can be null)
   * @param ataxxSquare
   *          the Coordinate
   */
  protected final void putPieceAtCoord(final Piece p, final Square ataxxSquare) {
    this.squares[ataxxSquare.getFile()][ataxxSquare.getRank()].setPiece(p);
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

}
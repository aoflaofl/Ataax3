package com.spamalot.ataxx3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
   * Flip the pieces around the to square.
   * 
   * @param move
   *          the move that results in the flipping.
   * @return a List of Coordinates of squares that had flipped pieces.
   */
  private List<Coordinate> flipPiecesAroundToSquare(final AtaxxMove move) {
    AtaxxColor oppositeColor = move.getColor().getOpposite();
    Coordinate s = move.getTo();

    int minX = Math.max(s.getX() - 1, 0);
    int maxX = Math.min(s.getX() + 1, this.width);
    int minY = Math.max(s.getY() - 1, 0);
    int maxY = Math.min(s.getY() + 1, this.height);

    List<Coordinate> ret = new ArrayList<>();

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {

        if (this.board[x][y] != null && this.board[x][y].getColor() == oppositeColor) {
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
    return squareIsEmpty(move.getTo());
  }

  /**
   * Check if square is empty.
   * 
   * @param sq
   *          the Square
   * @return true if square is empty.
   */
  private boolean squareIsEmpty(final Coordinate sq) {
    return this.board[sq.getX()][sq.getY()] == null;
  }

  /**
   * @param move
   *          the move to check
   * @return true if the squares involved are on the board.
   */
  private boolean isOnBoard(final AtaxxMove move) {
    return (isOnBoard(move.getFrom()) && isOnBoard(move.getTo()));
  }

  /**
   * Check if a square is on the board.
   * 
   * @param sq
   *          The square
   * @return true if the square is on the board
   */
  private boolean isOnBoard(final Coordinate sq) {
    int x = sq.getX();
    int y = sq.getY();
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
   * @param sq
   *          The square
   * @throws AtaxxException
   *           An Exception
   */
  public final void setPiece(final AtaxxColor color, final Coordinate sq) throws AtaxxException {
    if (!squareIsEmpty(sq)) {
      throw new AtaxxException("Can't do that.");
    }
    this.board[sq.getX()][sq.getY()] = new AtaxxPiece(color);
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

  /**
   * Generate moves.
   * 
   * @author gej
   *
   */
  private final class AtaxxMoveGenerator {

    /** Board for moves. */
    private AtaxxBoard boardObj;

    /**
     * Construct the move generator.
     * 
     * @param inBoard
     *          Board to construct generator for.
     */
    private AtaxxMoveGenerator(final AtaxxBoard inBoard) {
      this.boardObj = inBoard;
    }

    /**
     * Generate a list of moves.
     * 
     * @param toMove
     *          Color to move.
     * @return list of moves.
     */
    public List<AtaxxMove> getAvailableMoves(final AtaxxColor toMove) {
      List<AtaxxMove> result = new ArrayList<>();
      this.seen = new HashSet<>();
      for (int i = 0; i < this.boardObj.getHeight(); i++) {
        for (int j = 0; j < this.boardObj.getWidth(); j++) {
          if (this.boardObj.board[i][j] != null && this.boardObj.board[i][j].getColor().equals(toMove)) {

            result.addAll(expandMoves(this.boardObj.board[i][j], i, j));

          }
        }
      }
      return result;
    }

    /** Avoid coordinates that have already been seen. */
    private Set<Coordinate> seen = new HashSet<>();

    /**
     * Build list of expansion moves.
     * 
     * @param ataxxPiece
     *          the piece.
     * @param i
     *          the i
     * @param j
     *          the j
     * @return a list of expand moves.
     */
    private List<AtaxxMove> expandMoves(final AtaxxPiece ataxxPiece, final int i, final int j) {

      Coordinate fromCoord = new Coordinate(i, j);

      int minX = Math.max(fromCoord.getX() - 1, 0);
      int maxX = Math.min(fromCoord.getX() + 1, this.boardObj.getWidth() - 1);
      int minY = Math.max(fromCoord.getY() - 1, 0);
      int maxY = Math.min(fromCoord.getY() + 1, this.boardObj.getHeight() - 1);

      List<AtaxxMove> result = new ArrayList<>();

      for (int x = minX; x <= maxX; x++) {
        for (int y = minY; y <= maxY; y++) {
          if (this.boardObj.board[x][y] == null) {
            Coordinate toCoord = new Coordinate(x, y);
            if (!this.seen.contains(toCoord)) {
              result.add(new AtaxxMove(AtaxxMove.Type.EXPAND, ataxxPiece.getColor(), fromCoord, toCoord));
              this.seen.add(toCoord);
            }
          }
        }
      }

      return result;
    }

  }

  /**
   * Generate moves.
   * 
   * @param toMove
   *          Color to move.
   * @return a list of moves.
   */
  public List<AtaxxMove> getAvailableMoves(final AtaxxColor toMove) {
    AtaxxMoveGenerator gen = new AtaxxMoveGenerator(this);
    return gen.getAvailableMoves(toMove);
  }

}
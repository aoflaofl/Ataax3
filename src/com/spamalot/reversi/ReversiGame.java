package com.spamalot.reversi;

import com.spamalot.boardgame.Direction;
import com.spamalot.boardgame.Game;
import com.spamalot.boardgame.GameException;
import com.spamalot.boardgame.MinMaxSearchable;
import com.spamalot.boardgame.Piece;
import com.spamalot.boardgame.PieceColor;
import com.spamalot.boardgame.Square;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handle the Reversi Game.
 * 
 * @author gej
 *
 */
class ReversiGame extends Game implements MinMaxSearchable<ReversiMove> {
  /** Default Board Size Constant. */
  private static final int DEFAULT_REVERSI_BOARD_SIZE = 8;

  /**
   * Create a Reversi board of the default size.
   * 
   * @throws GameException
   *           if something goes wrong.
   */
  ReversiGame() throws GameException {
    this(DEFAULT_REVERSI_BOARD_SIZE);
  }

  /**
   * Construct the game Object with a square board of a specified size.
   * 
   * @param size
   *          Size of a side
   * @throws GameException
   *           when there is some problem.
   */
  private ReversiGame(final int size) throws GameException {
    setBoard(new ReversiBoard(size));
    initBoard();

    // Square sq = getBoard().getSquareAt(0, 0);
    // Direction d = Direction.SE;
    // while (sq != null) {
    // System.out.println(sq);
    // sq = sq.getSquareInDirection(d);
    // }
  }

  /**
   * Set up board.
   */
  @Override
  protected void initBoard() {
    getBoard().getSquareAt((getNumFiles() / 2) - 1, (getNumRanks() / 2) - 1).setPiece(new Piece(PieceColor.WHITE));
    getBoard().getSquareAt(getNumFiles() / 2, getNumRanks() / 2).setPiece(new Piece(PieceColor.WHITE));
    getBoard().getSquareAt((getNumFiles() / 2) - 1, getNumRanks() / 2).setPiece(new Piece(PieceColor.BLACK));
    getBoard().getSquareAt(getNumFiles() / 2, (getNumRanks() / 2) - 1).setPiece(new Piece(PieceColor.BLACK));
  }

  @Override
  public boolean isOver() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int evaluate(final boolean gameOver) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void undoLastMove() {
    // TODO Auto-generated method stub

  }

  @Override
  public void makeMove(final ReversiMove move) {
    // TODO Auto-generated method stub

  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ReversiGame [board=\n");
    builder.append(getBoard());
    builder.append("toMove=");
    builder.append(getColorToMove());
    builder.append("\ngetAvailableMoves()=");
    builder.append(getAvailableMoves());
    // builder.append("\nUndo move list=" + this.undoMoveStack);
    builder.append("\n]");
    return builder.toString();
  }

  @Override
  public List<ReversiMove> getAvailableMoves() {

    // Using a Set to avoid duplicate moves being stored.
    Set<ReversiMove> moves = new HashSet<>();

    for (int file = 0; file < getNumFiles(); file++) {
      for (int rank = 0; rank < getNumRanks(); rank++) {
        Square sq = getBoard().getSquareAt(file, rank);
        if (hasSameColorPiece(sq)) {
          for (Direction dir : Direction.values()) {
            ReversiMove newMove = findMoveInDirection(sq, dir);
            if (newMove != null) {
              moves.add(newMove);
            }
          }
        }
      }
    }

    List<ReversiMove> ret = new ArrayList<>();
    ret.addAll(moves);

    return ret;
  }

  /**
   * Look in a direction from a square to see if it can make a move and create
   * the ReversiMove object if it can.
   * 
   * @param s
   *          Square to start from
   * @param dir
   *          Direction to look
   * @return A ReversiMove or null if no move.
   */
  private ReversiMove findMoveInDirection(final Square s, final Direction dir) {
    Square lookSquare = s.getSquareInDirection(dir);

    // Exit early if the first neighbor isn't an opposite color piece.
    if (!hasOppositeColorPiece(lookSquare)) {
      return null;
    }

    lookSquare = lookSquare.getSquareInDirection(dir);
    while (hasOppositeColorPiece(lookSquare)) {
      lookSquare = lookSquare.getSquareInDirection(dir);
    }

    if (lookSquare != null && lookSquare.isEmpty()) {
      return new ReversiMove(this.getColorToMove(), lookSquare);
    }

    return null;
  }

  /**
   * Return true if the Square is on the board, has a piece, and that piece is
   * of the opposite color of the side to move.
   * 
   * @param sq
   *          Square to look at
   * @return true if it has an opposite color piece.
   */
  private boolean hasOppositeColorPiece(final Square sq) {
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
  private boolean hasSameColorPiece(final Square sq) {
    return sq != null && !sq.isEmpty() && sq.getPiece().getColor() == this.getColorToMove();
  }
}

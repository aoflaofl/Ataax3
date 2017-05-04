package com.spamalot.reversi;

import com.spamalot.boardgame.Direction;
import com.spamalot.boardgame.Game;
import com.spamalot.boardgame.GameException;
import com.spamalot.boardgame.MinMaxSearchable;
import com.spamalot.boardgame.Move;
import com.spamalot.boardgame.Piece;
import com.spamalot.boardgame.PieceColor;
import com.spamalot.boardgame.Square;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
  public void makeMove(final Move move) {
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

    HashSet<ReversiMove> moves = new HashSet<>();

    System.out.println("Color to move: " + this.getColorToMove());
    for (int f = 0; f < getNumFiles(); f++) {
      for (int r = 0; r < getNumRanks(); r++) {
        Square s = getBoard().getSquareAt(f, r);

        if (!s.isEmpty() && s.getPiece().getColor() == this.getColorToMove()) {
          System.out.println(s);
          for (Direction d : Direction.values()) {

            ReversiMove x = look(s, d);
            if (x != null) {
              System.out.println("Found one " + d + ":" + x);
              moves.add(x);
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
   * Look in a direction from a square to see if it can make a move.
   * 
   * @param s
   *          Square to start from
   * @param dir
   *          Direction to look
   * @return The Square that can receive a piece to make the move.
   */
  private ReversiMove look(final Square s, final Direction dir) {
    PieceColor otherColor = this.getColorToMove().getOpposite();

    Square lookSquare = s.getSquareInDirection(dir);

    if (lookSquare == null || lookSquare.isEmpty() || lookSquare.getPiece().getColor() != otherColor) {
      return null;
    }

    while (lookSquare != null && !lookSquare.isEmpty() && lookSquare.getPiece().getColor() == otherColor) {
      lookSquare = lookSquare.getSquareInDirection(dir);
    }

    if (lookSquare != null && lookSquare.isEmpty()) {
      return new ReversiMove(this.getColorToMove(), lookSquare);
    }

    return null;
  }

}

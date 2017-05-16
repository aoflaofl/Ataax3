package com.spamalot.ataxx3;

import com.spamalot.boardgame.Coordinate;
import com.spamalot.boardgame.Game;
import com.spamalot.boardgame.GameControllable;
import com.spamalot.boardgame.GameException;
import com.spamalot.boardgame.MinMaxSearchable;
import com.spamalot.boardgame.Move;
import com.spamalot.boardgame.Piece;
import com.spamalot.boardgame.PieceColor;
import com.spamalot.boardgame.Square;

import java.util.List;
import java.util.Stack;

/**
 * Handle a game of Ataxx.
 * 
 * @author gej
 *
 */
class AtaxxGame extends Game implements MinMaxSearchable<AtaxxMove>, GameControllable<AtaxxMove> {
  /** Default Board Size for an Ataxx game. */
  private static final int DEFAULT_ATAXX_BOARD_SIZE = 7;

  /** Stack for undo move list. */
  private Stack<AtaxxUndoMove> undoMoveStack = new Stack<>();

  /**
   * Construct the Ataxx game with default size of side.
   * 
   * @throws GameException
   *           when there is some Ataxx related problem.
   */
  AtaxxGame() throws GameException {
    this(DEFAULT_ATAXX_BOARD_SIZE);
  }

  /**
   * Construct the Ataxx game with a square board of a specified size.
   * 
   * @param size
   *          Size of a side
   * @throws GameException
   *           when there is some Ataxx related problem.
   */
  private AtaxxGame(final int size) throws GameException {
    setBoard(new AtaxxBoard(size));
    initBoard();
  }

  /**
   * Put the initial pieces for a standard game of Ataxx.
   * 
   * @throws GameException
   *           when there is some Ataxx related problem.
   */
  @Override
  protected void initBoard() throws GameException {
    getBoard().getSquareAt(0, 0).setPiece(new Piece(PieceColor.WHITE));
    getBoard().getSquareAt(getBoard().getNumRanks() - 1, getBoard().getNumFiles() - 1).setPiece(new Piece(PieceColor.WHITE));

    getBoard().getSquareAt(0, getBoard().getNumFiles() - 1).setPiece(new Piece(PieceColor.BLACK));
    getBoard().getSquareAt(getBoard().getNumRanks() - 1, 0).setPiece(new Piece(PieceColor.BLACK));
  }

  /**
   * Make a move in Ataxx game. For performance reasons it is assumed move has
   * been checked for legality before calling this method.
   * 
   * @param move
   *          The move to make
   * @throws GameException
   *           When something goes wrong
   */
  @Override
  public void makeMove(final AtaxxMove move) {
    Piece piece = null;
    switch (move.getType()) {
      case DROP:
        piece = new Piece(move.getColor());
        break;
      case JUMP:
        Coordinate c = move.getFromSquare();
        Square sq = this.getBoard().getSquareAt(c);
        piece = sq.pickupPiece();
        break;
      case PASS:
        break;
      default:
        break;
    }
    List<Square> flipped = null;
    if (piece != null) {
      Coordinate c = move.getToSquare();
      Square sq = this.getBoard().getSquareAt(c);
      sq.setPiece(piece);
      flipped = AtaxxBoard.flipPiecesAroundSquare(sq, move.getColor());
    }

    this.undoMoveStack.push(new AtaxxUndoMove(move, flipped));

    switchColorToMove();
  }

  /**
   * Undo the effects of the last move made.
   */
  @Override
  public void undoLastMove() {
    AtaxxUndoMove move = this.undoMoveStack.pop();
    if (move.getMove().getType() != Move.Type.PASS) {
      this.undoPieceMove(move.getMove());
      flipPiecesInSquares(move.getFlipped());
    }
    switchColorToMove();
  }

  /**
   * Flip the pieces in the AtaxxSquares in the list.
   * 
   * @param listOfSquares
   *          List of Coordinates of pieces to flip
   */
  private static void flipPiecesInSquares(final List<Square> listOfSquares) {
    for (Square square : listOfSquares) {
      square.getPiece().flip();
    }
  }

  /**
   * Undo the piece move effects of a move.
   * 
   * @param move
   *          the move
   */
  private void undoPieceMove(final AtaxxMove move) {
    Coordinate c = move.getToSquare();
    Square sq = this.getBoard().getSquareAt(c);
    Piece p = sq.pickupPiece();
    if (move.getType() == Move.Type.JUMP) {
      Coordinate cFrom = move.getFromSquare();
      Square sqFrom = this.getBoard().getSquareAt(cFrom);
      sqFrom.setPiece(p);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.spamalot.ataxx3.MinMaxSearchable#getAvailableMoves()
   */
  @Override
  public List<AtaxxMove> getAvailableMoves() {
    return this.getAvailableMoves(getColorToMove());
  }

  /**
   * Generate moves.
   * 
   * @param toMove
   *          Color to move.
   * @return a list of moves.
   */
  private List<AtaxxMove> getAvailableMoves(final PieceColor toMove) {
    AtaxxMoveGenerator gen = new AtaxxMoveGenerator(this);
    return gen.getAvailableMoves(toMove);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AtaxxGame [board=\n");
    builder.append(getBoard());
    builder.append("toMove=");
    builder.append(getColorToMove());
    // builder.append("\ngetAvailableMoves()=");
    // builder.append(getAvailableMoves());
    // builder.append("\nUndo move list=" + this.undoMoveStack);
    builder.append("\n]");
    return builder.toString();
  }

  /**
   * Parses a String and returns an AtaxxMove object if it can be converted.
   * 
   * @param text
   *          The String of the move
   * @return An AtaxxMove object
   * @throws GameException
   *           when move cannot be parsed.
   */
  @Override
  public final AtaxxMove parseMove(final String text) throws GameException {
    if (text.length() != 4) {
      throw new GameException("Not an Ataxx move.");
    }

    String moveText = text.replaceAll("\\s", "");
    Coordinate from = textPositionToCoordinate(moveText.substring(0, 2));
    Coordinate to = textPositionToCoordinate(moveText.substring(2, 4));

    int maxDiff = Coordinate.maxDiff(from, to);

    if (maxDiff > 2) {
      throw new GameException("Illegal Move, too far away");
    }

    AtaxxMove.Type moveType = null;
    if (maxDiff == 2) {
      moveType = Move.Type.JUMP;
    } else {
      moveType = Move.Type.DROP;
    }

    Square f = getSquareAt(from.getX(), from.getY());
    Square t = getSquareAt(to.getX(), to.getY());

    return new AtaxxMove(moveType, getColorToMove(), f, t);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.spamalot.ataxx3.MinMaxSearchable#evaluate(boolean)
   */
  @Override
  public int evaluate(final boolean gameOver) {
    Square[][] b = getBoard().getSquares();

    int numRanks = this.getNumRanks();
    int numFiles = this.getNumFiles();

    int white = 0;
    int black = 0;
    int whiteMobility = 0;
    int blackMobility = 0;
    for (int f = 0; f < numFiles; f++) {
      for (int r = 0; r < numRanks; r++) {
        Piece p = b[f][r].getPiece();
        if (p != null) {
          if (p.getColor() == PieceColor.WHITE) {
            white++;
            Square[] sq = b[f][r].getOneAwaySquares();
            for (Square s : sq) {
              if (s.isEmpty()) {
                whiteMobility++;
              }
            }
            sq = b[f][r].getTwoAwaySquares();
            for (Square s : sq) {
              if (s.isEmpty()) {
                whiteMobility++;
              }
            }
          } else {
            black++;
            Square[] sq = b[f][r].getOneAwaySquares();
            for (Square s : sq) {
              if (s.isEmpty()) {
                blackMobility++;
              }
            }
            sq = b[f][r].getTwoAwaySquares();
            for (Square s : sq) {
              if (s.isEmpty()) {
                blackMobility++;
              }
            }
          }
        }
      }
    }
    // AtaxxScore s = getScore();

    if (gameOver) {
      return (white - black) * 10000;
    }

    int material = 0;
    if (white == 0) {
      material = -1000;
    } else if (black == 0) {
      material = 1000;
    } else {
      material = white - black;
    }

    int position = whiteMobility - blackMobility;
    return material * 100 + position;
  }
}

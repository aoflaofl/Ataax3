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
class AtaxxGame extends Game implements MinMaxSearchable<AtaxxMove>, GameControllable {
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
  public void makeMove(final Move move) {
    AtaxxMove aMove = (AtaxxMove) move;
    // A null move would indicate a pass.
    if (aMove == null) {
      switchColorToMove();
      this.undoMoveStack.push(null);
      return;
    }

    Piece piece = null;
    switch (aMove.getType()) {
      case EXPAND:
        piece = new Piece(aMove.getColor());
        break;
      case JUMP:
        piece = aMove.getFromSquare().pickupPiece();
        break;
      default:
        break;
    }
    aMove.getToSquare().setPiece(piece);

    List<Square> flipped = AtaxxBoard.flipPiecesAroundSquare(aMove.getToSquare(), aMove.getColor());

    this.undoMoveStack.push(new AtaxxUndoMove(aMove, flipped));

    switchColorToMove();
  }

  /**
   * Undo the effects of the last move made.
   */
  @Override
  public void undoLastMove() {
    AtaxxUndoMove move = this.undoMoveStack.pop();
    if (move != null) {
      AtaxxGame.undoPieceMove(move.getMove());
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
  private static void undoPieceMove(final AtaxxMove move) {
    Piece p = move.getToSquare().pickupPiece();
    if (move.getType() == AtaxxMove.Type.JUMP) {
      move.getFromSquare().setPiece(p);
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
   * Get the board as a String.
   * 
   * @return the board as a String.
   */
  @Override
  public final String boardToString() {
    return getBoard().toString();
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
  public final Move parseMove(final String text) throws GameException {
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
      moveType = AtaxxMove.Type.JUMP;
    } else {
      moveType = AtaxxMove.Type.EXPAND;
    }

    Square f = getSquareAt(from.getX(), from.getY());
    Square t = getSquareAt(to.getX(), to.getY());

    return new AtaxxMove(moveType, getColorToMove(), f, t);
  }

  /**
   * Turn text position into a Coordinate.
   * 
   * @param text
   *          Text to turn into coordinate
   * @return the Coordinate.
   */
  private static Coordinate textPositionToCoordinate(final String text) {
    char file = text.charAt(0);
    char rank = text.charAt(1);
    int x = file - 'a';
    int y = rank - '0' - 1;
    Coordinate coord = new Coordinate(x, y);
    return coord;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.spamalot.ataxx3.MinMaxSearchable#isOver()
   */
  @Override
  public boolean isOver() {
    Square[][] b = getBoard().getSquares();

    int numRanks = this.getNumRanks();
    int numFiles = this.getNumFiles();

    int white = 0;
    int black = 0;
    for (int f = 0; f < numFiles; f++) {
      for (int r = 0; r < numRanks; r++) {
        Piece p = b[f][r].getPiece();
        if (p != null) {
          if (p.getColor() == PieceColor.WHITE) {
            white++;
          } else {
            black++;
          }
        }
      }
    }

    // AtaxxScore s = getScore();

    int boardSize = (numRanks * numFiles) - getBoard().getNumBlockedSquares();

    return ((black + white == boardSize) || black == 0 || white == 0);
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

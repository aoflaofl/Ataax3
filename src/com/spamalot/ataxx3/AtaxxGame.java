package com.spamalot.ataxx3;

import com.spamalot.boardgame.Coordinate;
import com.spamalot.boardgame.Game;
import com.spamalot.boardgame.GameControllable;
import com.spamalot.boardgame.GameException;
import com.spamalot.boardgame.Move;
import com.spamalot.boardgame.Piece;
import com.spamalot.boardgame.PieceColor;
import com.spamalot.boardgame.Square;
import com.spamalot.boardgame.ai.MinMaxSearchable;
import com.spamalot.boardgame.ai.NegaMax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Handle a game of Ataxx.
 * 
 * @author gej
 *
 */
class AtaxxGame extends Game implements MinMaxSearchable<AtaxxMove>, GameControllable<AtaxxGame, AtaxxMove> {
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
    initGame();
  }

  /**
   * Put the initial pieces for a standard game of Ataxx.
   * 
   * @throws GameException
   *           when there is some Ataxx related problem.
   */
  @Override
  protected void initGame() throws GameException {
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
        piece = new Piece(this.getColorToMove());
        break;
      case JUMP:
        Coordinate c = move.getFromCoordinate();
        Square sq = this.getBoard().getSquareAt(c);
        piece = sq.pickupPiece();
        break;
      case PASS:
        break;
      default:
        break;
    }
    List<Piece> flipped = null;
    if (piece != null) {
      Coordinate c = move.getToCoordinate();
      Square sq = this.getBoard().getSquareAt(c);
      sq.setPiece(piece);
      flipped = AtaxxGame.getPiecesToFlip(sq, this.getColorToMove());
      flipPieces(flipped);
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
      flipPieces(move.getFlipped());
    }
    switchColorToMove();
  }

  /**
   * Undo the piece move effects of a move.
   * 
   * @param move
   *          the move
   */
  private void undoPieceMove(final AtaxxMove move) {
    Coordinate toCoord = move.getToCoordinate();
    Square sq = this.getBoard().getSquareAt(toCoord);
    Piece p = sq.pickupPiece();
    if (move.getType() == Move.Type.JUMP) {
      Coordinate fromCoord = move.getFromCoordinate();
      Square sqFrom = this.getBoard().getSquareAt(fromCoord);
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
    List<AtaxxMove> result = new ArrayList<>();
    AtaxxGame.seen.clear();
    for (int rank = 0; rank < this.getNumRanks(); rank++) {
      for (int file = 0; file < this.getNumFiles(); file++) {
        Square sq = this.getSquareAt(file, rank);
        if (sq.getPiece() != null && sq.getPiece().getColor().equals(toMove)) {
          result.addAll(generateMovesForSquare(sq));
        }
      }
    }

    if (result.size() == 0) {
      result.add(new AtaxxMove());
    }

    return result;
  }

  /** Avoid coordinates of expand moves that have already been seen. */
  private static Set<Square> seen = new HashSet<>();

  /**
   * Generate a list of moves for a square.
   * 
   * @param fromSquare
   *          Square to generate moves for
   * @return A list of moves.
   */
  private static Set<AtaxxMove> generateMovesForSquare(final Square fromSquare) {

    Set<AtaxxMove> result = new HashSet<>();

    for (Square sq : fromSquare.getOneAwaySquares()) {
      if (sq.isEmpty()) {
        if (!seen.contains(sq)) {
          result.add(new AtaxxMove(Move.Type.DROP, fromSquare.getPiece().getColor(), fromSquare.getCoordinate(), sq.getCoordinate()));
          seen.add(sq);
        }
      }
    }

    for (Square sq : fromSquare.getTwoAwaySquares()) {
      if (sq.isEmpty()) {
        result.add(new AtaxxMove(Move.Type.JUMP, fromSquare.getPiece().getColor(), fromSquare.getCoordinate(), sq.getCoordinate()));
      }
    }

    return result;
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
    builder.append("\ngetAvailableMoves()=");
    builder.append(getAvailableMoves());
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

    // Square f = getSquareAt(from.getX(), from.getY());
    // Square t = getSquareAt(to.getX(), to.getY());

    return new AtaxxMove(moveType, getColorToMove(), from, to);
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
    int position = whiteMobility - blackMobility;

    // if (gameOver) {
    // return (white - black) * 100;
    // }

    int material = 0;
    if (white == 0) {
      material = -1000;
    } else if (black == 0) {
      material = 1000;
    } else {
      material = white - black;
    }

    return material * 100 + position;
  }

  @Override
  public NegaMax<AtaxxGame, AtaxxMove> getThinker() throws GameException {
    NegaMax<AtaxxGame, AtaxxMove> ret = new NegaMax<>(this);

    ret.setDiffModifier(2);
    ret.setInitialDiff(1);

    return ret;
  }

  @Override
  public Game copyGame() throws GameException {
    AtaxxGame ret = new AtaxxGame();
    ret.getBoard().makeCopyOfPiecesInSquaresFromBoard(this.getBoard());
    ret.setColorToMove(this.getColorToMove());

    return ret;
  }

  /**
   * Get list of pieces around the square that don't match the passed in color.
   * 
   * @param ataxxSquare
   *          AtaxxSquare around which to flip
   * @param color
   *          Color to flip to
   * @return a List of AtaxxSquares that had flipped pieces.
   * 
   */
  private static List<Piece> getPiecesToFlip(final Square ataxxSquare, final PieceColor color) {
    PieceColor oppositeColor = color.getOpposite();

    List<Piece> retPieces = new ArrayList<>();

    Square[] squares = ataxxSquare.getOneAwaySquares();
    for (Square sq : squares) {
      Piece piece = sq.getPiece();
      if (piece != null && piece.getColor() == oppositeColor) {
        retPieces.add(piece);
      }
    }

    return retPieces;
  }

  @Override
  public boolean isOver() {
    int len = this.undoMoveStack.size();
    if (len >= 2) {
      AtaxxUndoMove move1 = this.undoMoveStack.elementAt(len - 1);
      AtaxxUndoMove move2 = this.undoMoveStack.elementAt(len - 2);

      if (move1.getMove().getType() == Move.Type.PASS && move2.getMove().getType() == Move.Type.PASS) {
        return true;
      }
    }

    return super.isOver();
  }
}

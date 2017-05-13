package com.spamalot.boardgame;

import java.util.Collections;
import java.util.List;

/**
 * AI Class for Board Game.
 * 
 * @author gej
 *
 * @param <T>
 *          Type of game to search
 * @param <S>
 *          Type of Move to use in Search
 */
class NegaMax<T extends MinMaxSearchable<S>, S extends Move> {
  /** What to multiply the diff by every time there is a fail high or low. */
  private static final int DIFF_MODIFIER = 2;

  /** Initial diff value. */
  private static final int INITIAL_DIFF = 50;

  /** Used for setting the maximum values for alpha, beta and bestValue. */
  private static final int MAX_VAL = Integer.MAX_VALUE;

  /** Game to think about. */
  private T thisGame;

  /** How many nodes were looked at in the search. */
  private long nodeCount = 0;

  /**
   * Construct a thinker.
   * 
   * @param game
   *          Game to think about
   */
  NegaMax(final T game) {
    this.thisGame = game;
  }

  /**
   * Start thinking using iterative deepening.
   * 
   * @param maxIteration
   *          How deep to evaluate
   * @return the best move.
   */
  public S think(final int maxIteration) {
    if (maxIteration < 1) {
      return null;
    }

    List<S> candidateMoves = this.thisGame.getAvailableMoves();

    int alphaDiff = -INITIAL_DIFF;
    int betaDiff = INITIAL_DIFF;

    int alpha = 0;
    int beta = 0;

    S move = null;

    for (int i = 0; i < maxIteration; i++) {
      // Sorting for an Moveable sorts first by evaluation, and then for equal
      // evaluations sorts expand moves before jump moves.
      Collections.sort(candidateMoves);

      int pass = 0;

      // Whether the search fails high or low
      boolean failed = true;

      System.out.println();
      System.out.println("-------------------------");

      while (failed) {
        System.out.println();
        System.out.println("    Iteration " + i + "." + pass + "  Window: alpha=" + alpha + " beta=" + beta);

        move = negaMaxAlphaBetaRoot(candidateMoves, alpha, beta, i + 1);

        if (move == null) {
          // a null move means there is no legal move. This is not a search
          // fail.
          failed = false;
        } else {
          if (move.getEvaluation() >= beta) {
            System.out.println("    Failed High: " + move);
            beta = move.getEvaluation() + betaDiff;
            betaDiff = betaDiff * DIFF_MODIFIER;
            failed = true;
            pass++;
          } else if (move.getEvaluation() <= alpha) {
            System.out.println("    Failed Low: " + move);
            alpha = move.getEvaluation() + alphaDiff;
            alphaDiff = alphaDiff * DIFF_MODIFIER;
            failed = true;
            pass++;
          } else {
            alpha = move.getEvaluation() - INITIAL_DIFF;
            beta = move.getEvaluation() + INITIAL_DIFF;
            alphaDiff = -INITIAL_DIFF;
            betaDiff = INITIAL_DIFF;
            failed = false;
          }
        }
      }

      System.out.println("Move found: Iteration " + i + "." + pass + " with Alpha=" + alpha + " Beta=" + beta);
      System.out.println(move);

    }
    return move;
  }

  // int Search(int alpha, int beta, int depth)
  // {
  // ~~int bestScore;
  // ~~MOVE bestMove = INVALID;
  // at branch end, use evaluation heuristic on position
  // ~~if(depth == 0) return Evaluate();

  // ~~for(iterDepth from 1 to depth) {
  // *** loop over all depths
  // ~~~~int iterAlpha = alpha;
  // *** as we touch alpha, we need to reset it to the original value for each
  // new depth
  // ~~~~bestScore = -INF;
  // for every depth we start with a clean slate
  // ~~~~PutInFront(bestMove);
  // *** put best move in front of list, so this depth iteration tries it first
  // ~~~~for(EVERY move) {
  // loop over moves
  // ~~~~~~MakeMove(move);
  // updates game state (board, side to move)
  // ~~~~~~score = -Search(-beta, -alpha, depth-1);
  // recursion (flip sign because we change POV)
  // ~~~~~~UnMake();
  // ~~~~~~if(score > bestScore) {
  // score accounting: remember best (from side-to-move POV)
  // ~~~~~~~~bestScore = score;
  // ~~~~~~~~if(score > iterAlpha) {
  // ~~~~~~~~~~iterAlpha = score;
  // ~~~~~~~~~~bestMove = move;
  // ~~~~~~~~~~if(score >= beta) break;
  // beta cutoff: previous ply is refuted by this move
  // ~~~~~~~~}
  // ~~~~~~}
  // ~~~~}
  // next move
  // ~~}
  // next depth
  // ~~return { bestScore, bestMove };
  // bestMove only used in root, to actually play it
  // }
  //
  //
  // int Search(int alpha, int beta, int depth, int color) {
  // int bestScore;
  // S bestMove = null;
  //
  // if (depth == 0) {
  // return color * thisGame.evaluate(false);
  // }
  //
  // return depth;
  // }

  /**
   * Root call to the NegaMaxAlphaBeta routine.
   * 
   * @param candidateMoves
   *          the move list
   * @param alpha
   *          the alpha
   * @param beta
   *          the beta
   * @param depth
   *          How deep to search
   * @return the best move found.
   */
  private S negaMaxAlphaBetaRoot(final List<S> candidateMoves, final int alpha, final int beta, final int depth) {
    System.out.println("Searching to a depth of " + depth);

    int color = 1;
    if (this.thisGame.getColorToMove() == PieceColor.BLACK) {
      color = -1;
    }

    int newAlpha = alpha;

    int bestValue = -MAX_VAL;
    S bestMove = null;

    for (S move : candidateMoves) {
      // System.out.println(move);
      this.thisGame.makeMove(move);
      this.nodeCount++;
      int evaluation = -negaMaxAlphaBeta(this.thisGame, depth - 1, -beta, -newAlpha, -color);
      move.setEvaluation(evaluation);

      this.thisGame.undoLastMove();

      if (evaluation > bestValue) {
        bestValue = evaluation;
        bestMove = move;

        System.out.println("New Best Move: " + move);
        System.out.println("Node Count to this point: " + this.nodeCount);
      }
      newAlpha = Math.max(newAlpha, evaluation);
      if (newAlpha >= beta) {
        break;
      }
    }
    System.out.println("Final Move: " + bestMove);
    System.out.println("Final Node Count: " + this.nodeCount);
    return bestMove;
  }
  // 01 function negamax(node, depth, alpha, beta, color)
  // 02 if depth = 0 or node is a terminal node
  // 03 ~~ return color * the heuristic value of node
  //
  // 04 childNodes := GenerateMoves(node)
  // 05 childNodes := OrderMoves(childNodes)
  // 06 bestValue := -INFINITY
  // 07 foreach child in childNodes
  // 08 ~~ v := -negamax(child, depth - 1, -beta, -alpha, -color)
  // 09 ~~ bestValue := max( bestValue, v )
  // 10 ~~ alpha := max( alpha, v )
  // 11 ~~ if alpha >= beta
  // 12 ~~ ~~ break
  // 13 return bestValue

  /**
   * Implement the negaMax algorithm with AlphaBeta Pruning.
   * 
   * @param game2
   *          Game to evaluate
   * @param depth
   *          depth to evaluate
   * @param alpha
   *          alph value
   * @param beta
   *          beta value
   * @param color
   *          color
   * @return an evaluation.
   */
  private int negaMaxAlphaBeta(final T game2, final int depth, final int alpha, final int beta, final int color) {
    boolean gameOver = game2.isOver();
    if (depth == 0 || gameOver) {
      return color * game2.evaluate(gameOver);
    }

    List<S> childMoves = game2.getAvailableMoves();
    Collections.sort(childMoves);
    // if (childMoves.size() == 0) {
    // childMoves.add((S) new Move(Move.Type.PASS));
    // }

    int bestValue = -MAX_VAL;
    int newAlpha = alpha;
    for (S move : childMoves) {
      game2.makeMove(move);
      this.nodeCount++;
      int evaluation = -negaMaxAlphaBeta(game2, depth - 1, -beta, -newAlpha, -color);
      game2.undoLastMove();

      bestValue = Math.max(bestValue, evaluation);
      newAlpha = Math.max(newAlpha, evaluation);
      if (newAlpha >= beta) {
        break;
      }
    }

    return bestValue;
  }
}

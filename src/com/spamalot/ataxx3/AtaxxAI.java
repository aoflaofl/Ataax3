package com.spamalot.ataxx3;

import java.util.Collections;
import java.util.List;

/**
 * AI Class for Ataxx Game.
 * 
 * @author gej
 *
 */
class AtaxxAI {
  /** Used for setting the maximum values for alpha, beta and bestValue. */
  private static final int MAX_VAL = 1000000;

  /** Game to think about. */
  private AtaxxGame ataxxGame;

  /** How many nodes were looked at in the search. */
  private int nodeCount = 0;

  /**
   * Construct a thinker.
   * 
   * @param game
   *          Game to think about
   */
  AtaxxAI(final AtaxxGame game) {
    this.ataxxGame = game;
  }

  /** Hold list of moves from curent game location. */
  // private List<AtaxxMove> globalChildMoves;

  /**
   * Start thinking using iterative deepening.
   * 
   * @param depth
   *          How deep to evaluate
   * @return the best move.
   */
  final AtaxxMove think(final int depth) {
    if (depth < 1) {
      return null;
    }

    int i = 0;

    List<AtaxxMove> globalChildMoves = this.ataxxGame.getAvailableMoves();

    AtaxxMove m = null;
    for (int iter = 1; iter <= depth; iter += 2) {
      Collections.shuffle(globalChildMoves);

      System.out.println("Iteration " + i);
      
      m = negaMaxAlphaBetaRoot(globalChildMoves, 2 * i + 1);i++;
      System.out.println();
    }
    return m;
  }

  /**
   * Root call to the NegaMaxAlphaBeta routine.
   * 
   * @param depth
   *          How deep to search
   * @return the best move found.
   */
  private AtaxxMove negaMaxAlphaBetaRoot(List<AtaxxMove> globalChildMoves, final int depth) {
    System.out.println("Searching to a depth of " + depth);

    int color = 1;
    if (this.ataxxGame.getToMove() == AtaxxColor.BLACK) {
      color = -1;
    }

    int alpha = -MAX_VAL;
    int beta = MAX_VAL;

    int bestValue = -MAX_VAL;
    AtaxxMove bestMove = null;

    for (AtaxxMove move : globalChildMoves) {
      try {
        this.ataxxGame.makeMove(move);
        this.nodeCount++;
      } catch (AtaxxException e) {
        e.printStackTrace();
        System.out.println("This is me: " + this.ataxxGame);
        System.out.println("This is the move that made me cry: " + move);
      }
      int v = -negaMaxAlphaBeta(this.ataxxGame, depth - 1, -beta, -alpha, -color);
      move.setEvaluation(v);

      this.ataxxGame.undoLastMove();

      if (v > bestValue) {
        bestValue = v;
        bestMove = move;

        System.out.println("New Best Move: " + move);
        System.out.println("New Best Evaluation: " + v);
        System.out.println("Node Count to this point: " + this.nodeCount);
      }
      alpha = Math.max(alpha, v);
      if (alpha >= beta) {
        break;
      }
    }
    System.out.println("Final Move: " + bestMove);
    System.out.println("Final Evaluation: " + bestValue);
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
   * @param game
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
  private int negaMaxAlphaBeta(final AtaxxGame game, final int depth, final int alpha, final int beta, final int color) {
    if (depth == 0 || game.isOver()) {
      return color * game.evaluate();
    }

    List<AtaxxMove> childMoves = game.getAvailableMoves();
    Collections.shuffle(childMoves);
    if (childMoves.size() == 0) {
      childMoves.add(null);
    }

    int bestValue = -MAX_VAL;
    int newAlpha = alpha;
    for (AtaxxMove move : childMoves) {
      try {
        game.makeMove(move);
        this.nodeCount++;
      } catch (AtaxxException e) {
        e.printStackTrace();
        System.out.println("This is me: " + game);
        System.out.println("This is the move that made me cry: " + move);
      }
      int v = -negaMaxAlphaBeta(game, depth - 1, -beta, -newAlpha, -color);
      game.undoLastMove();

      bestValue = Math.max(bestValue, v);
      newAlpha = Math.max(newAlpha, v);
      if (newAlpha >= beta) {
        break;
      }
    }

    return bestValue;
  }

  // 01 function negamax(node, depth, color)
  // 02 ____if depth = 0 or node is a terminal node
  // 03 ________return color * the heuristic value of node
  //
  // 04 ____bestValue := -INFINITY
  // 05 ____foreach child of node
  // 06 ________v := -negamax(child, depth - 1, -color)
  // 07 ________bestValue := max( bestValue, v )
  // 08 ____return bestValue
  //
  /**
   * Nega max algorithm.
   * 
   * @param game
   *          Game to search
   * @param depth
   *          depth to search
   * @param color
   *          color moving
   * @return evaluation of position.
   */
  private int negamax(final AtaxxGame game, final int depth, final int color) {
    if (depth == 0 || game.isOver()) {
      return color * game.evaluate();
    }

    int bestValue = -MAX_VAL;

    List<AtaxxMove> childMoves = game.getAvailableMoves();
    // Order Moves Here
    for (AtaxxMove move : childMoves) {

      try {
        // System.out.println(move);
        game.makeMove(move);
        this.nodeCount++;
        int v = -negamax(game, depth - 1, -color);
        bestValue = Math.max(v, bestValue);
        game.undoLastMove();
      } catch (AtaxxException e) {

        e.printStackTrace();
        System.out.println("This is me: " + game);
        System.out.println("This is the move that made me cry: " + move);
      }

    }
    return bestValue;
  }
}

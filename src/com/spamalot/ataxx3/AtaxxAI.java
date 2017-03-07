package com.spamalot.ataxx3;

import java.util.List;

/**
 * AI Class for Ataxx Game.
 * 
 * @author gej
 *
 */
class AtaxxAI {
  /**
   * Game to think about.
   */
  private AtaxxGame ataxxGame;

  /**
   * Construct a thinker.
   * 
   * @param game
   *          Game to think about
   */
  AtaxxAI(final AtaxxGame game) {
    this.ataxxGame = game;
  }

  /**
   * Start thinking.
   * 
   * @param depth
   *          How deep to evaluate
   * @return the best move.
   */
  final AtaxxMove think(final int depth) {
    if (depth < 1) {
      return null;
    }

    List<AtaxxMove> a = this.ataxxGame.getAvailableMoves();
    System.out.println("Current Board:\n" + this.ataxxGame);
    for (AtaxxMove move : a) {
      try {
        this.ataxxGame.makeMove(move);
      } catch (AtaxxException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      int colorToMove = -1;
      if (this.ataxxGame.getToMove() == AtaxxColor.BLACK) {
        colorToMove = 1;
      }

      int v = negaMax(this.ataxxGame, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, colorToMove);
      // System.out.println("Position:\n" + this.ataxxGame);
      System.out.println("Move:\n" + move);
      System.out.println("Evaluation: " + v);

      this.ataxxGame.undoLastMove();
    }
    return null;

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
   * Implement the negaMax algorithm.
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
  private int negaMax(final AtaxxGame game, final int depth, final int alpha, final int beta, final int color) {
    if (depth == 0 || game.isOver()) {
      return color * game.evaluate();
    }

    List<AtaxxMove> childMoves = game.getAvailableMoves();
    // Order Moves Here
    int bestValue = Integer.MIN_VALUE;

    for (AtaxxMove move : childMoves) {
      try {
        System.out.println(move);
        game.makeMove(move);
        // System.out.println(game);
      } catch (AtaxxException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      int v = -negaMax(game, depth - 1, -beta, -alpha, -color);
      game.undoLastMove();

      bestValue = Math.max(bestValue, v);
      int newAlpha = Math.max(alpha, v);
      if (newAlpha >= beta) {
        break;
      }
    }

    return bestValue;
  }
}

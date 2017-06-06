package com.spamalot.boardgame;

import java.util.List;

/**
 * Methods to implement in order for the game to be controllable.
 * 
 * @author gej
 *
 * @param <T>
 *          Move Type
 * @param <S>
 *          Game Type
 * 
 */
public interface GameControllable<S extends Game & MinMaxSearchable<T>, T extends Move> {

  /**
   * Get the List of available Moves in the Game.
   * 
   * @return A List of available moves for the game.
   */
  List<T> getAvailableMoves();

  /**
   * Get a String representation of the Board.
   * 
   * @return A String representation of the Board.
   */
  String boardToString();

  /**
   * Get a PieceCount Object for the current game.
   * 
   * @return a PieceCount object with the counts of the Black and White pieces.
   */
  PieceCount getPieceCount();

  /**
   * Undo last Move.
   */
  void undoLastMove();

  /**
   * Get the Color to move.
   * 
   * @return the Color to move.
   */
  PieceColor getColorToMove();

  /**
   * Check if Game is over.
   * 
   * @return true if the Game is over.
   */
  boolean isOver();

  /**
   * Make a Move.
   * 
   * @param movep
   *          the Move to make
   */
  void makeMove(T movep);

  /**
   * Parse text into a Move.
   * 
   * @param text
   *          the Move ini String.
   * @return the Move Object.
   * @throws GameException
   *           If something goes wrong.
   */
  T parseMove(String text) throws GameException;

  /**
   * Get the AI Object.
   * 
   * @return A Thinker.
   * @throws GameException
   *           if something goes wrong
   */
  NegaMax<S, T> getThinker() throws GameException;
}

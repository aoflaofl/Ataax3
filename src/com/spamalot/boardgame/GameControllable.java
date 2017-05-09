package com.spamalot.boardgame;

import java.util.List;

/**
 * Methods to implement in order for the game to be controllable.
 * 
 * @author gej
 *
 * @param <T>
 *          Move Type
 */
public interface GameControllable<T extends Move> {

  List<? extends Move> getAvailableMoves();

  String boardToString();

  PieceCount getPieceCount();

  void undoLastMove();

  PieceColor getColorToMove();

  boolean isOver();

  void makeMove(T movep);

  T parseMove(String text) throws GameException;

}

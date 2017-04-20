package com.spamalot.boardgame;

import java.util.List;

/**
 * Methods to implement in order for the game to be controllable.
 * 
 * @author gej
 *
 */
public interface GameControllable {

  List<? extends Move> getAvailableMoves();

  String boardToString();

  Score getScore();

  void undoLastMove();

  PieceColor getColorToMove();

  boolean isOver();

  void makeMove(Move movep);

  Move parseMove(String text) throws GameException;

}

package com.spamalot.boardgame;

import com.spamalot.ataxx3.Evaluatable;

import java.util.List;

/**
 * Methods to implement in order for the game to be controllable.
 * 
 * @author gej
 *
 */
public interface GameControllable {

  List<? extends Evaluatable> getAvailableMoves();

  String boardToString();

  Score getScore();

  void undoLastMove();

  PieceColor getColorToMove();

  boolean isOver();

  void makeMove(Evaluatable movep);

  Evaluatable parseMove(String text) throws GameException;

}

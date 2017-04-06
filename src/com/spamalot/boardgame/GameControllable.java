package com.spamalot.boardgame;

import com.spamalot.ataxx3.Evaluatable;

import java.util.List;

public interface GameControllable {

  List<? extends Evaluatable> getAvailableMoves();

  String boardToString();

  Score getScore();

  void undoLastMove();

  PieceColor getToMove();

  boolean isOver();

  void makeMove(Evaluatable movep);

  Evaluatable parseMove(String text) throws GameException;

}

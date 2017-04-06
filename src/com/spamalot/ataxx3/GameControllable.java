package com.spamalot.ataxx3;

import java.util.List;

interface GameControllable {

  List<? extends Evaluatable> getAvailableMoves();

  String boardToString();

  AtaxxScore getScore();

  void undoLastMove();

  PieceColor getToMove();

  boolean isOver();

  void makeMove(Evaluatable movep);

  Evaluatable parseMove(String text) throws AtaxxException;

}

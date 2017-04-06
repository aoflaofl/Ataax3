package com.spamalot.ataxx3;

import java.util.List;

interface GameControllable {

  List<Moveable> getAvailableMoves();

  String boardToString();

  AtaxxScore getScore();

  void undoLastMove();

  PieceColor getToMove();

  boolean isOver();

  void makeMove(AtaxxMove movep);

  AtaxxMove parseMove(String text) throws AtaxxException;

}

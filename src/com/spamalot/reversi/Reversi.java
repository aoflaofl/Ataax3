package com.spamalot.reversi;

import com.spamalot.boardgame.GameException;

/**
 * Game of Reversi.
 * 
 * @author gej
 *
 */
public final class Reversi {
  /**
   * Construct nothing.
   */
  private Reversi() {
  }

  /**
   * Start here.
   * 
   * @param args
   *          Arguments to the program
   * @throws GameException
   *           If something goes wrong.
   */
  public static void main(final String[] args) throws GameException {
    System.out.println("Reversi Game\nGene Johannsen");
    ReversiGame ataxxGame = new ReversiGame();
    System.out.println(ataxxGame.toString());

  }

}

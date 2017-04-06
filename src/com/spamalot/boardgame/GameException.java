package com.spamalot.boardgame;

/**
 * Exception thrown to flag an illegal move in Attax.
 * 
 * @author gej
 *
 */
public class GameException extends Exception {

  /** System generated serial ID. */
  private static final long serialVersionUID = -311227822482481911L;

  /**
   * The Constructor.
   * 
   * @param msg
   *          a message.
   */
  public GameException(final String msg) {
    System.out.println(msg);
  }

  /**
   * The Constructor.
   * 
   * @param move
   *          the bad move.
   * @param msg
   *          a message.
   */
  GameException(final Move move, final String msg) {
    // TODO Auto-generated constructor stub
  }

}

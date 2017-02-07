package com.spamalot.ataxx3;

/**
 * Exception thrown to flag an illegal move in Attax.
 * 
 * @author gej
 *
 */
public class AtaxxException extends Exception {

  /**
   * The Constructor.
   * 
   * @param msg
   *          a message.
   */
  AtaxxException(final String msg) {
    System.out.println(msg);
  }

  /**
   * The Constructor.
   * 
   * @param m
   *          the bad move.
   * @param msg
   *          a message.
   */
  AtaxxException(final AtaxxMove m, final String msg) {
    // TODO Auto-generated constructor stub
  }

}

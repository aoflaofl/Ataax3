package com.spamalot.ataxx3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.spamalot.boardgame.Board;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit Test for AtaxxBoard.
 * 
 * @author gej
 *
 */
public class AtaxxBoardTest {

  /** A square board to test. */
  private Board squareBoard;
  /** A rectangular board to test. */
  private Board rectangleBoard;

  @Before
  public final void setUp() throws Exception {
    this.squareBoard = new AtaxxBoard(1);
    this.rectangleBoard = new AtaxxBoard(4, 5);
  }

  @Test
  public final void testAtaxxBoardInt() {
    assertNotNull(this.squareBoard);
    assertEquals(1, this.squareBoard.getNumRanks());
    assertEquals(1, this.squareBoard.getNumFiles());
  }

  @Test
  public final void testAtaxxBoardIntInt() {
    assertNotNull(this.rectangleBoard);
    assertEquals(4, this.rectangleBoard.getNumFiles());
    assertEquals(5, this.rectangleBoard.getNumRanks());
  }

  @Test
  public final void testGetWidth() {
    assertEquals(1, this.squareBoard.getNumFiles());
    assertEquals(4, this.rectangleBoard.getNumFiles());
  }

  @Test
  public final void testGetHeight() {
    assertEquals(1, this.squareBoard.getNumRanks());
    assertEquals(5, this.rectangleBoard.getNumRanks());
  }

  // @Test(expected = com.spamalot.ataxx3.AtaxxException.class)
  // public final void testSetPiece() throws AtaxxException {
  // this.squareBoard.(AtaxxColor.WHITE, new Coordinate(0, 0));
  // this.squareBoard.setPiece(AtaxxColor.BLACK, new Coordinate(0, 0));
  // }

  // @Test
  // public final void testSquareIsEmpty() {
  // assertTrue(this.squareBoard.squareIsEmpty(new Coordinate(0, 0)));
  // }

}

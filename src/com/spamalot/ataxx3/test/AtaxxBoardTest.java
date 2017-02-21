package com.spamalot.ataxx3.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.spamalot.ataxx3.AtaxxBoard;
import com.spamalot.ataxx3.Coordinate;

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
  private AtaxxBoard squareBoard;
  /** A rectangular board to test. */
  private AtaxxBoard rectangleBoard;

  @Before
  public final void setUp() throws Exception {
    this.squareBoard = new AtaxxBoard(1);
    this.rectangleBoard = new AtaxxBoard(4, 5);
  }

  @Test
  public final void testAtaxxBoardInt() {
    assertNotNull(this.squareBoard);
    assertEquals(1, this.squareBoard.getHeight());
    assertEquals(1, this.squareBoard.getWidth());
  }

  @Test
  public final void testAtaxxBoardIntInt() {
    assertNotNull(this.rectangleBoard);
    assertEquals(4, this.rectangleBoard.getWidth());
    assertEquals(5, this.rectangleBoard.getHeight());
  }

  @Test
  public final void testGetWidth() {
    assertEquals(1, this.squareBoard.getWidth());
    assertEquals(4, this.rectangleBoard.getWidth());
  }

  @Test
  public final void testGetHeight() {
    assertEquals(1, this.squareBoard.getHeight());
    assertEquals(5, this.rectangleBoard.getHeight());
  }

  // @Test(expected = com.spamalot.ataxx3.AtaxxException.class)
  // public final void testSetPiece() throws AtaxxException {
  // this.squareBoard.(AtaxxColor.WHITE, new Coordinate(0, 0));
  // this.squareBoard.setPiece(AtaxxColor.BLACK, new Coordinate(0, 0));
  // }

  @Test
  public final void testSquareIsEmpty() {
    assertTrue(this.squareBoard.squareIsEmpty(new Coordinate(0, 0)));
  }

}

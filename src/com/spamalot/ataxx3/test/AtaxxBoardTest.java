/**
 * 
 */
package com.spamalot.ataxx3.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.spamalot.ataxx3.AtaxxBoard;
import com.spamalot.ataxx3.AtaxxColor;
import com.spamalot.ataxx3.AtaxxException;

import org.junit.Before;
import org.junit.Test;

/**
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
    squareBoard = new AtaxxBoard(1);
    rectangleBoard = new AtaxxBoard(4, 5);
  }

  @Test
  public final void testAtaxxBoardInt() {
    assertEquals(1, squareBoard.getHeight());
    assertEquals(1, squareBoard.getWidth());
    assertNotNull(squareBoard);
  }

  @Test
  public final void testAtaxxBoardIntInt() {
    assertEquals(4, rectangleBoard.getWidth());
    assertEquals(5, rectangleBoard.getHeight());
    assertNotNull(rectangleBoard);
  }

  @Test
  public final void testGetWidth() {
    assertEquals(1, squareBoard.getWidth());
    assertEquals(4, rectangleBoard.getWidth());
  }

  @Test
  public final void testGetHeight() {
    assertEquals(1, squareBoard.getHeight());
    assertEquals(5, rectangleBoard.getHeight());
  }

  @Test(expected = com.spamalot.ataxx3.AtaxxException.class)
  public final void testSetPiece() throws AtaxxException {
    squareBoard.setPiece(AtaxxColor.WHITE, 0, 0);
    squareBoard.setPiece(AtaxxColor.BLACK, 0, 0);
  }

  @Test
  public final void testMakeMove() {

  }

}

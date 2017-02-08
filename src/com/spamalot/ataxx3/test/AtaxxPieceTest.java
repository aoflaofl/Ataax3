package com.spamalot.ataxx3.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.spamalot.ataxx3.AtaxxColor;
import com.spamalot.ataxx3.AtaxxPiece;

import org.junit.Test;

/**
 * JUnit Test an Ataax Piece.
 * 
 * @author gej
 *
 */
public class AtaxxPieceTest {

  /**
   * Test method for
   * {@link com.spamalot.ataxx3.AtaxxPiece#AtaxxPiece(com.spamalot.ataxx3.AtaxxColor)}.
   */
  @SuppressWarnings("static-method")
  @Test
  public final void testAtaxxPiece() {
    AtaxxPiece p = new AtaxxPiece(AtaxxColor.WHITE);
    assertNotNull(p);
  }

  /**
   * Test method for {@link com.spamalot.ataxx3.AtaxxPiece#getColor()}.
   */
  @SuppressWarnings("static-method")
  @Test
  public final void testGetColor() {
    AtaxxPiece p = new AtaxxPiece(AtaxxColor.BLACK);
    assertEquals(AtaxxColor.BLACK, p.getColor());
  }

  /**
   * Test method for
   * {@link com.spamalot.ataxx3.AtaxxPiece#setColor(com.spamalot.ataxx3.AtaxxColor)}.
   */
  @SuppressWarnings("static-method")
  @Test
  public final void testFlip() {
    AtaxxPiece p = new AtaxxPiece(AtaxxColor.BLACK);
    p.flip();
    assertEquals(AtaxxColor.WHITE, p.getColor());
    p.flip();
    assertEquals(AtaxxColor.BLACK, p.getColor());

  }
}

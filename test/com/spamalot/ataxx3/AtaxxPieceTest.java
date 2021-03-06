package com.spamalot.ataxx3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.spamalot.boardgame.Piece;
import com.spamalot.boardgame.PieceColor;

import org.junit.Test;

/**
 * JUnit Test an Ataxx Piece.
 * 
 * @author gej
 *
 */
public class AtaxxPieceTest {

  /**
   * Test method for
   * {@link com.spamalot.boardgame.Piece#AtaxxPiece(com.spamalot.boardgame.PieceColor)}.
   */
  @SuppressWarnings("static-method")
  @Test
  public final void testAtaxxPiece() {
    Piece p = new Piece(PieceColor.WHITE);
    assertNotNull(p);
  }

  /**
   * Test method for {@link com.spamalot.boardgame.Piece#getColor()}.
   */
  @SuppressWarnings("static-method")
  @Test
  public final void testGetColor() {
    Piece p = new Piece(PieceColor.BLACK);
    assertEquals(PieceColor.BLACK, p.getColor());
  }

  /**
   * Test method for
   * {@link com.spamalot.boardgame.Piece#setColor(com.spamalot.boardgame.PieceColor)}.
   */
  @SuppressWarnings("static-method")
  @Test
  public final void testFlip() {
    Piece p = new Piece(PieceColor.BLACK);
    p.flip();
    assertEquals(PieceColor.WHITE, p.getColor());
    p.flip();
    assertEquals(PieceColor.BLACK, p.getColor());

  }
}

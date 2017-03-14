package com.spamalot.ataxx3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * @author gej
 *
 */
public class AtaxxGameTest {
  /**
   * Test variable.
   */
  private AtaxxGame ataxxGame;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {

  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {

    this.ataxxGame = new AtaxxGame();
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test method for {@link com.spamalot.ataxx3.AtaxxGame#AtaxxGame()}.
   */
  @Test
  public final void testAtaxxGame() {
    assertNotNull(this.ataxxGame);
//    assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(0, 0)), AtaxxColor.WHITE);
//    assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(6, 6)), AtaxxColor.WHITE);
//    assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(0, 6)), AtaxxColor.BLACK);
//    assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(6, 0)), AtaxxColor.BLACK);
  }

  /**
   * Test method for {@link com.spamalot.ataxx3.AtaxxGame#getAvailableMoves()}.
   */
  @Test
  public final void testGetAvailableMoves() {
    List<AtaxxMove> a = this.ataxxGame.getAvailableMoves();
    assertNotNull(a);
    assertTrue(a.size() > 0);

    for (AtaxxMove m : a) {
      System.out.println(m);
      try {
        this.ataxxGame.makeMove(m);
        this.ataxxGame.undoLastMove();
      } catch (AtaxxException e) {
        fail("Shouldn't get here\n" + e);
      }
    }
  }

//  @Test
//  public final void testPickupPutPiece() {
//
//    AtaxxPiece x;
//    try {
//      x = this.ataxxGame.pickupPiece(new Coordinate(0, 0));
//      this.ataxxGame.dropPiece(x, new Coordinate(0, 0));
//    } catch (AtaxxException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//
//    assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(0, 0)), AtaxxColor.WHITE);
//
//    try {
//      x = this.ataxxGame.pickupPiece(new Coordinate(1, 0));
//    } catch (AtaxxException e) {
//      // Should reach here
//    }
//  }

//  @Test
//  public final void testMoving() {
//    try {
//      AtaxxMove move1 = new AtaxxMove(AtaxxMove.Type.EXPAND, AtaxxColor.WHITE, new Coordinate(0, 0), new Coordinate(1, 1));
//      AtaxxMove move2 = new AtaxxMove(AtaxxMove.Type.JUMP, AtaxxColor.WHITE, new Coordinate(0, 0), new Coordinate(2, 2));
//
//      this.ataxxGame.makeMove(move1);
//      this.ataxxGame.makeMove(move2);
//
//      assertNull(this.ataxxGame.getColorOfPieceAt(new Coordinate(0, 0)));
//      assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(1, 1)), AtaxxColor.WHITE);
//      assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(2, 2)), AtaxxColor.WHITE);
//
//      this.ataxxGame.undoLastMove();
//
//      assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(0, 0)), AtaxxColor.WHITE);
//
//      this.ataxxGame.makeMove(move2);
//
//      AtaxxMove move3 = new AtaxxMove(AtaxxMove.Type.EXPAND, AtaxxColor.BLACK, new Coordinate(0, 6), new Coordinate(1, 5));
//      AtaxxMove move4 = new AtaxxMove(AtaxxMove.Type.JUMP, AtaxxColor.BLACK, new Coordinate(1, 5), new Coordinate(1, 3));
//
//      this.ataxxGame.makeMove(move3);
//      this.ataxxGame.makeMove(move4);
//
//      assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(2, 2)), AtaxxColor.BLACK);
//
//      this.ataxxGame.undoLastMove();
//
//      assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(2, 2)), AtaxxColor.WHITE);
//
//    } catch (AtaxxException e) {
//      fail("Shouldn't be an exception.");
//    }
//  }
}

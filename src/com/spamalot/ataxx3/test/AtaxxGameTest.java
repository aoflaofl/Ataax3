package com.spamalot.ataxx3.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.spamalot.ataxx3.AtaxxColor;
import com.spamalot.ataxx3.AtaxxException;
import com.spamalot.ataxx3.AtaxxGame;
import com.spamalot.ataxx3.AtaxxMove;
import com.spamalot.ataxx3.AtaxxPiece;
import com.spamalot.ataxx3.Coordinate;

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
    assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(0, 0)), AtaxxColor.WHITE);
    assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(6, 6)), AtaxxColor.WHITE);
    assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(0, 6)), AtaxxColor.BLACK);
    assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(6, 0)), AtaxxColor.BLACK);
  }

  /**
   * Test method for {@link com.spamalot.ataxx3.AtaxxGame#getAvailableMoves()}.
   */
  @Test
  public final void testGetAvailableMoves() {
    List<AtaxxMove> a = this.ataxxGame.getAvailableMoves();
    assertNotNull(a);
    assertTrue(a.size() > 0);
  }

  @Test
  public final void testPickupPutPiece() {

    AtaxxPiece x;
    try {
      x = this.ataxxGame.pickupPiece(new Coordinate(0, 0));
      this.ataxxGame.dropPiece(x, new Coordinate(0, 0));
    } catch (AtaxxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    assertEquals(this.ataxxGame.getColorOfPieceAt(new Coordinate(0, 0)), AtaxxColor.WHITE);

    try {
      x = this.ataxxGame.pickupPiece(new Coordinate(1, 0));
    } catch (AtaxxException e) {
      // Should reach here
    }
  }

  @Test
  public final void testMoving() {
    AtaxxMove move1 = new AtaxxMove(AtaxxMove.Type.EXPAND, AtaxxColor.WHITE, new Coordinate(0, 0), new Coordinate(1, 1));
    AtaxxMove move2 = new AtaxxMove(AtaxxMove.Type.JUMP, AtaxxColor.WHITE, new Coordinate(0, 0), new Coordinate(2, 2));

    try {
      this.ataxxGame.makeMove(move1);

      this.ataxxGame.makeMove(move2);

      this.ataxxGame.undoLastMove();

      this.ataxxGame.makeMove(move2);

      AtaxxMove move3 = new AtaxxMove(AtaxxMove.Type.EXPAND, AtaxxColor.BLACK, new Coordinate(0, 6), new Coordinate(1, 5));
      this.ataxxGame.makeMove(move3);

      AtaxxMove move4 = new AtaxxMove(AtaxxMove.Type.JUMP, AtaxxColor.BLACK, new Coordinate(1, 5), new Coordinate(1, 3));
      this.ataxxGame.makeMove(move4);

      this.ataxxGame.undoLastMove();
    } catch (AtaxxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}

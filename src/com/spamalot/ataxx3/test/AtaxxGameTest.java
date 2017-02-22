package com.spamalot.ataxx3.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.spamalot.ataxx3.AtaxxGame;
import com.spamalot.ataxx3.AtaxxMove;

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

}

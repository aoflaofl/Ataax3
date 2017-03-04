package com.spamalot.ataxx3;

import java.util.List;
import java.util.Scanner;

/**
 * Control an Ataxx Game.
 * 
 * @author gej
 *
 */
class AtaxxController {
  /** The Ataxx Game to control. */
  private AtaxxGame ataxxGame;

  /**
   * Construct an Ataxx Game Controller.
   * 
   * @param game
   *          The game to control
   */
  AtaxxController(final AtaxxGame game) {
    this.ataxxGame = game;
  }

  /**
   * Start control of the Ataxx Game.
   */
  final void control() {
    int depth = 1;
    try (Scanner scan = new Scanner(System.in, "UTF-8")) {
      String text;
      do {

        text = scan.nextLine();
        System.out.println(text);

        switch (text) {
          case "moves":
            List<AtaxxMove> moves = this.ataxxGame.getAvailableMoves();
            for (AtaxxMove move : moves) {
              System.out.println(move);
            }
            break;
          case "board":
            System.out.println(this.ataxxGame.boardToString());
            System.out.println("Color to move: " + this.ataxxGame.getToMove());
            System.out.println(this.ataxxGame.getScore());
            break;
          case "undo":
            this.ataxxGame.undoLastMove();
            break;
          case "think":
            AtaxxAI ai = new AtaxxAI(this.ataxxGame);
            ai.think(depth);
            break;
          case "help":
            System.out.println("moves board undo help");
            break;
          default:
            // If it is not a recognized command then it might be a move.
            try {
              AtaxxMove m = this.ataxxGame.parseMove(text);
              System.out.println(m);
              this.ataxxGame.makeMove(m);
            } catch (AtaxxException e) {
              System.out.println(e);
              System.out.println("Try again.");
            }
            break;
        }
      } while (!text.equals("end"));
    }
  }

}

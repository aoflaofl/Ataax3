package com.spamalot.ataxx3;

import java.util.List;
import java.util.Scanner;

public class AtaxxInterface {
  private AtaxxGame ataxxGame;

  /**
   * @param ataxxGame
   */
  public AtaxxInterface(final AtaxxGame ataxxGame) {
    this.ataxxGame = ataxxGame;
  }

  public final void play() {
    try (Scanner scan = new Scanner(System.in)) {
      String text;
      do {

        text = scan.nextLine();

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

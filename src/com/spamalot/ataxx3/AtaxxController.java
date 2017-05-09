package com.spamalot.ataxx3;

import com.spamalot.boardgame.GameException;
import com.spamalot.boardgame.Move;
import com.spamalot.boardgame.NegaMax;
import com.spamalot.boardgame.PieceColor;

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
    int depth = 2;
    try (Scanner scan = new Scanner(System.in, "UTF-8")) {
      String text;
      do {

        text = scan.nextLine();
        System.out.println(text);

        switch (text) {
          case "moves":
            List<AtaxxMove> moves = this.ataxxGame.getAvailableMoves();
            for (Move move : moves) {
              System.out.println(move);
            }
            break;
          case "board":
            System.out.println(this.ataxxGame.boardToString());
            System.out.println("Color to move: " + this.ataxxGame.getColorToMove());
            System.out.println(this.ataxxGame.getPieceCount());
            break;
          case "undo":
            this.ataxxGame.undoLastMove();
            break;
          case "think":
            NegaMax<AtaxxGame, AtaxxMove> ai = new NegaMax<>(this.ataxxGame);
            AtaxxMove move = ai.think(depth);

            System.out.println("Move:\n" + move);

            this.ataxxGame.makeMove(move);
            System.out.println(this.ataxxGame.boardToString());
            System.out.println("Color to move: " + this.ataxxGame.getColorToMove());
            System.out.println(this.ataxxGame.getPieceCount());
            break;
          case "play":
            while (!this.ataxxGame.isOver()) {
              if (this.ataxxGame.getColorToMove() == PieceColor.BLACK) {
                depth = 4;
              } else {
                depth = 5;
              }
              NegaMax<AtaxxGame, AtaxxMove> aip = new NegaMax<>(this.ataxxGame);
              AtaxxMove movep = aip.think(depth);
              this.ataxxGame.makeMove(movep);
              System.out.println(this.ataxxGame.boardToString());
              System.out.println("Color to move: " + this.ataxxGame.getColorToMove());
              System.out.println(this.ataxxGame.getPieceCount());
            }
            System.out.println("Done.");
            break;
          case "help":
            System.out.println("moves board undo help");
            break;
          case "end":
            break;
          default:
            // If it is not a recognized command then it might be a move.
            try {
              AtaxxMove move2 = this.ataxxGame.parseMove(text);
              System.out.println(move2);
              this.ataxxGame.makeMove(move2);
            } catch (GameException e) {
              System.out.println(e);
              System.out.println("Try again.");
            }
            break;
        }
      } while (!text.equals("end"));
    }
  }

}

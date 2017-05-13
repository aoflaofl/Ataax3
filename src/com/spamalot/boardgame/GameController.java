package com.spamalot.boardgame;

import java.util.List;
import java.util.Scanner;

/**
 * Control a Game.
 * 
 * @author gej
 *
 * @param <T>
 *          Type of Game to control
 * @param <S>
 *          Move Object in Game
 */
public class GameController<T extends MinMaxSearchable<S> & GameControllable<S>, S extends Move> {
  /** The Game to control. */
  private T game;

  /**
   * Construct a Game Controller.
   * 
   * @param g
   *          The game to control
   */
  public GameController(final T g) {
    this.game = g;
  }

  /**
   * Start control of the Game.
   */
  public final void control() {
    int depth = 2;
    try (Scanner scan = new Scanner(System.in, "UTF-8")) {
      String text;
      do {

        text = scan.nextLine();
        System.out.println(text);

        switch (text) {
          case "moves":
            List<S> moves = this.game.getAvailableMoves();
            for (Move move : moves) {
              System.out.println(move);
            }
            break;
          case "board":
            System.out.println(this.game.boardToString());
            System.out.println("Color to move: " + this.game.getColorToMove());
            System.out.println(this.game.getPieceCount());
            break;
          case "undo":
            this.game.undoLastMove();
            break;
          case "think":
            NegaMax<T, S> ai = new NegaMax<>(this.game);
            S move = ai.think(depth);

            System.out.println("Move:\n" + move);

            this.game.makeMove(move);
            System.out.println(this.game.boardToString());
            System.out.println("Color to move: " + this.game.getColorToMove());
            System.out.println(this.game.getPieceCount());
            break;
          case "play":
            while (!this.game.isOver()) {
              if (this.game.getColorToMove() == PieceColor.BLACK) {
                depth = 7;
              } else {
                depth = 6;
              }
              NegaMax<T, S> aip = new NegaMax<>(this.game);
              S movep = aip.think(depth);
              this.game.makeMove(movep);
              System.out.println(this.game.boardToString());
              System.out.println("Color to move: " + this.game.getColorToMove());
              System.out.println(this.game.getPieceCount());
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
              S move2 = this.game.parseMove(text);
              System.out.println(move2);
              this.game.makeMove(move2);
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

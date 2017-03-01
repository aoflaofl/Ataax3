package com.spamalot.ataxx3;

import java.util.List;
import java.util.Scanner;

/**
 * Play the game of Ataxx.
 * <p>
 * Ataxx is a board game originally appearing in an arcade game.
 * <p>
 * The rules are simple and something like Othello (Reversi).
 * <ul>
 * <li>The usual board is 7x7 with black's and white's stones starting in the
 * corners.
 * <li>There are two types of moves
 * <ol>
 * <li>Expand - A new stone is placed in an empty square adjacent to an existing
 * stone of the player's color.
 * <li>Jump - A stone on the board can jump two squares away. Diagonal and
 * Knight moves are allowed.
 * </ol>
 * <li>However a stone gets to a square, all stones adjacent to it of the
 * opponent's color flip to the player's color.
 * <li>Passing is allowed only if a player has no legal moves.
 * <li>Play continues until no more moves can be made. Whoever has the most
 * pieces on the board wins.
 * <li>Variants exist for putting barriers on the board to prevent some moves,
 * but that's out of scope for this little program.
 * </ul>
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Ataxx">Ataxx at Wikipedia</a>
 * @author gej
 *
 */
public final class Ataxx {

  /**
   * No construction.
   */
  private Ataxx() {
  }

  /**
   * Main method.
   * 
   * @param args
   *          arguments to the program.
   * @throws AtaxxException
   *           when there is some Ataxx related problem.
   */
  public static void main(final String[] args) throws AtaxxException {
    System.out.println("Ataxx Game\nGene Johannsen");

    AtaxxGame ataxxGame = new AtaxxGame();
    System.out.println(ataxxGame.toString());

    try (Scanner scan = new Scanner(System.in)) {
      String text;
      do {

        text = scan.nextLine();

        switch (text) {
          case "moves":
            List<AtaxxMove> moves = ataxxGame.getAvailableMoves();
            for (AtaxxMove move : moves) {
              System.out.println(move);
            }
            break;
          case "board":
            System.out.println(ataxxGame.boardToString());
            System.out.println(ataxxGame.getScore());
            break;
          case "undo":
            ataxxGame.undoLastMove();
            break;
          case "help":
            System.out.println("moves board undo help");
            break;
          default:
            // If it is not a recognized command then it might be a move.
            AtaxxMove m = ataxxGame.parseMove(text);
            System.out.println(m);
            ataxxGame.makeMove(m);
            break;
        }

      } while (!text.equals("end"));
    }

  }

}

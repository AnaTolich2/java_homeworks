package game;

import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static void main(String[] args) {
        final Game game = new Game(false, new HumanPlayer(), new RandomPlayer(5, 5));
        int result = 0;
        System.out.println();
//        result = game.play(new MNKBoard(5, 5, 4, 2));
        result = game.play(new MKRhombusBoard(10, 2, 4));
        System.out.println("Game result: " + result);
    }
}

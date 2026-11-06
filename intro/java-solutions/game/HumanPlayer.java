package game;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            String attempt = in.nextLine();
            while (attempt.isEmpty()){
                out.println("Waiting for input...");
                attempt = in.nextLine();
            }
            String[] args = attempt.split(" ");
            try {
                if (args.length == 2) {
                    final Move move = new Move(Integer.parseInt(args[0]) - 1, Integer.parseInt(args[1]) - 1, cell);
                    if (position.isValid(move)) {
                        return move;
                    }
                }
            } catch (Exception e){
                out.println("Move " + attempt + " is invalid");
                continue;
            }
            out.println("Move " + attempt + " is invalid");
        }
    }
}

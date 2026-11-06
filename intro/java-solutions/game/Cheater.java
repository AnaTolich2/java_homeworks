package game;

import java.util.Random;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Cheater implements Player {
    private final Random random;
    public Cheater(final Random random) {
        this.random = random;
    }

    public Cheater() {
        this(new Random());
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        Board board = (Board) position;
        System.out.println(board.getPosition());
        while (true) {
            int r = random.nextInt(3);
            int c = random.nextInt(3);
            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}

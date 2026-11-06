package game;

import java.util.Random;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class RandomPlayer implements Player {
    private final Random random;
    private int m, n;
    public RandomPlayer(int m, int n, final Random random) {
        this.m = m;
        this.n = n;
        this.random = random;
    }

    public RandomPlayer(int m, int n) {
        this(m, n, new Random());
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            int r = random.nextInt(position.getBound());
            int c = random.nextInt(n);
            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}

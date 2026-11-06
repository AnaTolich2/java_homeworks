package game;

import java.util.Random;

public class RandomRhombusPlayer implements Player {
    private final Random random;
    private int m;

    public RandomRhombusPlayer(int m, final Random random) {
        this.m = m;
        this.random = random;
    }

    public RandomRhombusPlayer(int m, int n) {
        this(m, new Random());
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            int r = random.nextInt(m);
            int c = random.nextInt(m * 2 - 1); // :NOTE: use getMaxBound
            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}

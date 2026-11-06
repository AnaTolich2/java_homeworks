package game;

public class PositionImpl implements Position {
    private final MNKBoard board;

    public PositionImpl(MNKBoard board) {
        this.board = board;
    }

    @Override
    public boolean isValid(Move move) {
        return board.isValid(move);
    }

    @Override
    public int getBound() {
        return board.getBound();
    }

    @Override
    public Cell getCell(int r, int c) {
        return board.getCell(r, c);
    }

    @Override
    public String toString() {
        return board.toString();
    }
}
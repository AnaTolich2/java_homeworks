package game;

import java.util.Map;

public class MKRhombusBoard extends MNKBoard {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    public MKRhombusBoard(int m, int k, int p) {
        super(m, m, k, p);
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        int[] mv = newMoves(move);
        cells[mv[0]][mv[1]] = move.getValue();
        return makeMove(mv[0], mv[1], move.getValue());
    }

    @Override
    public int getBound() {
        return 2 * m - 1;
    }

    private int[] newMoves(final Move move) {
        int[] mv = new int[2];
        if (move.getRow() < m) {
            mv[0] = move.getRow() - move.getColumn();
            mv[1] = move.getColumn();
        } else {
            mv[0] = m - 1 - move.getColumn();
            mv[1] = move.getRow() - m + 1 + move.getColumn();
        }
        return mv;
    }

    @Override
    boolean isValid(final Move move) {
        int[] mv = newMoves(move);
        return isValid(mv[0], mv[1]);
    }

    @Override
    public Cell getCell(final int r, final int c) {
        int nr, nc;
        if (r < m) {
            nr = r - c;
            nc = c;
        } else {
            nr = m - 1 - c;
            nc = r - m + 1 + c;
        }
        return cells[nr][nc];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        int size = 2 * m - 1;
        for (int r = 0; r < size; r++) {
            if (r >= m) {
                sb.append(" ".repeat(3 * (r - m + 1)));
            } else {
                sb.append(" ".repeat(3 * (m - r - 1)));
            }
            if (r < m) {
                for (int c = 0; c <= r; c++) {
                    sb.append(SYMBOLS.get(cells[r - c][c])).append(" ".repeat(5));
                }
            } else {
                for (int c = 0; c < 2 * m - 1 - r; c++) {
                    sb.append(SYMBOLS.get(cells[m - 1 - c][r - (m - 1) + c])).append(" ".repeat(5));
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

}

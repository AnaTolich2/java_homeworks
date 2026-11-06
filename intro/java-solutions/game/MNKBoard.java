package game;

import java.util.Arrays;
import java.util.Map;

public class MNKBoard implements Board {
    protected int m, n, k, p;
    protected int busyCells;
    protected int moveNum;
    protected static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    public int getBound() {
        return m;
    }

    protected final Cell[][] cells;
    protected Cell turn;

    protected MNKBoard(int m, int n, int k, int p) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.p = p;
        moveNum = 1;
        busyCells = 0;
        this.cells = new Cell[m][n];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return new PositionImpl(this);
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        cells[move.getRow()][move.getColumn()] = move.getValue();
        return makeMove(move.getRow(), move.getColumn(), move.getValue());
    }

    protected Result makeMove(int newMoveX, int newMoveY, Cell value) {
        busyCells++;
        if (busyCells == m * n) {
            return Result.DRAW;
        }
        int[] dx = {1, 0, 1, -1};
        int[] dy = {0, 1, 1, 1};
        for (int dir = 0; dir < 4; dir++) {
            int count = 1;
            int nx = newMoveX + dx[dir];
            int ny = newMoveY + dy[dir];
            while (nx >= 0 && nx < m && ny >= 0 && ny < n && cells[nx][ny] == value) {
                count++;
                nx += dx[dir];
                ny += dy[dir];
            }
            nx = newMoveX - dx[dir];
            ny = newMoveY - dy[dir];
            while (nx >= 0 && nx < m && ny >= 0 && ny < n && cells[nx][ny] == value) {
                count++;
                nx -= dx[dir];
                ny -= dy[dir];
            }
            if (count >= k) {
                return Result.WIN;
            }
        }
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    boolean isValid(final Move move) {
        return isValid(move.getRow(), move.getColumn());
    }

    protected boolean isValid(int newMoveX, int newMoveY) {
        moveNum = busyCells + 1;
        return (0 <= newMoveX && newMoveX < m
                && 0 <= newMoveY && newMoveY < n && (moveNum > p
                || firstP(newMoveX, newMoveY))) && cells[newMoveX][newMoveY] == Cell.E
                && turn == getCell();
    }

    private boolean firstP(int newMoveX, int newMoveY) {
        return (newMoveX >= (m - (2 * moveNum + (-m % 2))) / 2) &&
                (newMoveX < ((m + (2 * moveNum + (-m % 2))) / 2)) &&
                (newMoveY >= (n - (2 * moveNum + (-n % 2))) / 2) &&
                (newMoveY < ((n + (2 * moveNum + (-n % 2))) / 2));
    }

    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                sb.append(SYMBOLS.get(cells[r][c])).append(" ".repeat(2));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
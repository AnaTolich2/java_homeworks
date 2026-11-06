package expression;

import java.util.Map;

public class Const extends SomeOperation {
    private long c;

    public Const(int c) {
        this.c = c;
    }

    public Const(long c) {
        this.c = c;
    }

    public int evaluate(int x) {
        return (int) c;
    }

    public long evaluate(long x) {
        return c;
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean equals(Object expression) {
        if (this == expression) {
            return true;
        }
        if (expression == null || getClass() != expression.getClass()) {
            return false;
        }
        Const n = (Const) expression;
        return this.c == n.c;
    }

    @Override
    public int hashCode() {
        if (c <= Integer.MAX_VALUE && c >= Integer.MIN_VALUE) {
            return Integer.hashCode((int) c);
        }
        return Long.hashCode(c);
    }

    @Override
    public String toString() {
        return String.valueOf(c);
    }

    @Override
    public String toMiniString() {
        return String.valueOf(c);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int) c;
    }

}

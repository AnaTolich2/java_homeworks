package expression.exceptions;

public class MoreThan extends CheckedBinaryOperation {
    protected MoreThan(NewSomeOperation x1, NewSomeOperation x2) {
        super(x1, x2);
    }

    @Override
    protected String getOperator() {
        return ">";
    }

    @Override
    protected int subExpression(int x1, int x2) {
        if (x1 > x2) {
            return 1;
        }
        return 0;
    }

    @Override
    protected long subExpression(long x1, long x2) {
        return 0;
    }

    @Override
    protected boolean isAssociative() {
        return false;
    }

    @Override
    public int getPriority() {
        return 1;
    }
}

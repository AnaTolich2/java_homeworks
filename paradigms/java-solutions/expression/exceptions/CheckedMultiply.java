package expression.exceptions;

public class CheckedMultiply extends CheckedBinaryOperation {
    public CheckedMultiply(NewSomeOperation x1, NewSomeOperation x2) {
        super(x1, x2);
    }

    @Override
    protected boolean isAssociative() {
        return true;
    }

    @Override
    protected int subExpression(int x1, int x2) {
        if ((x1 > 0 && x2 > 0 && x1 > Integer.MAX_VALUE / x2) || (x1 < 0 && x2 < 0 && x1 < Integer.MAX_VALUE / x2)) {
            throw new OverflowException();
        }
        //x2 = -1; min_value / -1 = overflow
        if ((x1 < 0 && x2 > 0 && x1 < Integer.MIN_VALUE / x2) || (x1 > 0 && x2 < -1 && x1 > Integer.MIN_VALUE / x2)) {
            throw new OverflowException();
        }
        return x1 * x2;
    }

    @Override
    protected long subExpression(long x1, long x2) {
        return x1 * x2;
    }

    @Override
    public int getPriority() {
        return 3;
    }


    @Override
    protected String getOperator() {
        return "*";
    }
}

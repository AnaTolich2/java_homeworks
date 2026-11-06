package expression.exceptions;

public class CheckedAdd extends CheckedBinaryOperation {
    public CheckedAdd(NewSomeOperation x1, NewSomeOperation x2) {
        super(x1, x2);
    }


    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    protected String getOperator() {
        return "+";
    }

    @Override
    protected int subExpression(int x1, int x2) {
        if (x1 > 0 && x2 > 0 && x1 > Integer.MAX_VALUE - x2) {
            throw new OverflowException();
        }
        if (x1 < 0 && x2 < 0 && x1 < Integer.MIN_VALUE - x2) {
            throw new OverflowException();
        }
        return x1 + x2;
    }

    @Override
    protected long subExpression(long x1, long x2) {
        return x1 + x2;
    }


    @Override
    protected boolean isAssociative() {
        return true;
    }

}

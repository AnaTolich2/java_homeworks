package expression;

import java.util.Map;

public class Multiply extends BinaryOperation {
    public Multiply(SomeOperation x1, SomeOperation x2) {
        super(x1, x2);
    }

    public int evaluate(int x) { // :NOTE: Subexpressions
        return x1.evaluate(x) * x2.evaluate(x);
    }


    @Override
    protected boolean isAssociative() {
        return true;
    }

    @Override
    protected int subExpression(int x1, int x2) {
        return x1 * x2;
    }

    @Override
    protected long subExpression(long x1, long x2) {
        return x1 * x2;
    }

    @Override
    public int getPriority() {
        return 2;
    }


    @Override
    protected String getOperator() {
        return "*";
    }
}

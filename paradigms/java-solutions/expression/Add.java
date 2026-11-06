package expression;

import java.util.Map;

public class Add extends BinaryOperation {
    public Add(SomeOperation x1, SomeOperation x2) {
        super(x1, x2);
    }


    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    protected String getOperator() {
        return "+";
    }

    @Override
    protected int subExpression(int x1, int x2) {
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

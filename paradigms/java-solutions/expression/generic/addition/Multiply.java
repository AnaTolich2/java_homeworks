package expression.generic.addition;

import expression.generic.types.*;

public class Multiply<T> extends BinaryOperation<T> {
    public Multiply(GenericExpression<T> x1, GenericExpression<T> x2, GenericType<T> type) {
        super(x1, x2, type);
    }

    @Override
    protected boolean isAssociative() {
        return true;
    }


    @Override
    protected T subExpression(T x1, T x2) {
        return type.multiply(x1, x2);
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

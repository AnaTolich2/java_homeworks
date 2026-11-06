package expression.generic.addition;

import expression.generic.types.*;

public class MoreEqualThan<T> extends BinaryOperation<T> {
    protected MoreEqualThan(GenericExpression<T> x1, GenericExpression<T> x2, GenericType<T> type) {
        super(x1, x2, type);
    }

    @Override
    protected String getOperator() {
        return ">=";
    }

    @Override
    protected T subExpression(T x1, T x2) {
        return type.moreEqualThan(x1, x2);
    }

    @Override
    protected boolean isAssociative() {
        return false;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}

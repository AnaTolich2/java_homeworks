package expression.generic.addition;

import expression.generic.types.*;

public class MoreThan<T> extends BinaryOperation<T> {
    protected MoreThan(GenericExpression<T> x1, GenericExpression<T> x2, GenericType<T> type) {
        super(x1, x2, type);
    }

    @Override
    protected String getOperator() {
        return ">";
    }
    @Override
    protected T subExpression(T x1, T x2) {
        return type.moreThan(x1, x2);
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

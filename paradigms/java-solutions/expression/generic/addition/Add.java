package expression.generic.addition;

import expression.generic.types.*;

public class Add<T> extends BinaryOperation<T> {
    public Add(GenericExpression<T> x1, GenericExpression<T> x2, GenericType<T> type) {
        super(x1, x2, type);
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
    protected T subExpression(T x1, T x2) {
        return type.add(x1, x2);
    }



    @Override
    protected boolean isAssociative() {
        return true;
    }


}

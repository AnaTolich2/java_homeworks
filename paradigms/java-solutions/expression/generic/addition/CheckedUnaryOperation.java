package expression.generic.addition;

import expression.exceptions.NewSomeOperation;
import expression.generic.types.GenericType;

import java.util.Objects;

public abstract class CheckedUnaryOperation<T> implements GenericExpression<T>{
    protected final GenericExpression<T> x1;
    protected final GenericType<T> type;
    CheckedUnaryOperation(GenericExpression<T> x1, GenericType<T> type){
        this.x1 = x1;
        this.type = type;
    }
    @Override
    public boolean equals(Object expression) {
        if (this == expression){
            return true;
        }
        if (expression == null || getClass() != expression.getClass()){ return false;}
        CheckedUnaryOperation<?> n = (CheckedUnaryOperation<?>) expression;
        return this.x1.equals(n.x1);
    }

    protected abstract String getOperator();
    @Override
    public int hashCode() {
        return Objects.hash(x1, getOperator());
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return null;
    }

    @Override
    public int getPriority() {
        return 0;
    }


    @Override
    public String toMiniString() {
        return toString();
    }
}

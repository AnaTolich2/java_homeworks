package expression.generic.addition;

import expression.generic.types.GenericType;

import java.util.Objects;

abstract public class BinaryOperation<T> implements GenericExpression<T> {
    protected final GenericExpression<T> x1;
    protected final GenericExpression<T> x2;
    protected final GenericType<T> type;
    protected BinaryOperation(GenericExpression<T> x1, GenericExpression<T> x2, GenericType<T> type) {
        this.x1 = x1;
        this.x2 = x2;
        this.type = type;
    }

    protected abstract String getOperator();

    protected abstract T subExpression(T x1, T x2);
    @Override
    public T evaluate(T x, T y, T z) {
        return subExpression(x1.evaluate(x, y, z), x2.evaluate(x, y, z));
    }


    @Override
    public boolean equals(Object expression) {
        if (this == expression) {
            return true;
        }
        if (expression == null || getClass() != expression.getClass()) {
            return false;
        }
        BinaryOperation n = (BinaryOperation) expression;
        return this.x1.equals(n.x1) && this.x2.equals(n.x2);
    }

    @Override
    public String toString() {
        return "(" + x1.toString() + " " + getOperator() + " " + x2.toString() + ")";
    }
    @Override
    public String toMiniString() {
        return addBrackets(x1, false) +
                " " + getOperator() + " " +
                addBrackets(x2, true);
    }
    private String addBrackets(GenericExpression<T> expr, boolean isRight) {
        if (expr.getPriority() < this.getPriority() ||
                (expr.getPriority() == this.getPriority() && (
                        (isRight && (!this.isAssociative() ||
                                (this.getPriority() == 2 && getClass() != expr.getClass()))))
                )){
            return "(" + expr.toMiniString() + ")";
        }
        return expr.toMiniString();
    }
    protected abstract boolean isAssociative();


    @Override
    public int hashCode() {
        return Objects.hash(x1, x2, getClass());
    }
}

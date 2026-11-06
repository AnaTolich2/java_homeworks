package expression.exceptions;

import expression.SomeOperation;

import java.util.Objects;

abstract public class CheckedBinaryOperation extends NewSomeOperation {
    protected final NewSomeOperation x1;
    protected final NewSomeOperation x2;

    protected CheckedBinaryOperation(NewSomeOperation x1, NewSomeOperation x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    protected abstract String getOperator();

    protected abstract int subExpression(int x1, int x2);

    protected abstract long subExpression(long x1, long x2);

    @Override
    public int evaluate(int x, int y, int z) {
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
        CheckedBinaryOperation n = (CheckedBinaryOperation) expression;
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

    private String addBrackets(NewSomeOperation expr, boolean isRight) {
        if (expr.getPriority() < this.getPriority() ||
                (expr.getPriority() == this.getPriority() && (
                        (isRight && ((!this.isAssociative()) ||
                                (this.getPriority() == 3 && getClass() != expr.getClass()))))
                )) {
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
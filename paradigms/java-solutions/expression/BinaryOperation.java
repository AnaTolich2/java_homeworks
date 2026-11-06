package expression;

import java.util.Map;
import java.util.Objects;

abstract public class BinaryOperation extends SomeOperation {
    protected final SomeOperation x1;
    protected final SomeOperation x2;

    protected BinaryOperation(SomeOperation x1, SomeOperation x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    protected abstract String getOperator();

    protected abstract int subExpression(int x1, int x2);

    protected abstract long subExpression(long x1, long x2);

    @Override
    public int evaluate(int x) {
        return subExpression(x1.evaluate(x), x2.evaluate(x));
    }

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


    protected abstract boolean isAssociative();

    private String addBrackets(SomeOperation expr, boolean isRight) {
        if (expr.getPriority() < this.getPriority() ||
                (expr.getPriority() == this.getPriority() && (
                        (isRight && (!this.isAssociative() ||
                                (this.getPriority() == 2 && getClass() != expr.getClass()))))
                )){
            return "(" + expr.toMiniString() + ")";
        }
        return expr.toMiniString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x1, x2, getClass());
    }
}

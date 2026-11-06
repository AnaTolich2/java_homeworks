package expression.exceptions;

import java.util.Objects;

public abstract class CheckedUnaryOperation extends NewSomeOperation {
    protected final NewSomeOperation x1;

    CheckedUnaryOperation(NewSomeOperation x1) {
        this.x1 = x1;
    }

    @Override
    public boolean equals(Object expression) {
        if (this == expression) {
            return true;
        }
        if (expression == null || getClass() != expression.getClass()) {
            return false;
        }
        CheckedUnaryOperation n = (CheckedUnaryOperation) expression;
        return this.x1.equals(n.x1);
    }

    protected abstract String getOperator();

    @Override
    public int hashCode() {
        return Objects.hash(x1, getOperator());
    }
}

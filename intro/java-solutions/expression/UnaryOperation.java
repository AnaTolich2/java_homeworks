package expression;

import java.util.Map;
import java.util.Objects;

public abstract class UnaryOperation extends SomeOperation{
    protected final SomeOperation x1;
    protected UnaryOperation(SomeOperation x1){
        this.x1 = x1;
    }
    @Override
    public boolean equals(Object expression) {
        if (this == expression){
            return true;
        }
        if (expression == null || getClass() != expression.getClass()){ return false;}
        UnaryOperation n = (UnaryOperation) expression;
        return this.x1.equals(n.x1);
    }

    protected abstract String getOperator();
    @Override
    public int hashCode() {
        return Objects.hash(x1, getOperator());
    }
}

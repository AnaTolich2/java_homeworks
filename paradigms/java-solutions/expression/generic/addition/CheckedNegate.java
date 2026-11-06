package expression.generic.addition;

import expression.exceptions.NewSomeOperation;
import expression.exceptions.OverflowException;
import expression.generic.types.*;

import java.math.BigInteger;

public class CheckedNegate<T> extends CheckedUnaryOperation<T> {


    public CheckedNegate(GenericExpression<T> x1, GenericType<T> type) {
        super(x1, type);
    }
    @Override
    public int getPriority(){
        return 4;
    }
    @Override
    public T evaluate(T x, T y, T z) {
        return type.negate(x1.evaluate(x, y, z));
    }
    @Override
    public String toString() {
        return "-(" + x1 + ")";
    }

    @Override
    public String toMiniString() {

        if (x1.getPriority() >= 4) {
            return "- " + x1.toMiniString();
        } else {
            return "-(" + x1.toMiniString() + ")";
        }
    }

    @Override
    protected String getOperator() {
        return "- ";
    }
}

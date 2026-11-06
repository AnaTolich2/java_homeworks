package expression.generic.addition;

import expression.exceptions.NewSomeOperation;
import expression.generic.types.*;

import java.math.BigInteger;

public class Const<T> implements GenericExpression<T>{
    private final T c;
    private final GenericType<T> type;
    public Const(T c, GenericType<T> type) {
        this.c = c;
        this.type = type;
    }



    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean equals(Object expression) {
        if (this == expression) {
            return true;
        }
        if (expression == null || getClass() != expression.getClass()) {
            return false;
        }
        return this.c == ((Const<?>) expression).c;
    }

    @Override
    public int hashCode() {
        return c == null ? 0 : c.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(c);
    }

    @Override
    public String toMiniString() {
        return String.valueOf(c);
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return type.cast(c);
    }

}

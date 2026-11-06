package expression.generic.addition;

public interface GenericExpression<T> {
    T evaluate(T x, T y, T z);
    int getPriority();
    String toMiniString();
}

package expression.generic.types;

public interface GenericType<T> {
    T cast(Object value);
    T add(T x, T y);
    T subtract(T x, T y);
    T divide(T x, T y);
    T multiply(T x, T y);
    T negate(T x);
    T moreThan(T x, T y);
    T lessThan(T x, T y);
    T lessEqualThan(T x, T y);
    T moreEqualThan(T x, T y);
    T equal(T x, T y);
    T unequal(T x, T y);
    T parse(String in);
}
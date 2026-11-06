package expression.generic.types;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;


public class IntegerType implements GenericType<Integer>{
    @Override
    public Integer cast(Object value) {
        return ((Number) value).intValue();
    }
    @Override
    public Integer add(Integer x, Integer y) {
        if (x > 0 && y > 0 && x > Integer.MAX_VALUE - y) {
            throw new OverflowException();
        }
        if (x < 0 && y < 0 && x < Integer.MIN_VALUE - y) {
            throw new OverflowException();
        }
        return x + y;
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        if (x >= 0 && y <= 0 && x > Integer.MAX_VALUE + y){
            throw new OverflowException();
        }
        if (x <= 0 && y >= 0 && x < Integer.MIN_VALUE + y){
            throw new OverflowException();
        }
        return x - y;
    }

    @Override
    public Integer divide(Integer x, Integer y) {
        if (y == 0){
            throw new DivisionByZeroException();
        }
        if (x == Integer.MIN_VALUE && y == -1){
            throw new OverflowException();
        }
        return x / y;
    }
    @Override
    public Integer multiply(Integer x, Integer y){
        if ((x > 0 && y > 0 && x > Integer.MAX_VALUE / y) || (x < 0 && y < 0 && x < Integer.MAX_VALUE / y) ){
            throw new OverflowException();
        }
        if ((x < 0 && y > 0 && x < Integer.MIN_VALUE / y) || (x > 0 && y < -1 && x > Integer.MIN_VALUE / y) ){
            throw new OverflowException();
        }
        return x * y;
    }

    @Override
    public Integer negate(Integer x) {
        if (x == Integer.MIN_VALUE){
            throw new OverflowException();
        }
        return x * -1;
    }

    @Override
    public Integer moreThan(Integer x, Integer y) {
        if (x.compareTo(y) > 0){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer lessThan(Integer x, Integer y) {
        if (x.compareTo(y) < 0){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer lessEqualThan(Integer x, Integer y) {
        if (x.compareTo(y) <= 0){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer moreEqualThan(Integer x, Integer y) {
        if (x.compareTo(y) >= 0){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer equal(Integer x, Integer y) {
        if (x.compareTo(y) == 0){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer unequal(Integer x, Integer y) {
        if (x.compareTo(y) != 0){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer parse(String in) {
        return Integer.parseInt(in);
    }
}

package expression.generic.types;

import java.math.BigInteger;

public class BigIntegerType implements GenericType<BigInteger>{

    @Override
    public BigInteger cast(Object value) {
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        } else if (value instanceof Number) {
            return BigInteger.valueOf(((Number) value).longValue());
        } else {
            throw new IllegalArgumentException("Cannot cast to BigInteger: " + value);
        }
    }

    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger subtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger divide(BigInteger x, BigInteger y) {
        return x.divide(y);
    }

    @Override
    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger negate(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger moreThan(BigInteger x, BigInteger y) {
        if (x.compareTo(y) > 0){
            return BigInteger.valueOf(1);
        }
        return BigInteger.valueOf(0);
    }

    @Override
    public BigInteger lessThan(BigInteger x, BigInteger y) {
        if (x.compareTo(y) < 0){
            return BigInteger.valueOf(1);
        }
        return BigInteger.valueOf(0);
    }

    @Override
    public BigInteger lessEqualThan(BigInteger x, BigInteger y) {
        if (x.compareTo(y) <= 0){
            return BigInteger.valueOf(1);
        }
        return BigInteger.valueOf(0);
    }

    @Override
    public BigInteger moreEqualThan(BigInteger x, BigInteger y) {
        if (x.compareTo(y) >= 0){
            return BigInteger.valueOf(1);
        }
        return BigInteger.valueOf(0);
    }

    @Override
    public BigInteger equal(BigInteger x, BigInteger y) {
        if (x.compareTo(y) == 0){
            return BigInteger.valueOf(1);
        }
        return BigInteger.valueOf(0);
    }

    @Override
    public BigInteger unequal(BigInteger x, BigInteger y) {
        if (x.compareTo(y) != 0){
            return BigInteger.valueOf(1);
        }
        return BigInteger.valueOf(0);
    }

    @Override
    public BigInteger parse(String in) {
        return new BigInteger(in);
    }
}

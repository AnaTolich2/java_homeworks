package expression.generic.types;

public class UIntegerType implements GenericType<Integer>{
    @Override
    public Integer cast(Object value) {
        return ((Number) value).intValue();
    }
    @Override
    public Integer add(Integer x, Integer y) {
        return x + y;
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        return x - y;
    }

    @Override
    public Integer divide(Integer x, Integer y) {
        return x / y;
    }
    @Override
    public Integer multiply(Integer x, Integer y){
        return x * y;
    }

    @Override
    public Integer negate(Integer x) {
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

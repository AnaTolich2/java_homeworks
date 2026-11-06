package expression.generic.types;

public class LongType implements GenericType<Long>{
    @Override
    public Long cast(Object value) {
        return ((Number) value).longValue();
    }
    @Override
    public Long add(Long x, Long y) {
        return x + y;
    }

    @Override
    public Long subtract(Long x, Long y) {
        return x - y;
    }

    @Override
    public Long divide(Long x, Long y) {
        return x / y;
    }
    @Override
    public Long multiply(Long x, Long y){
        return x * y;
    }

    @Override
    public Long negate(Long x) {
        return x * -1;
    }

    @Override
    public Long moreThan(Long x, Long y) {
        if (x.compareTo(y) > 0){
            return 1L;
        }
        return 0L;
    }

    @Override
    public Long lessThan(Long x, Long y) {
        if (x.compareTo(y) < 0){
            return 1L;
        }
        return 0L;
    }

    @Override
    public Long lessEqualThan(Long x, Long y) {
        if (x.compareTo(y) <= 0){
            return 1L;
        }
        return 0L;
    }

    @Override
    public Long moreEqualThan(Long x, Long y) {
        if (x.compareTo(y) >= 0){
            return 1L;
        }
        return 0L;
    }

    @Override
    public Long equal(Long x, Long y) {
        if (x.compareTo(y) == 0){
            return 1L;
        }
        return 0L;
    }

    @Override
    public Long unequal(Long x, Long y) {
        if (x.compareTo(y) != 0){
            return 1L;
        }
        return 0L;
    }

    @Override
    public Long parse(String in) {
        return Long.parseLong(in);
    }
}

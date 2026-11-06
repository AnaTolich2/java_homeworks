package expression.generic.types;

public class DoubleType implements GenericType<Double>{
    @Override
    public Double cast(Object value) {
        return ((Number)value).doubleValue();
    }
    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double subtract(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double divide(Double x, Double y) {
        return x / y;
    }
    @Override
    public Double multiply(Double x, Double y){
        return x * y;
    }

    @Override
    public Double negate(Double x) {
        return x * -1.0;
    }

    @Override
    public Double moreThan(Double x, Double y) {
        if (x.compareTo(y) > 0){
            return 1.0;
        }
        return 0.0;
    }

    @Override
    public Double lessThan(Double x, Double y) {
        if (x.compareTo(y) < 0){
            return 1.0;
        }
        return 0.0;
    }

    @Override
    public Double lessEqualThan(Double x, Double y) {
        if (x.compareTo(y) <= 0){
            return 1.0;
        }
        return 0.0;
    }

    @Override
    public Double moreEqualThan(Double x, Double y) {
        if (x.compareTo(y) >= 0){
            return 1.0;
        }
        return 0.0;
    }

    @Override
    public Double equal(Double x, Double y) {
        if (x.compareTo(y) == 0){
            return 1.0;
        }
        return 0.0;
    }

    @Override
    public Double unequal(Double x, Double y) {
        if (x.compareTo(y) != 0){
            return 1.0;
        }
        return 0.0;
    }

    @Override
    public Double parse(String in) {
        return Double.parseDouble(in);
    }
}

package expression;

import java.util.Map;

public class Square extends UnaryOperation{
    public Square(SomeOperation x1) {
        super(x1);
    }
    @Override
    public int evaluate(int x) {
        return x1.evaluate(x) * x1.evaluate(x);
    }

    @Override
    public long evaluateL(Map<String, Long> variables) {
        return x1.evaluateL(variables) * x1.evaluateL(variables);
    }
    @Override
    public int getPriority(){
        return 5;
    }
    @Override
    public int evaluate(int x, int y, int z) {
        return x1.evaluate(x, y, z) * x1.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "(" + x1 + ")²";
    }

    @Override
    public String toMiniString() {
        if (x1.getPriority() >= 5) {
            return x1.toMiniString() + "²";
        } else {
            return "(" + x1.toMiniString() + ")²";
        }
    }
    @Override
    protected String getOperator() {
        return "²";
    }
}

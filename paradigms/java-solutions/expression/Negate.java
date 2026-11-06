package expression;

import java.util.Map;

public class Negate extends UnaryOperation{


    public Negate(SomeOperation x1) {
        super(x1);
    }

    @Override
    public int evaluate(int x) {
        return -1 * x1.evaluate(x);
    }

    @Override
    public int getPriority(){
        return 4;
    }
    @Override
    public int evaluate(int x, int y, int z) {
        return -1 * x1.evaluate(x, y, z);
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

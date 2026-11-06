package expression.exceptions;

public class CheckedNegate extends CheckedUnaryOperation {


    public CheckedNegate(NewSomeOperation x1) {
        super(x1);
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int temp = x1.evaluate(x, y, z);
        if (temp == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return -1 * x1.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "-(" + x1 + ")";
    }

    @Override
    public String toMiniString() {

        if (x1.getPriority() >= 5) {
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

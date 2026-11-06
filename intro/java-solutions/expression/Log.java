package expression;

import java.util.Map;

public class Log extends BinaryOperation{
    public Log(SomeOperation x1, SomeOperation x2) {
        super(x1, x2);
    }
    private long log(long x, long a){
        if (x < 0){
            return 0;
        }
        if (x == 0 && a > 0){
            return Integer.MIN_VALUE;
        }
        if (x == 1 && a > 0){
            return 0;
        }
        int res = 0;
        if (a > 1) {
            while (x / a > 0) {
                res += 1;
                x = x / a;
            }
        } else if (a > 0){
            return Integer.MAX_VALUE;
        }
        return res;
    }
    @Override
    protected String getOperator() {
        return "//";
    }

    @Override
    public int getPriority(){
        return 3;
    }
    @Override
    protected int subExpression(int x1, int x2) {
        return (int)log(x1, x2);
    }
    @Override
    protected long subExpression(long x1, long x2) {
        return log(x1, x2);
    }


    @Override
    protected boolean isAssociative() {
        return false;
    }
}

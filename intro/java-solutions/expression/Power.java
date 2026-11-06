package expression;

import java.util.Map;

public class Power extends BinaryOperation{
    public Power(SomeOperation x1, SomeOperation x2) {
        super(x1, x2);
    }
    private long power(long x, long p){
        p = (int) p;
        if ((x == 0 && p > 0) || x == 1){
            return x;
        }
        if (x == -1 && p > 0){
            if (p % 2 == 0){
                return 1;
            } else {
                return -1;
            }
        }
        int res = 1;
        while (p > 0){
            if (p % 2 == 1){
                res *= (int) x;
            }
            x *= x;
            p /= 2;
        }
        return res;
    }
    @Override
    public int getPriority(){
        return 3;
    }
    @Override
    protected String getOperator() {
        return "**";
    }
    @Override
    protected int subExpression(int x1, int x2) {
        return (int)power(x1 , x2);
    }

    @Override
    protected long subExpression(long x1, long x2) {
        return power(x1 , x2);
    }
    @Override
    public String toString(){
        return "("+x1.toString()+" ** "+x2.toString() +")";
    }


    @Override
    protected boolean isAssociative() {
        return false;
    }

}

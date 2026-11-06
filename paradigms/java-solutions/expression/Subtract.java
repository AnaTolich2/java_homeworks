package expression;

import java.util.Map;

public class Subtract extends BinaryOperation {
    public Subtract(SomeOperation x1, SomeOperation x2){
        super(x1, x2);
    }
    @Override
    protected int subExpression(int x1, int x2) {
        return x1 - x2;
    }

    @Override
    protected long subExpression(long x1, long x2) {
        return x1 - x2;
    }
    @Override
    public int getPriority(){
        return 1;
    }
    @Override
    public boolean equals(Object obj){
        return super.equals(obj);
    }


    @Override
    protected boolean isAssociative() {
        return false;
    }

    @Override
    protected String getOperator() {
        return "-";
    }

}

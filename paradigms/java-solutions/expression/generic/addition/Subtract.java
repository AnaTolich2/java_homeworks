package expression.generic.addition;

import expression.generic.types.*;

public class Subtract<T> extends BinaryOperation<T> {
    public Subtract(GenericExpression<T> x1, GenericExpression<T> x2, GenericType<T> type){
        super(x1, x2, type);
    }


    @Override
    protected T subExpression(T x1, T x2) {
        return type.subtract(x1, x2);
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

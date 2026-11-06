package expression.generic.addition;

import expression.generic.types.*;

public class Divide<T> extends BinaryOperation<T> {
    public Divide(GenericExpression<T> x1, GenericExpression<T> x2, GenericType<T> type){
        super(x1, x2, type);
    }

    @Override
    protected T subExpression(T x1, T x2) {
        return type.divide(x1, x2);
    }
    @Override
    public int getPriority(){
        return 2;
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
        return "/";
    }

}

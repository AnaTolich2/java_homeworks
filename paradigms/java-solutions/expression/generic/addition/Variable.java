package expression.generic.addition;


import expression.exceptions.NewSomeOperation;
import expression.exceptions.OverflowException;
import expression.exceptions.ParsingException;
import expression.generic.types.BigIntegerType;
import expression.generic.types.DoubleType;
import expression.generic.types.GenericType;
import expression.generic.types.IntegerType;

import java.math.BigInteger;

public class Variable<T> implements GenericExpression<T> {
    String view;
    private final GenericType<T> type;
    public Variable(String view, GenericType<T> type){
        char end = view.charAt(view.length() - 1);
        if (end != 'x' && end != 'y' && end != 'z'){
            throw new ParsingException("Unsupported variable: " + view);
        }
        this.view = view;
        this.type = type;
    }
    @Override
    public boolean equals(Object expression) {
        if (this == expression){ return true;}
        if (expression == null || getClass() != expression.getClass()){ return false;}
        Variable n = (Variable) expression;
        return this.view.equals(n.view);
    }
    @Override
    public int hashCode() {
        return view.hashCode();
    }

    @Override
    public String toString(){
        return view;
    }


    @Override
    public int getPriority(){
        return 5;
    }
    @Override
    public String toMiniString() {
        return view;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        T temp;
         switch (view.charAt(view.length() - 1)){
            case 'x' -> temp = x;
            case 'y' -> temp = y;
            case 'z' -> temp = z;
            default ->  throw new ParsingException("Unsupported variable: " + view);
        }
        return temp;
    }

}

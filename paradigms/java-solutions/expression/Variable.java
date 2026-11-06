package expression;

import expression.exceptions.NewSomeOperation;

import java.util.Map;

public class Variable extends SomeOperation {
    String view;
    public Variable(String view){
        this.view = view;
    }
    public int evaluate(int x) {
        return x;
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
    public int evaluate(int x, int y, int z) {
        return switch (view.charAt(view.length() - 1)){
            case 'x' -> x;
            case 'y' -> y;
            case 'z' -> z;
            default -> 0;
        };
    }
}

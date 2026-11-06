package expression.exceptions;


public class Variable extends NewSomeOperation {
    String view;

    public Variable(String view) {
        char end = view.charAt(view.length() - 1);
        if (end != 'x' && end != 'y' && end != 'z') {
            throw new ParsingException("Unsupported variable: " + view);
        }
        this.view = view;
    }

    public int evaluate(int x) {
        return x;
    }

    @Override
    public boolean equals(Object expression) {
        if (this == expression) {
            return true;
        }
        if (expression == null || getClass() != expression.getClass()) {
            return false;
        }
        Variable n = (Variable) expression;
        return this.view.equals(n.view);
    }

    @Override
    public int hashCode() {
        return view.hashCode();
    }

    @Override
    public String toString() {
        return view;
    }

    @Override
    public int getPriority() {
        return 6;
    }

    @Override
    public String toMiniString() {
        return view;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (view.charAt(view.length() - 1)) {
            case 'x' -> x;
            case 'y' -> y;
            case 'z' -> z;
            default -> 0;
        };
    }
}

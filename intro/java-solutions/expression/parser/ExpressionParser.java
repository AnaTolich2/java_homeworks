package expression.parser;

import expression.*;

import java.util.Objects;

public class ExpressionParser implements TripleParser {
    private String input;
    private int pointer;
    public TripleExpression parse(final String expression) {
        input = expression;
        pointer = 0;
        return expression();
    }
    private SomeOperation expression() {
        SomeOperation result = term();
        while (true) {
            if (current() == '+') {
                pointer++;
                result = new Add(result, term());
            } else if (current() == '-') {
                pointer++;
                result = new Subtract(result, term());
            } else {
                return result;
            }
        }
    }

    private SomeOperation term() {
        SomeOperation result = preFactor();
        while (true) {
            if (current() == '*') {
                pointer++;
                result = new Multiply(result, preFactor());
            } else if (current() == '/') {
                pointer++;
                result = new Divide(result, preFactor());
            } else {
                return result;
            }
        }
    }

    private SomeOperation preFactor() {
        SomeOperation result = factor();
        skipWS();
        while (true) {
            if (input.startsWith("**", pointer)) {
                pointer += 2;
                result = new Power(result, factor());
            } else if (input.startsWith("//", pointer)) {
                pointer += 2;
                result = new Log(result, factor());
            } else {
                return result;
            }
            skipWS();
        }
    }

    private SomeOperation factor() {
        SomeOperation result;
        if (current() =='-') {
            pointer++;
            if (Character.isDigit(input.charAt(pointer))) {
                result = number(true);
            } else {
                result = new Negate(factor());
            }
        } else if (current() == '(') {
            pointer++;
            result = expression();
            if (current() == ')') {
                pointer++;
            } else {
                throw new IllegalArgumentException("Expected closing parenthesis at position " + pointer);
            }
        } else if (Character.isDigit(current())) {
            result = number(false);
        } else if (Character.isLetter(current())) {
            result = variable();
        } else {
            throw new IllegalArgumentException("Unexpected character: " + current());
        }
        while (current() == '²') {
            result = new Square(result);
            pointer++;
        }
        return result;
    }

    private SomeOperation number(final boolean isNegative) {
        final int start = pointer;
        while (pointer < input.length() && Character.isDigit(input.charAt(pointer))) {
            pointer++;
        }
        final int value;

        if (isNegative) {
            value = Integer.parseInt("-" + input.substring(start, pointer));
        } else {
            value = Integer.parseInt(input.substring(start, pointer));
        }
        return new Const(value);
    }

    private SomeOperation variable() {
        final int start = pointer;
        pointer++;
        while (pointer < input.length() &&(Character.isDigit(input.charAt(pointer)) || Character.isLetter(input.charAt(pointer)))) {
            pointer++;
        }
        final String name = input.substring(start, pointer);
        return new Variable(name);
    }

    private char current() {
        skipWS();
        if (pointer < input.length()) {
            return input.charAt(pointer);
        }
        return 0;
    }

    private void skipWS() {
        while (pointer < input.length() && Character.isWhitespace(input.charAt(pointer))) {
            pointer++;
        }
    }
}

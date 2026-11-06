package expression.generic.addition;

import expression.exceptions.*;
import expression.generic.types.GenericType;

public class ExpressionParser<T> {
    private String input;
    private int pointer;
    private GenericType<T> type;
    public GenericExpression<T> parse(final String expression, final GenericType<T> type) {
        input = expression;
        this.type = type;
        pointer = 0;
        GenericExpression<T> temp = comparasion1();
        if (pointer < expression.length()){
            throw new ParsingException("Unexpected closing parenthesis");
        }
        return temp;
    }
    private GenericExpression<T> comparasion1(){
        GenericExpression<T> result = comparasion2();
        while (true) {
            if (current() == '=') {
                pointer++;
                if (current() == '=') {
                    pointer++;
                    result = new Equal<T>(result, comparasion2(), type);
                } else {
                    throw new ParsingException("Unexpected character: " + current());
                }
            } else if (current() == '!') {
                pointer++;
                if (current() == '=') {
                    pointer++;
                    result = new Unequal<T>(result, comparasion2(), type);
                } else {
                    throw new ParsingException("Unexpected character: " + current());
                }
            } else {
                return result;
            }
        }
    }
    private GenericExpression<T> comparasion2(){
        GenericExpression<T> result = expression();
        while (true) {
            if (current() == '<') {
                pointer++;
                if (input.charAt(pointer) == '=') {
                    pointer++;
                    result = new LessEqualThan<T>(result, expression(), type);
                } else if (Character.isWhitespace(input.charAt(pointer)) ||
                        input.charAt(pointer) == '-' ||
                        Character.getType(input.charAt(pointer)) == Character.getType('(')){
                    result = new LessThan<T>(result, expression(), type);
                } else {
                    throw new ParsingException("Unexpected character: " + current());
                }
            } else if (current() == '>') {
                pointer++;
                if (input.charAt(pointer) == '=') {
                    pointer++;
                    result = new MoreEqualThan<T>(result, expression(), type);
                } else if (Character.isWhitespace(input.charAt(pointer)) ||
                        input.charAt(pointer) == '-' ||
                        Character.getType(input.charAt(pointer)) == Character.getType('(')){
                    result = new MoreThan<T>(result, expression(), type);
                } else {
                    throw new ParsingException("Unexpected character: " + current());
                }
            } else {
                return result;
            }
        }
    }
    private GenericExpression<T> expression() {
        GenericExpression<T> result = term();
        while (true) {
            if (current() == '+') {
                pointer++;
                result = new Add<T>(result, term(), type);
            } else if (current() == '-') {
                pointer++;
                result = new Subtract<T>(result, term(), type);
            } else {
                return result;
            }
        }
    }

    private GenericExpression<T> term() {
        GenericExpression<T> result = factor();
        while (true) {
            if (current() == '*') {
                pointer++;
                result = new Multiply<T>(result, factor(), type);
            } else if (current() == '/') {
                pointer++;
                result = new Divide<T>(result, factor(), type);
            } else {
                return result;
            }
        }
    }


    private GenericExpression<T> factor() {
        GenericExpression<T> result;
        if (current() =='-') {
            pointer++;
            if (Character.isDigit(input.charAt(pointer))) {
                result = number(true);
            } else {
                result = new CheckedNegate<T>(factor(), type);
            }
        } else if (current() == '(') {
            pointer++;
            result = comparasion1();
            if (current() == ')') {
                pointer++;
            } else {
                throw new ParsingException("Expected closing parenthesis \")\" at position " + pointer);
            }
        } else if (current() == '['){
            pointer++;
            result = comparasion1();
            if (current() == ']') {
                pointer++;
            } else {
                throw new ParsingException("Expected closing parenthesis \"]\" at position " + pointer);
            }
        } else if (current() == '{'){
            pointer++;
            result = comparasion1();
            if (current() == '}') {
                pointer++;
            } else {
                throw new ParsingException("Expected closing parenthesis \"}\" at position " + pointer);
            }
        } else if (Character.isDigit(current())) {
            result = number(false);
        } else if (Character.isLetter(current())) {
            result = variable();
        } else {
            throw new ParsingException("Unexpected character: " + current());
        }
        return result;
    }

    private GenericExpression<T> number(final boolean isNegative) {
        final int start = pointer;
        while (pointer < input.length() && Character.isDigit(input.charAt(pointer))) {
            pointer++;
        }
        final T value;

        if (isNegative) {
            value = type.parse("-" + input.substring(start, pointer));
        } else {
            value = type.parse(input.substring(start, pointer));
        }
        return new Const<T>(value, type);
    }

    private GenericExpression<T> variable() {
        final int start = pointer;
        pointer++;
        while (pointer < input.length() &&(Character.isDigit(input.charAt(pointer)) || Character.isLetter(input.charAt(pointer)))) {
            pointer++;
        }
        final String name = input.substring(start, pointer);
        return new Variable<T>(name, type);
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

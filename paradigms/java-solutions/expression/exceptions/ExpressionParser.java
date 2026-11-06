package expression.exceptions;

import expression.*;

public class ExpressionParser implements TripleParser {
    private String input;
    private int pointer;

    public TripleExpression parse(final String expression) {
        input = expression;
        pointer = 0;
        NewSomeOperation temp = comparasion1();
        if (pointer < expression.length()) {
            throw new ParsingException("Unexpected closing parenthesis");
        }
        return temp;
    }

    private NewSomeOperation comparasion1() {
        NewSomeOperation result = comparasion2();
        while (true) {
            if (current() == '=') {
                pointer++;
                if (current() == '=') {
                    pointer++;
                    result = new Equal(result, comparasion2());
                } else {
                    throw new ParsingException("Unexpected character: " + current());
                }
            } else if (current() == '!') {
                pointer++;
                if (current() == '=') {
                    pointer++;
                    result = new Unequal(result, comparasion2());
                } else {
                    throw new ParsingException("Unexpected character: " + current());
                }
            } else {
                return result;
            }
        }
    }

    private NewSomeOperation comparasion2() {
        NewSomeOperation result = expression();
        while (true) {
            if (current() == '<') {
                pointer++;
                if (input.charAt(pointer) == '=') {
                    pointer++;
                    result = new LessEqualThan(result, expression());
                } else if (Character.isWhitespace(input.charAt(pointer)) ||
                        input.charAt(pointer) == '-' ||
                        Character.getType(input.charAt(pointer)) == Character.getType('(')) {
                    result = new LessThan(result, expression());
                } else {
                    throw new ParsingException("Unexpected character: " + current());
                }
            } else if (current() == '>') {
                pointer++;
                if (input.charAt(pointer) == '=') {
                    pointer++;
                    result = new MoreEqualThan(result, expression());
                } else if (Character.isWhitespace(input.charAt(pointer)) ||
                        input.charAt(pointer) == '-' ||
                        Character.getType(input.charAt(pointer)) == Character.getType('(')) {
                    result = new MoreThan(result, expression());
                } else {
                    throw new ParsingException("Unexpected character: " + current());
                }
            } else {
                return result;
            }
        }
    }

    private NewSomeOperation expression() {
        NewSomeOperation result = term();
        while (true) {
            if (current() == '+') {
                pointer++;
                result = new CheckedAdd(result, term());
            } else if (current() == '-') {
                pointer++;
                result = new CheckedSubtract(result, term());
            } else {
                return result;
            }
        }
    }

    private NewSomeOperation term() {
        NewSomeOperation result = factor();
        while (true) {
            if (current() == '*') {
                pointer++;
                result = new CheckedMultiply(result, factor());
            } else if (current() == '/') {
                pointer++;
                result = new CheckedDivide(result, factor());
            } else {
                return result;
            }
        }
    }

    private NewSomeOperation factor() {
        NewSomeOperation result;
        if (current() == '-') {
            pointer++;
            if (Character.isDigit(input.charAt(pointer))) {
                result = number(true);
            } else {
                result = new CheckedNegate(factor());
            }
        } else if (current() == '(') {
            pointer++;
            result = comparasion1();
            if (current() == ')') {
                pointer++;
            } else {
                throw new ParsingException("Expected closing parenthesis \")\" at position " + pointer);
            }
        } else if (current() == '[') {
            pointer++;
            result = comparasion1();
            if (current() == ']') {
                pointer++;
            } else {
                throw new ParsingException("Expected closing parenthesis \"]\" at position " + pointer);
            }
        } else if (current() == '{') {
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

    private NewSomeOperation number(final boolean isNegative) {
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

    private NewSomeOperation variable() {
        final int start = pointer;
        pointer++;
        while (pointer < input.length() && (Character.isDigit(input.charAt(pointer)) || Character.isLetter(input.charAt(pointer)))) {
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

package expression;
import expression.parser.ExpressionParser;


public class Main {
    public static void main(String[] args) {
        TripleExpression exp  = (new ExpressionParser()).parse("(0)²");
        System.out.println(exp);
        System.out.println(exp.toMiniString());
    }
}
//(-(-908556838))²
//(- -908556838)²
package expression;
import expression.exceptions.ExpressionParser;


public class Main {
    public static void main(String[] args) {
        TripleExpression exp  = (new ExpressionParser()).parse("[{{1008201157 < z} <= {z / y}} < {[825873055 != x] + {z < y}}]");
        System.out.println(exp.toString());
    }
    //x=-8, y=-6, z=0
}
//(((1008201157 < z) <= (z / y)) < ((825873055 != x) + (z < y)))
//(((0) <= (-0)) < ((1) + (0)))
//      0  <  1 = 1
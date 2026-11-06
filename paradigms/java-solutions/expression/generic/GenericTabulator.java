package expression.generic;
import expression.exceptions.DivisionByZeroException;
import expression.exceptions.EvaluationException;
import expression.generic.addition.ExpressionParser;
import expression.generic.addition.GenericExpression;
import expression.generic.types.*;

public class GenericTabulator implements Tabulator {

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        GenericType<?> type = switch (mode) {
            case "i" -> new IntegerType();
            case "d" -> new DoubleType();
            case "bi" -> new BigIntegerType();
            case "u" -> new UIntegerType();
            case "l" -> new LongType();
            default -> throw new IllegalArgumentException("Unsupported mode: " + mode);
        };

        return evaluateExpression(type, expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] evaluateExpression(GenericType<T> type, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        ExpressionParser<T> parser= new ExpressionParser<>();
        GenericExpression<T> function = parser.parse(expression, type);
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        result[i - x1][j - y1][k - z1] = function.evaluate(type.cast(i),
                                type.cast(j),
                                type.cast(k));
                    }catch (EvaluationException | ArithmeticException | DivisionByZeroException e){
                        result[i - x1][j - y1][k - z1] = null;
                    }

                }
            }
        }
        return result;
    }

}
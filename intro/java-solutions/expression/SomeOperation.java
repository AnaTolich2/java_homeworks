package expression;

public abstract class SomeOperation implements Expression, TripleExpression, LongMapExpression{
    public abstract int getPriority();
}

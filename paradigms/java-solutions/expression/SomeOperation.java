package expression;

import expression.exceptions.NewSomeOperation;

public abstract class SomeOperation extends NewSomeOperation implements Expression{
    public abstract int getPriority();
}

package expression;

public interface Elements extends Expression, TripleExpression, BigIntegerExpression {

    int priority();
}

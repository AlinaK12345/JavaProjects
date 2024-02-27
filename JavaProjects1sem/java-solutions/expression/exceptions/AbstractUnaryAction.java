package expression.exceptions;

import expression.Elements;

import java.math.BigInteger;
import java.util.Objects;

public abstract class AbstractUnaryAction implements Elements {
    Elements exp1;
    public AbstractUnaryAction(Elements elem){
        this.exp1 = elem;
    }

    abstract protected String action();

    @Override
    public String toString() {
        return action() + toParenthesis( exp1.toString());
    }

    @Override
    public String toMiniString() {
        if (exp1.priority() != -1) {
            return action() + toParenthesis(exp1.toMiniString());
        }
        return action() + " " + exp1.toMiniString();
    }

    @Override
    public int evaluate(int x) {
        int v1 = exp1.evaluate(x);
        return makeAction(v1);
    }

    public BigInteger evaluate(BigInteger x) {
        BigInteger v1 = exp1.evaluate(x);
        return makeActionBig(v1);
    }

    protected abstract int makeAction(int v1) ;

    protected abstract BigInteger makeActionBig(BigInteger v1);

    public int evaluate(int x, int y, int z) {
        int v1 = exp1.evaluate(x, y, z);
        return makeAction(v1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractUnaryAction that) {
            return this.exp1.equals(that.exp1) && this.action().equals(that.action());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp1, this.action());
    }

    private String toParenthesis(String s) {
        return "(" + s + ")";
    }

    public int priority(){
        return -1;
    }
    public int getBit(int a, int i){
        return (a>>i) & 1;
    }

}




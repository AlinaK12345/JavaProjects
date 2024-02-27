package expression.exceptions;

import expression.Elements;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public abstract class AbstractBinnaryAction implements Elements {
    private final Elements exp1;
    private final Elements exp2;

    public AbstractBinnaryAction(Elements exp1, Elements exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    abstract protected String action();

    @Override
    public String toString() {
        return toParenthesis(exp1.toString() + " " + action() + " " + exp2.toString());
    }

    @Override
    public String toMiniString() {
        StringBuilder s = new StringBuilder();
        String s1 = exp1.toMiniString();
        String s2 = exp2.toMiniString();
        if (this.priority() >= 7){
            if (exp2.priority() >= 7 && exp2.priority() != this.priority()){
                s2 = toParenthesis(s2);
            }
        }else if (this.priority() == 1 || this.priority() == 0) {
            if (exp1.priority() >= 2) {
                s1 = toParenthesis(s1);
            }
            if (!(exp2.priority() == 0 && this.priority() == 0) && (exp2.priority() >= 1 || this.priority() == 1 && exp2.priority() >= 0)) {
                s2 = toParenthesis(s2);
            }
        } else if (this.action().equals("-") && exp2.priority() >= 2) {
            s2 = toParenthesis(s2);
            if (this.priority() < exp1.priority()) {
                s1 = toParenthesis(s1);
            }
        } else {
            if (this.priority() < exp1.priority()) {
                s1 = toParenthesis(s1);
            }
            if (this.priority() < exp2.priority()) {
                s2 = toParenthesis(s2);
            }
        }

        s.append(s1).append(" ").append(action()).append(" ").append(s2);
        return s.toString();

    }

    @Override
    public int evaluate(int x) {
        int v1 = exp1.evaluate(x);
        int v2 = exp2.evaluate(x);
        return makeAction(v1, v2);
    }

    public BigInteger evaluate(BigInteger x) {
        BigInteger v1 = exp1.evaluate(x);
        BigInteger v2 = exp2.evaluate(x);
        return makeActionBig(v1, v2);
    }

    protected abstract int makeAction(int v1, int v2) ;

    protected abstract BigInteger makeActionBig(BigInteger v1, BigInteger v2);

    public int evaluate(int x, int y, int z) {
        int v1 = exp1.evaluate(x, y, z);
        int v2 = exp2.evaluate(x, y, z);
        return makeAction(v1, v2);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractBinnaryAction that) {
            return this.exp1.equals(that.exp1) && this.exp2.equals(that.exp2) && this.action().equals(that.action());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp1, exp2, this.action());
    }

    private String toParenthesis(String s) {
        return "(" + s + ")";
    }

    public abstract int priority();

}

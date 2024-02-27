package expression.exceptions;

import expression.Elements;

import java.math.BigInteger;

public class Max extends AbstractBinnaryAction{
    public Max(Elements exp1, Elements exp2) {
        super(exp1, exp2);
    }

    @Override
    protected String action() {
        return "max";
    }

    protected int makeAction(int v1, int v2) {
        BigInteger v3 = BigInteger.valueOf(v1);
        BigInteger v4 = BigInteger.valueOf(v2);
        if (v3.compareTo(BigInteger.valueOf(Integer.MAX_VALUE))>0 || v3.compareTo(BigInteger.valueOf(Integer.MIN_VALUE))<0){
            throw new RuntimeException("overflow");
        }
        if (v4.compareTo(BigInteger.valueOf(Integer.MAX_VALUE))>0 || v4.compareTo(BigInteger.valueOf(Integer.MIN_VALUE))<0){
            throw new RuntimeException("overflow");
        }
        if (v1 > v2) {
            return v1;
        }
        return v2;
    }

    protected BigInteger makeActionBig(BigInteger v1, BigInteger v2) {
        if (v1.compareTo(v2)>0) {
            return v1;
        }
        return v2;
    }

    public int priority() {
        return 8;
    }

}

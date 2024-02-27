package expression;

import java.math.BigInteger;

public class Minus extends AbstractUnaryAction{
    public Minus(Elements exp1) {
        super(exp1);
    }

    @Override
    protected String action() {
        return "-";
    }

    protected int makeAction(int v1) {
        return -1*v1;
    }

    protected BigInteger makeActionBig(BigInteger v1) {
        return v1.multiply(BigInteger.valueOf(-1));
    }

//    public int priority() {
//        return -1;
//    }


}

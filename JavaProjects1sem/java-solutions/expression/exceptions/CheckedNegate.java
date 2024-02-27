package expression.exceptions;

import expression.Elements;
import java.math.BigInteger;

public class CheckedNegate extends AbstractUnaryAction {
    public CheckedNegate(Elements exp1) {
        super(exp1);
    }

    @Override
    protected String action() {
        return "-";
    }

    protected int makeAction(int v1) {
        BigInteger v3 = BigInteger.valueOf(v1);
        BigInteger v4 = BigInteger.valueOf(-1);
        if (v3.multiply(v4).compareTo(BigInteger.valueOf(Integer.MAX_VALUE))>0 || v3.multiply(v4).compareTo(BigInteger.valueOf(Integer.MIN_VALUE))<0){
            throw new RuntimeException("overflow");
        }
        return -1*v1;
    }

    protected BigInteger makeActionBig(BigInteger v1) {
        return v1.multiply(BigInteger.valueOf(-1));
    }

//    public int priority() {
//        return -1;
//    }


}

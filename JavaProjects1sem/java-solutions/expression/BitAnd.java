package expression;

import java.math.BigInteger;

public class BitAnd extends AbstractBinnaryAction{
    public BitAnd(Elements exp1, Elements exp2) {
        super(exp1, exp2);
    }

    @Override
    protected String action() {
        return "&";
    }

    protected int makeAction(int v1, int v2) {
        return v1 & v2;
    }

    protected BigInteger makeActionBig(BigInteger v1, BigInteger v2) {
        throw new UnsupportedOperationException("Unsupported BigInteger");
    }

    public int priority() {
        return 3;
    }
}

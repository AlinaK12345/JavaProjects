package expression.exceptions;

import expression.Elements;
import java.math.BigInteger;

public class UnaryL1 extends AbstractUnaryAction {
    public UnaryL1(Elements exp1) {
        super(exp1);
    }

    @Override
    protected String action() {
        return "l1";
    }


    protected int makeAction(int v1) {
        int ans = 0;
        for (int i = 31; i >= 0; i--){
            if (getBit(v1, i) == 0){
                return ans;
            }
            ans++;
        }
        return ans;
    }

    protected BigInteger makeActionBig(BigInteger v1) {
        throw new UnsupportedOperationException("Unsupported BigInteger");
    }

//    public int priority() {
//        return -1;
//    }
}

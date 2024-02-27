package expression;

import java.math.BigInteger;
import java.util.Objects;

public class Const implements Elements {
    public final BigInteger value;

    public Const(int x) {
        value = BigInteger.valueOf(x);
    }

    public Const(BigInteger x) {
        value = x;
    }

    public int evaluate(int variable) {
        return value.intValue();
    }

    public BigInteger evaluate(BigInteger x) {/////
        return value;
    } ////&&&&777

    public int evaluate(int x, int y, int z) {
        return value.intValue();
    }

    public int priority() {
        return -1;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public String toMiniString() {
        return value.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            return this.value.equals(((Const) obj).value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }


}

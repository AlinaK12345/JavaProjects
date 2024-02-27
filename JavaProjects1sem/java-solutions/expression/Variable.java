package expression;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Objects;

public class Variable implements Elements {
    public final String arg;

    public Variable(String x) {
        arg = x;
    }

    public int evaluate(int x) {
        return x;
    }

    public int evaluate(int x, int y, int z) { /// ???
        try {
            switch (arg) {
                case ("x"):
                    return x;
                case ("y"):
                    return y;
                case ("z"):
                    return z;
                default:
                    throw new IOException("Not correct value of Variable");
            }
        } catch (IOException e) {
            System.err.println("Not correct name of Variable");
            return 0;
        }
    }

    public BigInteger evaluate(BigInteger x) {
        return x;
    }

    public int priority() {
        return -1;
    }

    @Override
    public String toString() {
        return arg;
    }

    public String toMiniString() {
        return arg;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            return this.arg.equals(((Variable) obj).arg);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(arg);
    }

}

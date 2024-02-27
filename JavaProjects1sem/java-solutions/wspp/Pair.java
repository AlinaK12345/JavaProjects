package wspp;

public class Pair {
    int count;
    IntList index;

    public Pair() {
        count = 0;
        index = new IntList();
    }

    public void increase() {
        count++;
    }

    public void add(int x) {
        index.add(x);
    }

    public int lastInd() {
        return index.last();
    }

    public String toString() {
        return count + " " + index;
    }
}

package game;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class CircleBoard extends TicTacToeBoard {

    public CircleBoard(int n, int k) {
        super(n, n, k);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!isInCircle(i, j)) {
                    cells[i][j] = Cell.B;
                }
            }
        }
    }

    private boolean isInCircle(int i, int j) {
        int r = n / 2;
        int delta = 0;

        if (n % 2 != 0) {
            delta = abs(i - r) * abs(i - r) + abs(j - r) * abs(j - r);
        } else {
            r--;
            int r1 = r;
            int r2 = r + 1;
            int a = min(abs(i - r1), abs(i - r2));
            int b = min(abs(j - r1), abs(j - r2));
            delta = a * a + b * b;

        }
        return delta <= r * r;
    }

}

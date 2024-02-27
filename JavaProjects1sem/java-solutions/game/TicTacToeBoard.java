package game;

import java.util.Arrays;
import java.util.Map;


public class TicTacToeBoard implements Board {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.B, ' '
    );

    protected final Cell[][] cells;
    protected final int n;
    protected final int m;
    private final int k;
    private Cell turn;
    private int notEmpty = 0;
    private final Position boardPosition;
    private boolean hasBigLine = false;


    public TicTacToeBoard(int n, int m, int k) {
        this.cells = new Cell[n][m]; //create new map size <n*m, >
        this.n = n;
        this.m = m;
        this.k = k;
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        this.boardPosition = new BoardPosition(this);
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    @Override
    public Position getPosition() {
        return boardPosition;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    private int inLine(int r1, int r2, int currentRow, int currentColumn) {
        int u = currentRow;
        int v = currentColumn;
        int line = 0;
        int part1 = -1;
        //:NOTE: copy paste
        while (u >= 0 && u < n && v >= 0 && v < m) {
            if (cells[u][v] == turn) {
                line++;
                u += r1;
                v += r2;
            } else {
                break;
            }

        }
        if (line < 4) {
            part1 = line - 1;
        }

        line--;
        u = currentRow;
        v = currentColumn;
        r1 *= -1;
        r2 *= -1;
        while (u >= 0 && u < n && v >= 0 && v < m) {
            if (cells[u][v] == turn) {
                line++;
                u += r1;
                v += r2;
            } else {
                break;
            }
        }
        int part2 = line - part1 - 1;
        if (part1 >= 0 && part2 < 4 && line >= 4) {
            hasBigLine = true;
        }
        return line;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        hasBigLine = false;

        int currentRow = move.getRow();
        int currentColumn = move.getColumn();
        cells[currentRow][currentColumn] = move.getValue();

        notEmpty++;


        int inRow = 0;
        int inColumn = 0;
        int inDiag1 = 0;
        int inDiag2 = 0;

        inColumn = inLine(1, 0, currentRow, currentColumn);
        inRow = inLine(0, 1, currentRow, currentColumn);
        inDiag1 = inLine(1, 1, currentRow, currentColumn);
        inDiag2 = inLine(-1, 1, currentRow, currentColumn);


        if (inDiag1 == k || inDiag2 == k || inRow == k || inColumn == k) {
            return Result.WIN;
        }
        if (notEmpty == m * n) {
            return Result.DRAW;
        }

        if (hasBigLine) {
            turn = turn == Cell.X ? Cell.O : Cell.X;

        }
        turn = turn == Cell.X ? Cell.O : Cell.X;


        return Result.UNKNOWN;
    }

    public boolean hasNewBigLine() {
        return hasBigLine;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < n
                && 0 <= move.getColumn() && move.getColumn() < m
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("  ");
        for (int i = 0; i < m; i++) {
            sb.append(i + 1);
            sb.append(" ");
        }

        for (int r = 0; r < n; r++) {
            //:NOTE: non cross-platform line separator
            sb.append("\n");
            sb.append(r + 1);
            sb.append(" ");
            for (int c = 0; c < m; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));

                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public void clear() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (cells[i][j] == Cell.O || cells[i][j] == Cell.X) {
                    cells[i][j] = Cell.E;
                }
            }
        }
        notEmpty = 0;
        turn = Cell.X;
    }
}

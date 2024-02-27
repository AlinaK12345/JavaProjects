package game;


public interface Board {
    Position getPosition();

    Cell getCell();

    Cell getCell(final int r, final int c);

    Result makeMove(Move move);

    boolean isValid(Move move);

    int getN();

    int getM();

    boolean hasNewBigLine();

    void clear();
}

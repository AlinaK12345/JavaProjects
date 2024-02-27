package game;

public class LosePlayer implements Player {
    @Override
    public Move move(final Position position, final Cell cell) {
        final Move move = new Move(-1, -1, cell);
        return move;
    }
}

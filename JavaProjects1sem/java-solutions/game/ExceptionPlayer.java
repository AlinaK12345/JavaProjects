package game;

public class ExceptionPlayer implements Player {
    @Override
    public Move move(Position position, Cell cell) {
        throw new RuntimeException();
    }
}

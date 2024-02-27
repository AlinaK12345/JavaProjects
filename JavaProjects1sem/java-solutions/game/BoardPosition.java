package game;

public class BoardPosition implements Position{
    private Board board;

    public BoardPosition(Board board){
        this.board = board;
    }
    @Override
    public boolean isValid(Move move) {
        return board.isValid(move);
    }

    @Override
    public Cell getCell(int r, int c) {
        return board.getCell(r, c);
    }
    @Override
    public String toString(){
        return board.toString();
    }
    public int getN(){
        return board.getN();
    }
    public int getM(){
        return board.getM();
    }
}

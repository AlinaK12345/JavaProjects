package game;

import java.io.PrintStream;
import java.util.Scanner;


public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            int a = 0;
            int b = 0;
            boolean error = false;
            if (!in.hasNext()){
                throw new IllegalStateException("User close input");
            }
            if (in.hasNextInt()){
                a = in.nextInt();
            }
            else{
                error = true;
                in.next();
            }

            if (!in.hasNext()){
                throw new IllegalStateException("User close input");
            }
            if (in.hasNextInt()){
                b = in.nextInt();
            }else{
                in.next();
            }

            if (error){
                out.println("Invalid input: Not numbers");
                continue;
            }
            final Move move = new Move(a - 1, b - 1, cell);
            if (position.isValid(move)) {
                return move;
            }
            final int row = move.getRow();
            final int column = move.getColumn();
            out.println("Move " + move + " is invalid");
        }
    }
}

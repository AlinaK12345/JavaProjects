package game;

import java.util.Scanner;


public class Main {

    static int isCorrect(Scanner sc) throws IllegalStateException {
        while (true) {
            if (!sc.hasNext()) {
                throw new IllegalStateException("User close input");
            }
            if (sc.hasNextInt()) {
                return sc.nextInt();
            }
            sc.next();
            System.out.println("Incorrect input, please enter again: ");
        }
    }

    static int correctShape(Scanner sc, int a1, int a2) {
        int number = -1;
        while (number < a1 || number > a2) {
            if (!sc.hasNext()) {
                throw new IllegalStateException("User close input");
            }
            if (sc.hasNextInt()) {
                number = sc.nextInt();
            } else {
                sc.next();
                System.out.printf("Incorrect input (not in [%d, %d]) please enter again: ", a1, a2);
            }
        }
        return number;
    }



    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Hello! If you want m*n board, enter 1 \nIf you want circle board, enter 0");
            try {

                int shape = correctShape(sc, 0, 1);


                Board board;

                if (shape == 1) {
                    System.out.print("n, m, k : ");
                    int n = correctShape(sc, 0, 100_000);
                    int m = correctShape(sc, 0, 100_000);
                    int k = correctShape(sc, 0, 100_000);
                    board = new TicTacToeBoard(n, m, k);
                } else {
                    System.out.print("diameter, k : ");
                    int d = correctShape(sc, 0, 100_000);
                    int k = correctShape(sc, 0, 100_000);
                    board = new CircleBoard(d, k);
                }

                System.out.print("Play tourney? no - 0 || yes - 1 : ");
                int tour = correctShape(sc, 0, 1);
                if (tour == 0) {
                    final Game game = new Game(true, new RandomPlayer(), new HumanPlayer());
                    int result;
                    result = game.play(board);
                    if (result == 0) {
                        System.out.println("Draw");
                    } else {
                        System.out.println("Game result: " + result);
                    }

                } else {
                    System.out.print("How many players? : ");
                    int countAll = isCorrect(sc);
                    System.out.print("How many players will be real? : ");
                    int realPlayers = correctShape(sc, 0, countAll);
                    Tourney tourney = new Tourney(countAll, realPlayers);
                    int[] res = tourney.play(board);
                    for (int i = 1; i <= countAll; i++) {
                        System.out.println("Player " + i + " place: " + res[i - 1]);
                    }
                }
            } finally {
                sc.close();
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}

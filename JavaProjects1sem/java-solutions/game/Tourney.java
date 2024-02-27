package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tourney {
    Player[] players;
    int n;
    int[] res;
    List<Integer> resultPlayers;
    int degree;

    private int toDegree(int a) {
        int s = 1;
        int deg = 0;
        while (s < a) {
            s *= 2;
            deg++;
        }
        degree = deg;
        return s;
    }

    public Tourney(int countAll, int realPlayers) {
        int n = toDegree(countAll);
        this.n = n;
        this.resultPlayers = new ArrayList<>();
        players = new Player[n];
        int fake = n - countAll;
        for (int i = 0; i < fake; i++) {
            players[i * 2] = new LosePlayer();
        }
        for (int i = 0; i < n; i++) {
            if (players[i] == null) {
                this.resultPlayers.add(i);
                if (realPlayers > 0) {
                    players[i] = new HumanPlayer();
                    realPlayers--;
                } else {
                    players[i] = new RandomPlayer();
                }
            }
        }

        res = new int[n];
    }

    public static int oneGame(Board board, Player player1, Player player2) {
        final Game game = new Game(true, player1, player2);
        int result;
        do {
            result = game.play(board);
            return result;
        } while (result < 1);
    }

    public int[] play(Board board) {
        int result;
        int size = n / 2;
        for (int i = 0; i < degree; i++) {
            int ind1 = -1;
            int played = 0;
            while(played < size) {
                Random rn = new Random();
                int rand = 0;
                do {
                    rand = rn.nextInt(n);
                } while (res[rand] != 0 || (i == 0 && (ind1 + rand) % 2 == 0));
                if (res[rand] == 0 && ind1 != rand) {
                    if (ind1 == -1) {
                        ind1 = rand;
                    } else {
                        played += 1;
                        System.out.println("Player " + (ind1 + 1) + " VS player " + (rand + 1));
                        result = oneGame(board, players[ind1], players[rand]);
                        board.clear();
                        if (result == 1) {
                            res[rand] = degree - i + 1; // lose
                            res[ind1] = -1;
                        } else {
                            res[ind1] = degree - i + 1;
                            res[rand] = -1;
                        }
                        ind1 = -1;
                    }
                }
            }
            for (int j = 0; j < n; j++) {
                if (res[j] == -1) {
                    res[j] = 0;
                }
            }size = size/2;
        }
        for (int i = 0; i < n; i++) {
            if (res[i] == 0) {
                res[i] = 1;
            }
        }

        int[] answer = new int[resultPlayers.size()];
        for (int i = 0; i < resultPlayers.size(); i++) {
            answer[i] = res[resultPlayers.get(i)];
        }
        return answer;
    }


}

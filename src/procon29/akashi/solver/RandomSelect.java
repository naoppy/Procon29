package procon29.akashi.solver;

import procon29.akashi.owners.Owner;
import procon29.akashi.players.Player;
import procon29.akashi.selection.Selection;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

/**
 * ランダムに打つ手を決める
 */
public class RandomSelect implements Solver {
    private Random random = new Random(System.currentTimeMillis());

    @Override
    public void solve(int[][] scores, Owner[][] owners, Player[] players) {
        int h = scores.length, w = scores[0].length;

        Arrays.stream(players).limit(2).forEach(player -> {
            while (!player.isFinishNextSelect()) {
                int newY = player.getNowPoint().y + random.nextInt(3) - 1;
                int newX = player.getNowPoint().x + random.nextInt(3) - 1;
                if (newX < 0 || newX >= w || newY < 0 || newY >= h) continue;
                Selection selection = owners[newY + 1][newX + 1] == Owner.Enemy ? Selection.REMOVE : Selection.MOVE;
                player.select(selection, new Point(newX, newY));
            }
        });
    }
}

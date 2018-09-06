package procon29.akashi.solver;

import procon29.akashi.Owner;
import procon29.akashi.Selection;
import procon29.akashi.players.EnemyPlayer;
import procon29.akashi.players.FriendPlayer;

/**
 * 味方プレイヤー2人の動く方向を決定する
 */
public class Solver {

    public static void solve(int[][] scores, Owner[][] owners, FriendPlayer fp1, FriendPlayer fp2, EnemyPlayer ep1, EnemyPlayer ep2) {
        System.err.println("solve");
        fp1.select(Selection.MOVE, fp1.getNowPoint());
        fp2.select(Selection.MOVE, fp2.getNowPoint());
    }
}

package procon29.akashi.solver;

import procon29.akashi.owners.Owner;
import procon29.akashi.players.EnemyPlayer;
import procon29.akashi.players.FriendPlayer;

/**
 * 味方プレイヤー2人の動く方向を決定する
 */
public interface Solver {
    void solve(int[][] scores, Owner[][] owners, FriendPlayer fp1, FriendPlayer fp2, EnemyPlayer ep1, EnemyPlayer ep2);
}

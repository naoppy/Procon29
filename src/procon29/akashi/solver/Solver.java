package procon29.akashi.solver;

import procon29.akashi.owners.Owner;
import procon29.akashi.players.Player;

/**
 * 味方プレイヤー2人の動く方向を決定する
 */
public interface Solver {
    void solve(int[][] scores, Owner[][] owners, Player[] players);
}

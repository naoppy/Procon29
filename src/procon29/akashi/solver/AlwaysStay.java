package procon29.akashi.solver;

import procon29.akashi.owners.Owner;
import procon29.akashi.players.EnemyPlayer;
import procon29.akashi.players.FriendPlayer;
import procon29.akashi.selection.LongitudinalDiff;
import procon29.akashi.selection.Selection;
import procon29.akashi.selection.TransverseDiff;
import procon29.akashi.selection.XYDiff;

public class AlwaysStay implements Solver {
    public void solve(int[][] scores, Owner[][] owners, FriendPlayer fp1, FriendPlayer fp2, EnemyPlayer ep1, EnemyPlayer ep2) {
        System.err.println("solve");
        fp1.select(Selection.MOVE, new XYDiff(LongitudinalDiff.None, TransverseDiff.None));
        fp2.select(Selection.MOVE, new XYDiff(LongitudinalDiff.None, TransverseDiff.None));
    }
}

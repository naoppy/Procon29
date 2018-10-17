package procon29.akashi.solver;

import procon29.akashi.owners.Owner;
import procon29.akashi.players.Player;
import procon29.akashi.selection.LongitudinalDiff;
import procon29.akashi.selection.Selection;
import procon29.akashi.selection.TransverseDiff;
import procon29.akashi.selection.XYDiff;

public class AlwaysStay implements Solver {
    public void solve(int[][] scores, Owner[][] owners, Player[] players) {
        players[0].select(Selection.MOVE, new XYDiff(LongitudinalDiff.None, TransverseDiff.None));
        players[1].select(Selection.MOVE, new XYDiff(LongitudinalDiff.None, TransverseDiff.None));
    }
}

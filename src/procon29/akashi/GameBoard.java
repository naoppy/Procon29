package procon29.akashi;

import procon29.akashi.players.EnemyPlayer;
import procon29.akashi.players.FriendPlayer;
import procon29.akashi.players.Player;
import procon29.akashi.scores.QRInputer;
import procon29.akashi.scores.ScoreFromQR;
import procon29.akashi.scores.ScoreMaker;
import procon29.akashi.solver.Solver;

import java.awt.*;
import java.util.*;

/**
 * ゲームの進行を管理する最上位クラス
 */
public class GameBoard {
    /**
     * スコアと自分の位置を与える
     */
    public ScoreMaker maker = new ScoreFromQR(QRInputer.inputCode());
    /**
     * スコア
     */
    private int[][] scores = maker.getMap();
    /**
     * どのチームの領域か
     */
    private Owner[][] owners = new Owner[maker.getHeight()][maker.getWidth()];
    /**
     * 自分のチームのPlayer2人
     */
    private FriendPlayer fp1 = new FriendPlayer(maker.getFp1()), fp2 = new FriendPlayer(maker.getFp2());
    /**
     * 相手のチームのPlayer2人
     */
    private EnemyPlayer ep1, ep2;
    /**
     *
     */
    private Player[] players = {fp1, fp2, ep1, ep2};
    /**
     * 青プレイヤーの初期位置を入力するためのセット
     */
    public Set<Point> enemyPlayerSet = new HashSet<>();
    /**
     * ソルバー
     */
    private Solver solver = new Solver();

    /**
     * 敵エージェントの位置を決定する
     */
    public boolean decideEnemyPlayerPlace() {
        if (enemyPlayerSet.size() != 2) return false;

        Point[] points = enemyPlayerSet.toArray(new Point[2]);
        ep1 = new EnemyPlayer(points[0]);
        ep2 = new EnemyPlayer(points[1]);

        return true;
    }

    /**
     * 味方のエージェント2人の動く方向を決定する
     */
    public void solve() {
        solver.solve(scores, owners, fp1, fp2, ep1, ep2);
    }

    private boolean nextStage() {
        if (Arrays.stream(players).allMatch(Player::isFinishNextSelect))
            return false;

        Point[] applyPoints = Arrays.stream(players).map(p -> p.getApplyPoint()).toArray(Point[]::new);
        Map<Point, Integer> pointsMap = new HashMap<>();

        for (Point point : applyPoints) {
            if (pointsMap.containsKey(point)) {
                pointsMap.put(point, pointsMap.get(point) + 1);
            } else {
                pointsMap.put(point, 1);
            }
        }

        Arrays.stream(players).filter(player -> pointsMap.get(player.getApplyPoint()) == 1).forEach(player -> {
            player.reset();
            if (player.getSelection().equals(Selection.MOVE)) {
                owners[player.getApplyPoint().y][player.getApplyPoint().x] = player instanceof FriendPlayer ? Owner.Friend : Owner.Enemy;
            } else {//Selection.REMOVE case
                owners[player.getApplyPoint().y][player.getApplyPoint().x] = Owner.None;
            }
        });

        return true;
    }
}

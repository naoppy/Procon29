package procon29.akashi;

import procon29.akashi.players.EnemyPlayer;
import procon29.akashi.players.FriendPlayer;
import procon29.akashi.scores.QRInputer;
import procon29.akashi.scores.ScoreFromQR;
import procon29.akashi.scores.ScoreMaker;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

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
     * 青プレイヤーの初期位置を入力するためのセット
     */
    public Set<Point> enemyPlayerSet = new HashSet<>();

    /**
     * 敵エージェントの位置を決定する
     */
    public void decideEnemyPlayerPlace() {
        if (enemyPlayerSet.size() != 2) return;

        Point[] points = enemyPlayerSet.toArray(new Point[2]);
        ep1 = new EnemyPlayer(points[0]);
        ep2 = new EnemyPlayer(points[1]);

        //Solver.solve(scores, owners, fp1, fp2, ep1, ep2);


    }
}

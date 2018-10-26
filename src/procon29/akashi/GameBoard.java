package procon29.akashi;

import procon29.akashi.owners.Owner;
import procon29.akashi.players.EnemyPlayer;
import procon29.akashi.players.FriendPlayer;
import procon29.akashi.players.Player;
import procon29.akashi.scores.QRInputer;
import procon29.akashi.scores.ScoreFromQR;
import procon29.akashi.scores.ScoreMaker;
import procon29.akashi.selection.Selection;
import procon29.akashi.solver.PrintMapForOtherSolver;
import procon29.akashi.solver.RandomSelect;
import procon29.akashi.solver.Solver;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ゲームの進行を管理する最上位クラス
 */
public class GameBoard {
    /**
     * スコアと自分の位置を与える
     */
    //public ScoreMaker maker = new ScoreFromRandom(9, 10, true, false, 70);
    public ScoreMaker maker = new ScoreFromQR(QRInputer.inputCode());
    /**
     * スコア
     */
    private int[][] scores = maker.getMap();
    /**
     * 残りのターン数
     */
    private int remainTurnNumber = maker.getTurnNumber();
    /**
     * どのチームの領域か
     */
    private Owner[][] owners = new Owner[maker.getHeight() + 2][maker.getWidth() + 2];
    /**
     * [0]と[1]が味方、[2]と[3]が相手
     */
    public Player[] players = {new FriendPlayer(maker.getFp1()), new FriendPlayer(maker.getFp2()), maker.getEp1() == null ? null : new EnemyPlayer(maker.getEp1()), maker.getEp2() == null ? null : new EnemyPlayer(maker.getEp2())};
    /**
     * 青プレイヤーの初期位置を入力するためのセット
     */
    public Set<Point> enemyPlayerSet = new HashSet<>();
    /**
     * ソルバー
     */
    private Solver solver = new RandomSelect();
    /**
     * クリップボードに盤面の状態を出力するクラス
     */
    private PrintMapForOtherSolver PMFOS = new PrintMapForOtherSolver(this);
    /**
     * 敵と味方のスコア
     */
    private int friendScore = 0, enemyScore = 0;

    /**
     * 敵エージェントの位置を決定すると共に初期の所有マップを作成
     *
     * @return 不正でない位置によって正しく初期化が完了したらtrue
     */
    public boolean decideEnemyPlayerPlace() {
        if (!Arrays.stream(players).skip(2).allMatch(Objects::nonNull)) {
            if (enemyPlayerSet.size() != 2) return false;

            Point[] points = enemyPlayerSet.toArray(new Point[2]);
            players[2] = new EnemyPlayer(points[0]);
            players[3] = new EnemyPlayer(points[1]);
        }

        //所有マップを初期化する
        IntStream.range(0, maker.getHeight()).forEach(y -> IntStream.range(0, maker.getWidth()).forEach(x -> setOwn(x, y, Owner.None)));

        Arrays.stream(players).forEach(player -> setOwn(player.getNowPoint().x, player.getNowPoint().y, player instanceof FriendPlayer ? Owner.Friend : Owner.Enemy));

        return true;
    }

    /**
     * 味方のエージェント2人の動く方向を決定する
     */
    public void solve() {
        PMFOS.print();
        solver.solve(scores, owners, players);
    }

    /**
     * 盤面を次に進める
     *
     * @return 全てのプレイヤーが移動方向を決定できていなければfalse、出来ていたのならtrue
     */
    public boolean nextStage() {
        if (!Arrays.stream(players).allMatch(Player::isFinishNextSelect)) return false;

        remainTurnNumber -= 1;//1ターンカウントを減らす
        Point[] applyPoints = Stream.concat(Arrays.stream(players).map(Player::getApplyPoint), Arrays.stream(players).filter(player -> player.getSelection() == Selection.REMOVE).map(Player::getNowPoint)).toArray(Point[]::new);

        Arrays.stream(players).forEach(Player::resetSelection);
        Arrays.stream(players).filter(player -> Arrays.stream(applyPoints).filter(point -> player.getApplyPoint().equals(point)).count() == 1).forEach(player -> {//意思表示が無効にならないなら以下を実行
            if (player.getSelection() == Selection.MOVE) {
                player.move(player.getXyDiff());
                setOwn(player.getNowPoint().x, player.getNowPoint().y, player instanceof FriendPlayer ? Owner.Friend : Owner.Enemy);
            } else {//Selection.REMOVE case
                setOwn(player.getApplyPoint().x, player.getApplyPoint().y, Owner.None);
            }
        });

        this.calcScore();

        return true;
    }

    /**
     * スコアを計算する
     */
    public void calcScore() {
        //AllScoreCalcクラスに処理を委譲
        friendScore = AllScoreCalc.calc(this, Owner.Friend);
        enemyScore = AllScoreCalc.calc(this, Owner.Enemy);
    }

    /**
     * 与えられた座標のタイルを所持しているチームを返す
     *
     * @param x 取り出すx座標
     * @param y 取り出すy座標
     * @return 所持しているチーム
     */
    public Owner getOwn(int x, int y) {
        return owners[y + 1][x + 1];
    }

    /**
     * 指定した座標のタイルの所有者を変更する
     *
     * @param x     指定するx座標
     * @param y     指定するy座標
     * @param owner 新しく所持するチーム
     */
    public void setOwn(int x, int y, Owner owner) {
        owners[y + 1][x + 1] = owner;
    }

    /**
     * @return 残りのターン数
     */
    public int getRemainTurnNumber() {
        return remainTurnNumber;
    }

    /**
     * @return 味方のスコア
     */
    public int getFriendScore() {
        return this.friendScore;
    }

    /**
     * @return 敵のスコア
     */
    public int getEnemyScore() {
        return this.enemyScore;
    }

    /**
     * 指定した座標のスコアを返す
     *
     * @param x x座標
     * @param y y座標
     * @return スコア
     */
    public int getScore(int x, int y) {
        return scores[y][x];
    }
}

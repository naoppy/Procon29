package procon29.akashi.players;

import procon29.akashi.Selection;

import java.awt.*;

/**
 * プレイヤーを表すクラス
 */
public abstract class Player {
    /**
     * 今の座標(x,y)
     */
    private Point nowPoint;
    /**
     * 次の手を選択し終えたか
     */
    private boolean isFinishNextSelect = false;
    /**
     * 次の行動の対象座標(x,y)
     */
    private Point applyPoint;
    /**
     * 次の手
     */
    private Selection selection;

    /**
     * 最初の座標を設定して生成する
     *
     * @param initPoint 最初の座標(x,y)
     */
    public Player(Point initPoint) {
        this.nowPoint = initPoint;
    }

    /**
     * 次の手を選択し終えたか返す
     *
     * @return 選択し終えたならtrue
     */
    public boolean isFinishNextSelect() {
        return isFinishNextSelect;
    }

    /**
     * 次の手を選択する
     *
     * @param selection  行動の種類
     * @param applyPoint 行動を適応する場所
     * @return ルールに則った選択ならtrue
     */
    public boolean select(Selection selection, Point applyPoint) {
        if (!nearKinbo8(nowPoint, applyPoint)) return false;

        this.isFinishNextSelect = true;
        this.selection = selection;
        this.applyPoint = applyPoint;

        return true;
    }

    /**
     * 1点がもう1点の8近傍にあるかを調べる
     *
     * @param nowPoint  1つ目の点
     * @param nextPoint 2つ目の点
     * @return 8近傍にある場合true
     */
    private static boolean nearKinbo8(Point nowPoint, Point nextPoint) {
        int[][] kinbo8 = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int i = 0; i < 8; i++) {
            if (nowPoint.x + kinbo8[i][0] == nextPoint.x && nowPoint.y + kinbo8[i][1] == nextPoint.y) {
                return true;
            }
        }

        return false;
    }

    /**
     * 今の座標を返す
     *
     * @return 今の座標
     */
    public Point getNowPoint() {
        return nowPoint;
    }

    /**
     * 次の操作の対象座標のgetter
     *
     * @return 次の操作の対象座標
     */
    public Point getApplyPoint() {
        return applyPoint;
    }

    /**
     * 次の操作のgetter
     *
     * @return 次の操作
     */
    public Selection getSelection() {
        return selection;
    }

    /**
     * 内部の状態を更新する
     */
    public void reset() {
        if (!isFinishNextSelect) return;

        isFinishNextSelect = false;

        if (selection.equals(Selection.MOVE)) {
            nowPoint = applyPoint;
        }
    }

    @Override
    public String toString() {
        return this.getNowPoint().x + "," + this.getNowPoint().y;
    }
}

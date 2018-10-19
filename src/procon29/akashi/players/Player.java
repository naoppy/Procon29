package procon29.akashi.players;

import procon29.akashi.selection.LongitudinalDiff;
import procon29.akashi.selection.Selection;
import procon29.akashi.selection.TransverseDiff;
import procon29.akashi.selection.XYDiff;

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
     * nowPointから見たapplyPointの方向
     */
    private XYDiff xyDiff;
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
     * @param selection 行動の種類
     * @param xyDiff    今の位置からの移動方向
     * @return ルールに則った選択ならtrue
     */
    public boolean select(Selection selection, XYDiff xyDiff) {
        this.isFinishNextSelect = true;
        this.selection = selection;
        this.xyDiff = xyDiff;
        this.applyPoint = this.calcApplyPoint(xyDiff);

        return true;
    }

    /**
     * 次の手を選択する
     *
     * @param selection  行動の種類
     * @param applyPoint 行動の適応座標
     * @return ルールに則った選択ならtrue
     */
    public boolean select(Selection selection, Point applyPoint) {
        this.xyDiff = calcXYDiff(applyPoint);
        if (xyDiff == null) return false;

        this.isFinishNextSelect = true;
        this.selection = selection;
        this.applyPoint = applyPoint;

        return true;
    }

    /**
     * NowPointからxyDiffを使ってapplyPointを計算する
     *
     * @param xyDiff xyの差
     * @return applyPoint
     */
    private Point calcApplyPoint(XYDiff xyDiff) {
        int xDiff = 0, yDiff = 0;

        switch (xyDiff.getxDiff()) {
            case Right:
                xDiff = 1;
                break;
            case Left:
                xDiff = -1;
                break;
            case None:
                xDiff = 0;
                break;
        }

        switch (xyDiff.getyDiff()) {
            case Up:
                yDiff = 1;
                break;
            case Down:
                yDiff = -1;
                break;
            case None:
                yDiff = 0;
                break;
        }

        return new Point(nowPoint.x + xDiff, nowPoint.y + yDiff);
    }

    /**
     * applyPointからnowPointを使ってXYDiffを計算する
     *
     * @param applyPoint 行動先の座標
     * @return XYの差
     */
    private XYDiff calcXYDiff(Point applyPoint) {
        LongitudinalDiff yDiff;
        TransverseDiff xDiff;

        switch (applyPoint.x - nowPoint.x) {
            case 1:
                xDiff = TransverseDiff.Right;
                break;
            case 0:
                xDiff = TransverseDiff.None;
                break;
            case -1:
                xDiff = TransverseDiff.Left;
                break;
            default:
                return null;
        }

        switch (applyPoint.y - nowPoint.y) {
            case 1:
                yDiff = LongitudinalDiff.Up;
                break;
            case 0:
                yDiff = LongitudinalDiff.None;
                break;
            case -1:
                yDiff = LongitudinalDiff.Down;
                break;
            default:
                return null;
        }

        return new XYDiff(yDiff, xDiff);
    }

    /**
     * @return 今の座標
     */
    public Point getNowPoint() {
        return nowPoint;
    }

    /**
     * 移動する
     *
     * @param diff x方向、y方向の移動ベクトル
     */
    public void move(XYDiff diff) {
        nowPoint = calcApplyPoint(diff);
    }

    /**
     * @return 次の操作の対象座標
     */
    public Point getApplyPoint() {
        return applyPoint;
    }

    /**
     * @return nowPointから見たapplyPointの方向
     */
    public XYDiff getXyDiff() {
        return xyDiff;
    }

    /**
     * @return 次の操作
     */
    public Selection getSelection() {
        return selection;
    }

    /**
     * 内部の状態を更新する
     */
    public void resetSelection() {
        isFinishNextSelect = false;
    }

    @Override
    public String toString() {
        return this.getNowPoint().x + "," + this.getNowPoint().y;
    }
}

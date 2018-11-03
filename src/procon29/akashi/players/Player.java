package procon29.akashi.players;

import procon29.akashi.selection.Selection;
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
        this.applyPoint = PlayerUtils.calcApplyPoint(nowPoint, xyDiff);

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
        this.xyDiff = PlayerUtils.calcXYDiff(nowPoint, applyPoint);
        if (xyDiff == null) return false;

        this.isFinishNextSelect = true;
        this.selection = selection;
        this.applyPoint = applyPoint;

        return true;
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
        nowPoint = PlayerUtils.calcApplyPoint(nowPoint, diff);
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
        return "x:" + this.getNowPoint().x + ", y:" + this.getNowPoint().y;
    }
}

package procon29.akashi.players;

import procon29.akashi.selection.LongitudinalDiff;
import procon29.akashi.selection.TransverseDiff;
import procon29.akashi.selection.XYDiff;

import java.awt.*;

/**
 * プレイヤー関連の変換処理の便利クラス
 */
class PlayerUtils {
    private PlayerUtils() {
    }

    /**
     * NowPointからxyDiffを使ってapplyPointを計算する
     *
     * @param xyDiff xyの差
     * @return applyPoint
     */
    static Point calcApplyPoint(Point nowPoint, XYDiff xyDiff) {
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
    static XYDiff calcXYDiff(Point nowPoint, Point applyPoint) {
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
}

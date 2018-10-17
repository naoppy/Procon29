package procon29.akashi.selection;

/**
 * 二次元方向の向きを表すクラス
 */
public class XYDiff {
    private LongitudinalDiff yDiff;
    private TransverseDiff xDiff;

    /**
     * @param yDiff 縦の差
     * @param xDiff 横の差
     */
    public XYDiff(LongitudinalDiff yDiff, TransverseDiff xDiff) {
        this.yDiff = yDiff;
        this.xDiff = xDiff;
    }

    /**
     * Yの差、Xの差の順番に各1文字で表現した文字列を返す
     * Up:U, Down:D, Right:R, Left:L, None:N
     *
     * @return 2文字で表現されたXYの差
     */
    @Override
    public String toString() {
        return this.yDiff.toString().charAt(0) + "" + this.xDiff.toString().charAt(0);
    }

    public LongitudinalDiff getyDiff() {
        return yDiff;
    }

    public TransverseDiff getxDiff() {
        return xDiff;
    }
}

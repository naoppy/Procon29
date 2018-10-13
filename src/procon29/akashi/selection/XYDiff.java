package procon29.akashi.selection;

public class XYDiff {
    private LongitudinalDiff yDiff;
    private TransverseDiff xDiff;

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
}

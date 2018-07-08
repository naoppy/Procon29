/**
 * マップの各座標における点を決定するクラス
 */
public class ScoreMaker {
    /**
     * マップの縦、横の大きさ
     */
    private int height;
    private int width;
    /**
     * 中央の縦線で線対称か
     */
    private boolean verticalLineIsSymmetry;
    /**
     * 中央の横線で線対称か
     */
    private boolean horizontalLineIsSymmetry;

    /**
     * 指定されたパラメーターでこのクラスを作ります
     * @param h マップの縦のサイズ
     * @param w マップの横のサイズ
     * @param vLineIsSymmetry 縦線で線対称か
     * @param hLineIsSymmetry 横線で線対称か
     */
    public ScoreMaker(int h, int w, boolean vLineIsSymmetry, boolean hLineIsSymmetry) {
        height = h;
        width = w;
        verticalLineIsSymmetry = vLineIsSymmetry;
        horizontalLineIsSymmetry = hLineIsSymmetry;
    }

    /**
     * スコア配列を作成して返します
     * @return スコアの配列
     */
    public int[][] make() {
        int[][] scores = new int[height][width];

        return scores;
    }

    /**
     * 縦の長さを返す
     * @return マップの縦の高さ
     */
    public int getHeight() {
        return height;
    }

    /**
     * 横の長さを返す
     * @return マップの横の高さ
     */
    public int getWidth() {
        return width;
    }
}

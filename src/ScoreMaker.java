import java.util.Random;

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
     *
     * @param h               マップの縦のサイズ
     * @param w               マップの横のサイズ
     * @param vLineIsSymmetry 縦線で線対称か
     * @param hLineIsSymmetry 横線で線対称か
     */
    public ScoreMaker(int h, int w, boolean vLineIsSymmetry, boolean hLineIsSymmetry) {
        if (!(vLineIsSymmetry || hLineIsSymmetry)) {
            throw new IllegalArgumentException("縦と横のどちらかは最低限線対称である必要があります");
        }
        if (h < 12 || w < 12 || h <= 0 || w <= 0) {
            throw new IllegalArgumentException("hまたはwが不正です。h:" + h + " w:" + w);
        }

        height = h;
        width = w;
        verticalLineIsSymmetry = vLineIsSymmetry;
        horizontalLineIsSymmetry = hLineIsSymmetry;
    }

    /**
     * スコア配列を作成して返します
     *
     * @return スコアの配列
     */
    public int[][] make() {
        //Generate and Initialize a Score Map.
        Random randomMaker = new Random(System.currentTimeMillis());
        int[][] scores = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                scores[y][x] = randomMaker.nextInt(17);
            }
        }
        //Minus cell make
        if (verticalLineIsSymmetry) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if(randomMaker.nextDouble() < 0.1) {
                        scores[y][x] *= -1;
                    }
                }
            }
        }

        //Symmetrize about vLine
        if (verticalLineIsSymmetry) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width / 2; x++) {
                    scores[y][width - 1 - x] = scores[y][x];
                }
            }
        }
        //Symmetrize about hLine
        if (horizontalLineIsSymmetry) {
            for (int y = 0; y < height / 2; y++) {
                for (int x = 0; x < width; x++) {
                    scores[height - 1 - y][x] = scores[y][x];
                }
            }
        }

        return scores;
    }

    /**
     * 縦の長さを返す
     *
     * @return マップの縦の高さ
     */
    public int getHeight() {
        return height;
    }

    /**
     * 横の長さを返す
     *
     * @return マップの横の高さ
     */
    public int getWidth() {
        return width;
    }
}

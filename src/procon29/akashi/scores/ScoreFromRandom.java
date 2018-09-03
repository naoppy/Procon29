package procon29.akashi.scores;

import java.awt.*;
import java.util.Random;

/**
 * マップの各座標における点を決定するクラス
 */
public class ScoreFromRandom extends ScoreMaker {
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
    public ScoreFromRandom(int h, int w, boolean vLineIsSymmetry, boolean hLineIsSymmetry) {
        if (!(vLineIsSymmetry || hLineIsSymmetry)) {
            throw new IllegalArgumentException("縦と横のどちらかは最低限線対称である必要があります");
        }
        if (h > 12 || w > 12 || h <= 0 || w <= 0 || h * w < 80) {
            throw new IllegalArgumentException("hまたはwが不正です。h:" + h + " w:" + w);
        }

        setHeight(h);
        setWidth(w);
        verticalLineIsSymmetry = vLineIsSymmetry;
        horizontalLineIsSymmetry = hLineIsSymmetry;

        this.make();
    }

    /**
     * スコア配列を作成して返します
     *
     * @return スコアの配列
     */
    protected void make() {
        int height = getHeight();
        int width = getWidth();

        //Generate and Initialize a Score Map.
        Random randomMaker = new Random(System.currentTimeMillis());
        int[][] scores = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                scores[y][x] = randomMaker.nextInt(17);

                if (randomMaker.nextDouble() < 0.1) {
                    scores[y][x] *= -1;
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

        //Generate players point
        if (verticalLineIsSymmetry && horizontalLineIsSymmetry) {
            int ylim = height / 2;
            int xlim = width / 2;

            int fp1y = randomMaker.nextInt(ylim);
            int fp1x = randomMaker.nextInt(xlim);
            fp1 = new Point(fp1x, fp1y);

            int fp2y = height - 1 - fp1y;
            int fp2x = width - 1 - fp1x;
            fp2 = new Point(fp2x, fp2y);

            int ep1y = fp2y;
            int ep1x = fp1x;
            ep1 = new Point(ep1x, ep1y);

            int ep2y = fp1y;
            int ep2x = fp2x;
            ep2 = new Point(ep2x, ep2y);
        } else if (verticalLineIsSymmetry) {
            int ylim = height;
            int xlim = width / 2;

            int fp1y = randomMaker.nextInt(ylim);
            int fp1x = randomMaker.nextInt(xlim);
            fp1 = new Point(fp1x, fp1y);

            int ep1y = randomMaker.nextInt(ylim);
            while(ep1y == fp1y) ep1y = randomMaker.nextInt(ylim);
            int ep1x = randomMaker.nextInt(xlim);
            ep1 = new Point(ep1x, ep1y);

            int fp2y = ep1y;
            int fp2x = width - 1 - ep1x;
            fp2 = new Point(fp2x, fp2y);

            int ep2y = fp1y;
            int ep2x = width - 1 - fp1x;
            ep2 = new Point(ep2x, ep2y);
        } else if(horizontalLineIsSymmetry) {
            int ylim = height / 2;
            int xlim = width;

            int fp1y = randomMaker.nextInt(ylim);
            int fp1x = randomMaker.nextInt(xlim);
            fp1 = new Point(fp1x, fp1y);

            int ep1y = randomMaker.nextInt(ylim);
            int ep1x = randomMaker.nextInt(xlim);
            while(ep1x == fp1x) ep1x = randomMaker.nextInt(xlim);
            ep1 = new Point(ep1x, ep1y);

            int fp2y = height -1 -ep1y;
            int fp2x = ep1x;
            fp2 = new Point(fp2x, fp2y);

            int ep2y = height - 1 - fp1y;
            int ep2x = fp1x;
            ep2 = new Point(ep2x, ep2y);
        }

        setMap(scores);
    }
}

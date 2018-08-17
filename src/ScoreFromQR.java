import java.util.Scanner;

/**
 * QRコードを読み取った文字列からマップの点をだすクラス
 */
public class ScoreFromQR extends ScoreMaker {
    /**
     * QRコードから読み取った生の文字列
     */
    private String QRData;

    //private Point redPlayer1Point;

    /**
     * QR文字列を受け取ってScoreFromQRクラスを作成します
     *
     * @param QRData QRデータ
     */
    public ScoreFromQR(String QRData) {
        this.QRData = QRData;
        this.make();
    }

    /**
     * スコア配列を作成して返します
     *
     * @return スコアの配列
     */
    @Override
    protected void make() {
        /*  sample data
        8 11:-2 1 0 1 2 0 2 1 0 1 -2:1 3 2 -2 0 1 0 -2 2 3 1:1 3 2 1 0 -2 0 1 2 3 1:2 1 1
2 2 3 2 2 1 1 2: 2 1 1 2 2 3 2 2 1 1 2: 1 3 2 1 0 -2 0 1 2 3 1: 1 3 2 -2 0 1 0 -2
2 3 1: -2 1 0 1 2 0 2 1 0 1 -2:2 2:7 10:
         */
        String ReFormattedQRData = this.QRData.replaceAll(":", " ");
        //ReFormattedQRData = ReFormattedQRData.replaceAll(" {2}", " ");
        System.err.println(ReFormattedQRData);
        Scanner sc = new Scanner(ReFormattedQRData);

        int h = sc.nextInt();
        int w = sc.nextInt();
        setHeight(h);
        setWidth(w);

        int[][] scores = new int[h][w];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                scores[y][x] = sc.nextInt();
            }
        }

        setMap(scores);
    }

}

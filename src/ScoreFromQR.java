import java.util.Scanner;

/**
 * QRコードを読み取った文字列からマップの点をだすクラス
 */
public class ScoreFromQR {
    /**
     * マップの縦、横の大きさ
     */
    private int height;
    private int width;
    /**
     * QRコードから読み取った生の文字列
     */
    private String QRData;

    /**
     * QR文字列を受け取ってScoreFromQRクラスを作成します
     * @param QRData QRデータ
     */
    public ScoreFromQR(String QRData) {
        this.QRData = QRData;
    }

    /**
     * スコア配列を作成して返します
     *
     * @return スコアの配列
     */
    public int[][] make() {
        /*  sample data
        8 11:-2 1 0 1 2 0 2 1 0 1 -2:1 3 2 -2 0 1 0 -2 2 3 1:1 3 2 1 0 -2 0 1 2 3 1:2 1 1
2 2 3 2 2 1 1 2: 2 1 1 2 2 3 2 2 1 1 2: 1 3 2 1 0 -2 0 1 2 3 1: 1 3 2 -2 0 1 0 -2
2 3 1: -2 1 0 1 2 0 2 1 0 1 -2:2 2:7 10:
         */
        String ReFormattedQRData = this.QRData.replaceAll(":"," ");
        ReFormattedQRData = ReFormattedQRData.replaceAll(" {2}", " ");
        Scanner sc = new Scanner(ReFormattedQRData);

        int h = sc.nextInt();
        int w = sc.nextInt();
        this.height = h;
        this.width = w;

        int[][] scores = new int[h][w];
        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                scores[y][x] = sc.nextInt();
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

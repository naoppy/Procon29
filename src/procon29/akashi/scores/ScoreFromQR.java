package procon29.akashi.scores;

import java.awt.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * QRコードを読み取った文字列からマップの点をだすクラス
 */
public class ScoreFromQR extends ScoreMaker {
    /**
     * QRコードから読み取った生の文字列
     */
    private String QRData;

    /**
     * QRコードで読み取った高さと幅
     */
    private int QRHeight = 0, QRWidth = 0;

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
     */
    @Override
    protected void make() {
        /*  sample data
        8 11:-2 1 0 1 2 0 2 1 0 1 -2:1 3 2 -2 0 1 0 -2 2 3 1:1 3 2 1 0 -2 0 1 2 3 1:2 1 1
2 2 3 2 2 1 1 2: 2 1 1 2 2 3 2 2 1 1 2: 1 3 2 1 0 -2 0 1 2 3 1: 1 3 2 -2 0 1 0 -2
2 3 1: -2 1 0 1 2 0 2 1 0 1 -2:2 2:7 10:
         */
        String ReFormattedQRData = this.QRData.replaceAll(":", " ");
        //System.err.println(ReFormattedQRData);
        Scanner sc = new Scanner(ReFormattedQRData);

        inputSide();
        inputTurnNumber();

        //高さと幅の読み取り
        QRHeight = sc.nextInt();
        QRWidth = sc.nextInt();
        //スコアの読み取り
        int[][] scores = new int[QRHeight][QRWidth];
        for (int y = 0; y < QRHeight; y++) {
            for (int x = 0; x < QRWidth; x++) {
                scores[y][x] = sc.nextInt();
            }
        }
        //エージェント初期座標の読み取り
        int fp1y = sc.nextInt() - 1;
        fp1 = new Point(sc.nextInt() - 1, fp1y);
        int fp2y = sc.nextInt() - 1;
        fp2 = new Point(sc.nextInt() - 1, fp2y);

        //回転
        if (side == 1) {
            fp1 = rotate90(fp1);
            fp2 = rotate90(fp2);
            scores = rotate90(scores);
        } else if (side == 2) {
            fp1 = rotate270(fp1);
            fp2 = rotate270(fp2);
            scores = rotate270(scores);
        }
        //盤面を90度/270度回転すると、高さと幅は入れ変わる
        setHeight(QRWidth);
        setWidth(QRHeight);

        setMap(scores);
    }

    /**
     * ターン数を標準入力から入力させる
     */
    private void inputTurnNumber() {
        Scanner sc = new Scanner(System.in);
        System.out.println("ターン数を入力してください。");

        setTurnNumber(sc.nextInt());
    }

    /**
     * 自分のチームが右か左か入力する
     */
    private void inputSide() {
        Scanner sc = new Scanner(System.in);
        int i;
        do {
            System.out.println("\n舞台側を上として、チームの位置は右か左か入力してください。");
            System.out.println("1：右    2：左");
            i = sc.nextInt();
        } while (!(i == 1 || i == 2));

        this.side = i;
    }

    /**
     * 座標を90度回転する
     *
     * @param point 回転対象
     * @return 新しく作られる回転後の座標
     */
    private Point rotate90(Point point) {
        return new Point(QRHeight - 1 - point.y, point.x);
    }

    /**
     * 90度回転させた二次元配列を作成する
     *
     * @param arr 回転対象
     * @return 新しく作られる回転後の二次元配列
     */
    private int[][] rotate90(int[][] arr) {
        int[][] newArr = new int[QRWidth][QRHeight];
        for (int y = 0; y < QRHeight; y++) {
            for (int x = 0; x < QRWidth; x++) {
                newArr[x][QRHeight - 1 - y] = arr[y][x];
            }
        }
        return newArr;
    }

    /**
     * 座標を270度回転する
     *
     * @param point 回転対象
     * @return 新しく作られる回転後の座標
     */
    private Point rotate270(Point point) {
        return new Point(point.y, QRWidth - 1 - point.x);
    }

    /**
     * 270度回転させた二次元配列を作成する
     *
     * @param arr 回転対象
     * @return 新しく作られる回転後の二次元配列
     */
    private int[][] rotate270(int[][] arr) {
        int[][] newArr = new int[QRWidth][QRHeight];
        for (int y = 0; y < QRHeight; y++) {
            for (int x = 0; x < QRWidth; x++) {
                newArr[QRWidth - 1 - x][y] = arr[y][x];
            }
        }
        return newArr;
    }

    /**
     * EnemyPlayerの初期位置はQRデータからは読み取ることができない
     *
     * @return 例外(NoSuchElementException)を常にThrow
     */
    @Override
    public Point getEp1() {
        throw new NoSuchElementException("決定できないので存在しません");
    }

    /**
     * EnemyPlayerの初期位置はQRデータからは読み取ることができない
     *
     * @return 例外(NoSuchElementException)を常にThrow
     */
    @Override
    public Point getEp2() {
        throw new NoSuchElementException("決定できないので存在しません");
    }
}

package procon29.akashi.scores;

import java.awt.*;

public abstract class ScoreMaker {
    /**
     * マップの縦、横の大きさ
     */
    private int height;
    private int width;
    /**
     * ターン数
     */
    private int turnNumber;
    /**
     * マップ
     */
    private int[][] map;
    /**
     * 味方チームのプレイヤーの初期座標
     */
    Point fp1, fp2;
    /**
     * 敵チームのプレイヤーの初期座標
     */
    Point ep1, ep2;

    /**
     * マップを作って登録する関数
     */
    protected abstract void make();

    /**
     * マップを返す
     *
     * @return 二次元配列で表したマップ
     */
    public int[][] getMap() {
        return map;
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

    /**
     * ターン数を返す
     *
     * @return ターン数
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    /**
     * ターン数を設定する
     *
     * @param turnNumber 設定するターン数、2018/10/11時点では40～80
     */
    void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    /**
     * マップを設定する
     *
     * @param map マップとして使う二次元配列
     */
    void setMap(int[][] map) {
        this.map = map;
    }

    /**
     * 縦の長さを設定する
     *
     * @param height 縦の長さ
     */
    void setHeight(int height) {
        this.height = height;
    }

    /**
     * 横の長さを設定する
     *
     * @param width 横の長さ
     */
    void setWidth(int width) {
        this.width = width;
    }

    /**
     * 味方チームの一人目の初期位置を返す
     *
     * @return 味方チームの一人目の初期位置
     */
    public Point getFp1() {
        return fp1;
    }

    /**
     * 味方チームの二人目の初期位置を返す
     *
     * @return 味方チームの二人目の初期位置
     */
    public Point getFp2() {
        return fp2;
    }

    /**
     * 敵チームの一人目の初期位置を返す
     *
     * @return 敵チームの一人目の初期位置
     */
    public Point getEp1() {
        return ep1;
    }

    /**
     * 敵チームの二人目の初期位置を返す
     *
     * @return 敵チームの二人目の初期位置
     */
    public Point getEp2() {
        return ep2;
    }
}

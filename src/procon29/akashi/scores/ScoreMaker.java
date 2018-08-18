package procon29.akashi.scores;

import java.awt.*;

public abstract class ScoreMaker {
    /**
     * マップの縦、横の大きさ
     */
    private int height;
    private int width;
    /**
     * マップ
     */
    private int[][] map;
    /**
     * 赤チームのプレイヤーの初期座標
     */
    protected Point rp1, rp2;
    /**
     * 青チームのプレイヤーの初期座標
     */
    protected Point bp1, bp2;

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
     * マップを設定する
     *
     * @param map マップとして使う二次元配列
     */
    protected void setMap(int[][] map) {
        this.map = map;
    }

    /**
     * 縦の長さを設定する
     *
     * @param height 縦の長さ
     */
    protected void setHeight(int height) {
        this.height = height;
    }

    /**
     * 横の長さを設定する
     *
     * @param width 横の長さ
     */
    protected void setWidth(int width) {
        this.width = width;
    }

    /**
     * 赤チームの一人目の初期位置を返す
     *
     * @return 赤チームの一人目の初期位置
     */
    public Point getRp1() {
        return rp1;
    }

    /**
     * 赤チームの二人目の初期位置を返す
     *
     * @return 赤チームの二人目の初期位置
     */
    public Point getRp2() {
        return rp2;
    }

    /**
     * 青チームの一人目の初期位置を返す
     *
     * @return 青チームの一人目の初期位置
     */
    public Point getBp1() {
        return bp1;
    }

    /**
     * 青チームの二人目の初期位置を返す
     *
     * @return 青チームの二人目の初期位置
     */
    public Point getBp2() {
        return bp2;
    }
}

public abstract class ScoreMaker {
    /**
     * マップの縦、横の大きさ
     */
    private int height;
    private int width;

    /**
     * マップを作って返す関数
     */
    public abstract int[][] make();

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
     * 縦の長さを設定する
     * @param height 縦の長さ
     */
    protected void setHeight(int height) {
        this.height = height;
    }

    /**
     * 横の長さを設定する
     * @param width 横の長さ
     */
    protected void setWidth(int width) {
        this.width = width;
    }
}

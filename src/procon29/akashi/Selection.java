package procon29.akashi;

/**
 * プレイヤーのとる操作を表す列挙型
 */
public enum Selection {
    /**
     * その場にとどまる
     */
    WAIT,
    /**
     * 移動する
     */
    MOVE,
    /**
     * タイル除去
     */
    REMOVE,
}

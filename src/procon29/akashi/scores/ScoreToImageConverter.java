package procon29.akashi.scores;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * 画像をキャッシュして数字から画像に変換するクラス
 */
public class ScoreToImageConverter {
    private ScoreToImageConverter() {
    }

    private static Map<Integer, Image> scoreImageMap = new HashMap<>();

    static {
    }

    /**
     * @return 対応する画像
     */
    public static Image convert(int i) {
        return scoreImageMap.get(i);
    }
}

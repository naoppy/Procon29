package procon29.akashi.scores;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 画像をキャッシュして数字から画像に変換するクラス
 */
public class ScoreToImageConverter {
    private ScoreToImageConverter() {
    }

    private static Map<Integer, Image> scoreImageMap = new HashMap<>();

    static {
        IntStream.rangeClosed(-16, 16).forEach(i -> {
            scoreImageMap.put(i, new Image("/score/num" + i + ".png"));
        });
    }

    /**
     * @return 対応する画像
     */
    public static Image convert(int i) {
        return scoreImageMap.get(i);
    }
}

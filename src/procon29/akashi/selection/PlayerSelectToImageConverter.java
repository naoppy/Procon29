package procon29.akashi.selection;

import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 矢印の画像をキャッシュして、文字列から変換する
 */
public class PlayerSelectToImageConverter {
    private PlayerSelectToImageConverter() {
    }

    private static Map<String, Image> selectImageMap = new HashMap<>();

    static {
        String[] states = {"None", "DL", "DN", "DR", "NL", "NN", "NR", "UL", "UN", "UR"};

        Arrays.stream(states).forEach(s -> selectImageMap.put(s, new Image("/arrows/" + s + ".png")));
    }

    public static Image convert(String state) {
        return selectImageMap.get(state);
    }
}

package procon29.akashi.owners;

import javafx.scene.image.Image;
import procon29.akashi.side.Side;

import java.util.HashMap;
import java.util.Map;

/**
 * 画像をキャッシュしてオーナーから画像に変換するクラス
 */
public class OwnerToImageConverter {
    public OwnerToImageConverter(Side side) {
        if (side == Side.RIGHT || side == Side.None) {
            ownerImageMap.put(Owner.Friend, new Image("RedTile.png"));
            ownerImageMap.put(Owner.Enemy, new Image("BlueTile.png"));
        } else if (side == Side.LEFT) {
            ownerImageMap.put(Owner.Friend, new Image("BlueTile.png"));
            ownerImageMap.put(Owner.Enemy, new Image("RedTile.png"));
        }
    }

    private static Map<Owner, Image> ownerImageMap = new HashMap<>();

    static {
        ownerImageMap.put(Owner.None, new Image("GrayTile.png"));
    }

    /**
     * @return 対応する画像
     */
    public Image convert(Owner owner) {
        return ownerImageMap.get(owner);
    }
}

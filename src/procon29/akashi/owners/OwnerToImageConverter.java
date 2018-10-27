package procon29.akashi.owners;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * 画像をキャッシュしてオーナーから画像に変換するクラス
 */
public class OwnerToImageConverter {
    public OwnerToImageConverter(int side) {
        if (side == 1) {
            ownerImageMap.put(Owner.Friend, new Image("FriendTile.png"));
            ownerImageMap.put(Owner.Enemy, new Image("EnemyTile.png"));
        } else if (side == 2) {
            ownerImageMap.put(Owner.Friend, new Image("EnemyTile.png"));
            ownerImageMap.put(Owner.Enemy, new Image("FriendTile.png"));
        } else {
            ownerImageMap.put(Owner.Friend, new Image("FriendTile.png"));
            ownerImageMap.put(Owner.Enemy, new Image("EnemyTile.png"));
        }
    }

    private static Map<Owner, Image> ownerImageMap = new HashMap<>();

    static {
        ownerImageMap.put(Owner.None, new Image("NoneTile.png"));
    }

    /**
     * @return 対応する画像
     */
    public Image convert(Owner owner) {
        return ownerImageMap.get(owner);
    }
}

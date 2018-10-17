package procon29.akashi.owners;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class OwnerToImageConverter {
    private OwnerToImageConverter() {
    }

    private static Map<Owner, Image> ownerImageMap = new HashMap<>();

    static {
        ownerImageMap.put(Owner.None, new Image("NoneTile.png"));
        ownerImageMap.put(Owner.Friend, new Image("FriendTile.png"));
        ownerImageMap.put(Owner.Enemy, new Image("EnemyTile.png"));
    }

    public static Image convert(Owner owner) {
        return ownerImageMap.get(owner);
    }
}

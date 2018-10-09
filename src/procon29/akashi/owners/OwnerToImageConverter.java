package procon29.akashi.owners;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class OwnerToImageConverter {
    private OwnerToImageConverter() {
    }

    private static Map<Owner, Image> ownerImageMap = new HashMap<>();

    public static Image convert(Owner owner) {
        if(!ownerImageMap.containsKey(Owner.None)) ownerImageMap.put(Owner.None, new Image("NoneTile.png"));
        if(!ownerImageMap.containsKey(Owner.Friend)) ownerImageMap.put(Owner.Friend, new Image("FriendTile.png"));
        if(!ownerImageMap.containsKey(Owner.Enemy)) ownerImageMap.put(Owner.Enemy, new Image("EnemyTile.png"));

        return ownerImageMap.get(owner);
    }
}

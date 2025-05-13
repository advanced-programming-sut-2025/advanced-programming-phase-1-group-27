package org.example.models.Shops;

import org.example.models.enums.ShopType;

import java.util.HashMap;
import java.util.Map;

public class BlackSmith extends Shop{

    Map<String , Integer> upgradeLimit = new HashMap<>();

    public BlackSmith(ShopType shopType) {
        super(shopType);
        upgradeLimit.put("")
    }
}

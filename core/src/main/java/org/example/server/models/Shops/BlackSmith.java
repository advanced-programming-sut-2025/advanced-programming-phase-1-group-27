package org.example.server.models.Shops;

import org.example.server.models.enums.ShopType;

import java.util.HashMap;
import java.util.Map;

public class BlackSmith extends Shop {

    Map<String, Integer> upgradeLimit = new HashMap<>();

    public BlackSmith(ShopType shopType) {
        super(shopType);
        upgradeLimit.put("Copper Tool", 1);
        upgradeLimit.put("Iron Tool", 1);
        upgradeLimit.put("Gold Tool", 1);
        upgradeLimit.put("Iridium Tool", 1);
        upgradeLimit.put("Copper Trash Can", 1);
        upgradeLimit.put("Steel Trash Can", 1);
        upgradeLimit.put("Gold Trash Can", 1);
        upgradeLimit.put("Iridium Trash Can", 1);
    }

    public Map<String, Integer> getUpgradeLimit() {
        return upgradeLimit;
    }

}

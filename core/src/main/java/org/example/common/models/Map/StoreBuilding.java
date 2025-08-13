package org.example.common.models.Map;

import org.example.common.models.Building;
import org.example.common.models.Cell;
import org.example.common.models.Shops.Shop;

public class StoreBuilding extends Building {
    private final Shop shop;
    private Cell door = null;

    public StoreBuilding(Shop shop, Cell topLeftCell) {
        super(topLeftCell, 4, 4);
        this.shop = shop;
    }

    public Shop getStore() {
        return shop;
    }

    public Cell getDoor() {
        return door;
    }

    public void setDoor(Cell door) {
        this.door = door;
    }
}

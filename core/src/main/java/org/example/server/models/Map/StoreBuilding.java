package org.example.server.models.Map;

import org.example.server.models.Building;
import org.example.server.models.Cell;
import org.example.server.models.Shops.Shop;

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

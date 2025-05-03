package org.example.models.Map;

import org.example.models.Building;
import org.example.models.Cell;
import org.example.models.Stores.Store;

public class StoreBuilding extends Building {
    private final Store store;
    private Cell door = null;

    public StoreBuilding(Store store) {
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

    public Cell getDoor() {
        return door;
    }

    public void setDoor(Cell door) {
        this.door = door;
    }
}

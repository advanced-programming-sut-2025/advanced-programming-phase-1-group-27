package org.example.models.Map;

import org.example.models.Building;
import org.example.models.Cell;
import org.example.models.Stores.Store;

public class StoreBuilding extends Building {
    private final Store store;
    private Cell door = null;

    public StoreBuilding(Store store, Cell topLeftCell) {
        this.store = store;
        super(topLeftCell, 4, 4);
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

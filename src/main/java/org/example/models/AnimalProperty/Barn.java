package org.example.models.AnimalProperty;

import org.example.models.Building;
import org.example.models.Cell;
import org.example.models.enums.items.BuildingType;

public class Barn extends AnimalEnclosure {
    private Cell topLeftCell;

    public Barn(BuildingType barnType, Cell topLeftCell) {
        super(barnType, topLeftCell);
    }

    public void setTopLeftCell(Cell topLeftCell) {
        this.topLeftCell = topLeftCell;
    }

    public BuildingType getType() {
        return type;
    }
}
